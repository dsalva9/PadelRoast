package com.example.data

import android.content.Context
import com.example.api.PlayerTitles
import com.example.api.RoastCardText
import com.example.api.RoastResponseSchema
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

// --- Moshi models for assets/roast_templates.json ---

@JsonClass(generateAdapter = true)
data class TemplateStrings(
    val headline: String = "",
    val body_text: String = "",
    val pre_filled_whatsapp: String = "",
    val match_recap: String? = null,
    val mvp_line: String? = null,
    val rematch_hype: String? = null
)

@JsonClass(generateAdapter = true)
data class RoastTemplate(
    val id: String,
    val trigger: String,
    val tone: String,
    val game_type: String = "standard",
    val strings: Map<String, TemplateStrings> = emptyMap()
)

@JsonClass(generateAdapter = true)
data class TemplateFile(
    val version: Int = 1,
    val templates: List<RoastTemplate> = emptyList()
)

// --- Engine ---

class TemplateEngine(private val context: Context) {

    companion object {
        private const val PREFS_NAME = "padelroast_templates"
        private const val KEY_RECENT_IDS = "recent_template_ids"
        private const val MAX_RECENT = 20

        /** Pool of funny player titles used when generating responses. */
        private val TITLE_POOL = listOf(
            "El Fantasma", "La Pared", "Smash King", "Net Ninja",
            "Lob Lord", "Bandeja Boss", "The Wall", "Glass Cannon",
            "Captain Whiff", "The Closer", "Fence Hugger", "Point Machine",
            "Drama Queen", "Silent Assassin", "The Anchor", "Vibora Viper",
            "Serve Bot", "Chiquita Master", "No-Show", "Baseline Hugger"
        )
    }

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    /** Lazily loaded and cached template list. */
    private var cachedTemplates: List<RoastTemplate>? = null

    /**
     * Generates a [RoastResponseSchema] from a pre-baked template.
     *
     * @param trigger  Narrative trigger (e.g. "bagel", "comeback").
     * @param tone     Roast tone (e.g. "savage", "friendly").
     * @param players  All four player names in order: [p1, p2, p3, p4]. Team A = p1+p2, Team B = p3+p4.
     * @param scores   Set scores as (TeamA, TeamB) pairs.
     * @param locale   Locale code (e.g. "en", "es"). Falls back to "en" when not found.
     * @return A fully populated [RoastResponseSchema], or null if no templates match.
     */
    fun generate(
        trigger: String,
        tone: String,
        players: List<String>,
        scores: List<Pair<Int, Int>>,
        locale: String = "en"
    ): RoastResponseSchema? {
        val templates = loadTemplates()

        // Filter by trigger + tone
        val candidates = templates.filter { it.trigger == trigger && it.tone == tone }
        if (candidates.isEmpty()) return null

        // Pick up to 3 templates avoiding recently used ones
        val selectedTemplates = pickMultipleTemplates(candidates, 3)

        // Build substitution map
        val playedSets = scores.filter { (a, b) -> a > 0 || b > 0 }
        val winners = determineWinners(players, scores)
        val losers = determineLosers(players, scores)
        val scoreString = playedSets.joinToString(", ") { (a, b) -> "$a-$b" }

        val substitutions = buildMap {
            put("{winner1}", winners.getOrElse(0) { players.getOrElse(0) { "" } })
            put("{winner2}", winners.getOrElse(1) { players.getOrElse(1) { "" } })
            put("{loser1}", losers.getOrElse(0) { players.getOrElse(2) { "" } })
            put("{loser2}", losers.getOrElse(1) { players.getOrElse(3) { "" } })
            put("{player1}", players.getOrElse(0) { "" })
            put("{player2}", players.getOrElse(1) { "" })
            put("{player3}", players.getOrElse(2) { "" })
            put("{player4}", players.getOrElse(3) { "" })
            put("{score}", scoreString)
            put("{set1}", playedSets.getOrElse(0) { 0 to 0 }.let { (a, b) -> "$a-$b" })
            put("{set2}", playedSets.getOrElse(1) { 0 to 0 }.let { (a, b) -> "$a-$b" })
            put("{set3}", playedSets.getOrElse(2) { 0 to 0 }.let { (a, b) -> "$a-$b" })
        }

        val cards = mutableListOf<RoastCardText>()
        for ((idx, template) in selectedTemplates.withIndex()) {
            val strings = template.strings[locale] ?: template.strings["en"] ?: continue
            var headline = applySubstitutions(strings.headline, substitutions)
            val bodyText = applySubstitutions(strings.body_text, substitutions)
            val whatsapp = applySubstitutions(strings.pre_filled_whatsapp, substitutions)
            var matchRecap = strings.match_recap?.let { applySubstitutions(it, substitutions) }
            var mvpLine = strings.mvp_line?.let { applySubstitutions(it, substitutions) }
            val rematchHype = strings.rematch_hype?.let { applySubstitutions(it, substitutions) }

            // Add subtle variety if templates are duplicated
            if (idx > 0) {
                val emojiPrefix = when (idx) {
                    1 -> "⭐ "
                    2 -> "⚡ "
                    else -> "🔥 "
                }
                headline = "$emojiPrefix$headline"
                matchRecap = matchRecap?.let { "$it (Alt View $idx)" }
                mvpLine = mvpLine?.let { "$it (Spicy Edition)" }
            }

            cards.add(
                RoastCardText(
                    headline = headline,
                    body_text = bodyText,
                    pre_filled_whatsapp = whatsapp,
                    match_recap = matchRecap,
                    mvp_line = mvpLine,
                    rematch_hype = rematchHype
                )
            )
        }

        if (cards.isEmpty()) return null

        // Generate player titles from pool
        val shuffledTitles = TITLE_POOL.shuffled()
        val playerTitles = PlayerTitles(
            player_1 = shuffledTitles[0],
            player_2 = shuffledTitles[1],
            player_3 = shuffledTitles[2],
            player_4 = shuffledTitles[3]
        )

        val firstCard = cards.first()
        return RoastResponseSchema(
            narrative_trigger_detected = trigger,
            player_titles_assigned = playerTitles,
            roast_cards = cards,
            match_recap = firstCard.match_recap ?: "",
            mvp_line = firstCard.mvp_line ?: "",
            rematch_hype = firstCard.rematch_hype ?: ""
        )
    }

    private fun pickMultipleTemplates(candidates: List<RoastTemplate>, count: Int): List<RoastTemplate> {
        val recentIds = getRecentIds()
        val fresh = candidates.filter { it.id !in recentIds }.shuffled()
        
        val selected = mutableListOf<RoastTemplate>()
        selected.addAll(fresh.take(count))
        
        if (selected.size < count) {
            val remainingCount = count - selected.size
            val used = candidates.filter { it.id !in selected.map { s -> s.id } }.shuffled()
            selected.addAll(used.take(remainingCount))
        }

        // Guarantee count by repeating candidates if we still don't have enough
        while (selected.size < count && candidates.isNotEmpty()) {
            selected.add(candidates.random())
        }
        
        selected.forEach { recordRecentId(it.id) }
        return selected
    }


    // --- Private helpers ---

    private fun loadTemplates(): List<RoastTemplate> {
        cachedTemplates?.let { return it }

        val json = try {
            context.assets.open("roast_templates.json").bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            "{\"version\":1,\"templates\":[]}"
        }

        val adapter = moshi.adapter(TemplateFile::class.java)
        val file = adapter.fromJson(json) ?: TemplateFile()
        cachedTemplates = file.templates
        return file.templates
    }

    /**
     * Picks a template avoiding recently used IDs.
     * If every candidate has been used recently, clears history and picks randomly.
     */
    private fun pickTemplate(candidates: List<RoastTemplate>): RoastTemplate {
        val recentIds = getRecentIds()
        val fresh = candidates.filter { it.id !in recentIds }

        val selected = if (fresh.isNotEmpty()) {
            fresh.random()
        } else {
            // All candidates exhausted — clear history and pick any
            clearRecentIds()
            candidates.random()
        }

        recordRecentId(selected.id)
        return selected
    }

    private fun getRecentIds(): Set<String> {
        val raw = prefs.getString(KEY_RECENT_IDS, "") ?: ""
        return if (raw.isBlank()) emptySet() else raw.split(",").toSet()
    }

    private fun recordRecentId(id: String) {
        val current = getRecentIds().toMutableList()
        current.add(id)
        // Keep only the last MAX_RECENT entries
        val trimmed = if (current.size > MAX_RECENT) {
            current.takeLast(MAX_RECENT)
        } else {
            current
        }
        prefs.edit().putString(KEY_RECENT_IDS, trimmed.joinToString(",")).apply()
    }

    private fun clearRecentIds() {
        prefs.edit().putString(KEY_RECENT_IDS, "").apply()
    }

    /**
     * Determines the two players on the winning team.
     * Team A = players[0]+players[1], Team B = players[2]+players[3].
     */
    private fun determineWinners(
        players: List<String>,
        scores: List<Pair<Int, Int>>
    ): List<String> {
        val playedSets = scores.filter { (a, b) -> a > 0 || b > 0 }
        var teamASets = 0
        var teamBSets = 0
        for ((a, b) in playedSets) {
            if (a > b) teamASets++ else if (b > a) teamBSets++
        }
        return if (teamASets >= teamBSets) {
            listOf(players.getOrElse(0) { "" }, players.getOrElse(1) { "" })
        } else {
            listOf(players.getOrElse(2) { "" }, players.getOrElse(3) { "" })
        }
    }

    private fun determineLosers(
        players: List<String>,
        scores: List<Pair<Int, Int>>
    ): List<String> {
        val playedSets = scores.filter { (a, b) -> a > 0 || b > 0 }
        var teamASets = 0
        var teamBSets = 0
        for ((a, b) in playedSets) {
            if (a > b) teamASets++ else if (b > a) teamBSets++
        }
        return if (teamASets >= teamBSets) {
            listOf(players.getOrElse(2) { "" }, players.getOrElse(3) { "" })
        } else {
            listOf(players.getOrElse(0) { "" }, players.getOrElse(1) { "" })
        }
    }

    private fun applySubstitutions(text: String, subs: Map<String, String>): String {
        var result = text
        for ((placeholder, value) in subs) {
            result = result.replace(placeholder, value)
        }
        return result
    }
}
