package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.BuildConfig
import com.example.api.*
import com.example.data.*
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

sealed class GenerationState {
    object Idle : GenerationState()
    object Loading : GenerationState()
    data class Success(val roast: RoastResponseSchema, val matchId: Long, val isTemplate: Boolean = false) : GenerationState()
    data class Error(val message: String) : GenerationState()
}

class BanterViewModel(
    application: Application,
    private val repository: PadelRepository,
    val userTier: UserTier,
    val appSettings: AppSettings,
    val templateEngine: TemplateEngine
) : AndroidViewModel(application) {

    // --- Inputs state ---
    val player1 = MutableStateFlow("")
    val player2 = MutableStateFlow("")
    val player3 = MutableStateFlow("")
    val player4 = MutableStateFlow("")

    val set1A = MutableStateFlow("6")
    val set1B = MutableStateFlow("4")
    val set2A = MutableStateFlow("6")
    val set2B = MutableStateFlow("4")
    val set3A = MutableStateFlow("0")
    val set3B = MutableStateFlow("0")

    val selectedTone = MutableStateFlow("Balanced") // Casual / Balanced / Savage / SAVAGE!!
    val selectedTags = MutableStateFlow<Set<String>>(emptySet()) // Demolished, Choked, Lucky Win, Comeback
    val customContext = MutableStateFlow("")

    // Auto-suggest suggestions for each of the 4 text fields
    val suggestionsP1 = MutableStateFlow<List<String>>(emptyList())
    val suggestionsP2 = MutableStateFlow<List<String>>(emptyList())
    val suggestionsP3 = MutableStateFlow<List<String>>(emptyList())
    val suggestionsP4 = MutableStateFlow<List<String>>(emptyList())

    // --- Output results ---
    val generationState = MutableStateFlow<GenerationState>(GenerationState.Idle)
    val currentRoastIndex = MutableStateFlow(0)
    val isDetailsExpanded = MutableStateFlow(false)

    // --- Tier & settings state ---
    private val _remainingAiCalls = MutableStateFlow(userTier.getRemainingAiCalls())
    val remainingAiCalls: StateFlow<Int> = _remainingAiCalls.asStateFlow()

    private val _isProUser = MutableStateFlow(userTier.isProUser)
    val isProUser: StateFlow<Boolean> = _isProUser.asStateFlow()

    private val _monthlyAiCount = MutableStateFlow(userTier.getMonthlyAiCount())
    val monthlyAiCount: StateFlow<Int> = _monthlyAiCount.asStateFlow()

    private val _appLocale = MutableStateFlow(appSettings.appLocale)
    val appLocale: StateFlow<String> = _appLocale.asStateFlow()

    // Historical streams
    val matchHistory: StateFlow<List<MatchEntity>> = repository.allItemsFlowIfAny()
    val leaderboard: StateFlow<List<PlayerEntity>> = repository.allPlayers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        // Automatically query auto-suggestions on composition/changes
        setupAutoSuggestions()
    }

    private fun PadelRepository.allItemsFlowIfAny(): StateFlow<List<MatchEntity>> {
        return allMatches.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }

    private fun setupAutoSuggestions() {
        viewModelScope.launch {
            player1.debounce(200).collectLatest { query ->
                suggestionsP1.value = if (query.isNotBlank()) repository.searchPlayerNames(query) else emptyList()
            }
        }
        viewModelScope.launch {
            player2.debounce(200).collectLatest { query ->
                suggestionsP2.value = if (query.isNotBlank()) repository.searchPlayerNames(query) else emptyList()
            }
        }
        viewModelScope.launch {
            player3.debounce(200).collectLatest { query ->
                suggestionsP3.value = if (query.isNotBlank()) repository.searchPlayerNames(query) else emptyList()
            }
        }
        viewModelScope.launch {
            player4.debounce(200).collectLatest { query ->
                suggestionsP4.value = if (query.isNotBlank()) repository.searchPlayerNames(query) else emptyList()
            }
        }
    }

    /**
     * Fills players from the most recent match.
     */
    fun prefillSameLineup() {
        viewModelScope.launch {
            val lastMatch = repository.getMostRecentMatch()
            if (lastMatch != null) {
                player1.value = lastMatch.player1
                player2.value = lastMatch.player2
                player3.value = lastMatch.player3
                player4.value = lastMatch.player4
            }
        }
    }

    fun toggleTag(tag: String) {
        val currentSet = selectedTags.value
        if (currentSet.contains(tag)) {
            selectedTags.value = currentSet - tag
        } else {
            selectedTags.value = currentSet + tag
        }
    }

    /**
     * Reshares an old match roast, populating states and showing the card directly.
     */
    fun selectMatchToView(match: MatchEntity, onNavigateToDeck: () -> Unit) {
        player1.value = match.player1
        player2.value = match.player2
        player3.value = match.player3
        player4.value = match.player4
        set1A.value = match.set1_teamA.toString()
        set1B.value = match.set1_teamB.toString()
        set2A.value = match.set2_teamA.toString()
        set2B.value = match.set2_teamB.toString()
        set3A.value = match.set3_teamA.toString()
        set3B.value = match.set3_teamB.toString()
        selectedTone.value = match.tone
        customContext.value = match.customContext ?: ""
        
        try {
            val adapter = RetrofitClient.moshiParser.adapter(RoastResponseSchema::class.java)
            val roastObj = adapter.fromJson(match.gemini_response_json)
            if (roastObj != null) {
                generationState.value = GenerationState.Success(roastObj, match.id.toLong(), isTemplate = match.isTemplate)
                currentRoastIndex.value = 0
                isDetailsExpanded.value = false
                onNavigateToDeck()
            } else {
                generationState.value = GenerationState.Error("Could not parse cached roast card.")
                onNavigateToDeck()
            }
        } catch (e: Exception) {
            generationState.value = GenerationState.Error("Parsing error: ${e.localizedMessage}")
            onNavigateToDeck()
        }
    }

    /**
     * Filters match history to find previous encounters involving the exact same 4 players,
     * serializing their dates, scores, and prior roast details.
     */
    fun getRivalryHistoryContext(p1: String, p2: String, p3: String, p4: String): String {
        val currentSet = setOf(p1.trim().lowercase(), p2.trim().lowercase(), p3.trim().lowercase(), p4.trim().lowercase())
        val encounters = matchHistory.value.filter { m ->
            val matchSet = setOf(m.player1.lowercase(), m.player2.lowercase(), m.player3.lowercase(), m.player4.lowercase())
            matchSet == currentSet
        }.take(5) // Limit to 5 encounters to stay within prompt token limits

        if (encounters.isEmpty()) return ""

        return encounters.joinToString("\n") { m ->
            val dateStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(m.date))
            val previousRoastsText = try {
                val adapter = RetrofitClient.moshiParser.adapter(RoastResponseSchema::class.java)
                val response = adapter.fromJson(m.gemini_response_json)
                response?.roast_cards?.joinToString("; ") { card ->
                    "\"${card.headline}: ${card.body_text}\""
                } ?: ""
            } catch (e: Exception) { "" }

            "Match on $dateStr: ${m.player1} & ${m.player2} vs ${m.player3} & ${m.player4} " +
            "(Scores: Set1: ${m.set1_teamA}-${m.set1_teamB}, Set2: ${m.set2_teamA}-${m.set2_teamB}" +
            (if (m.set3_teamA > 0 || m.set3_teamB > 0) ", Set3: ${m.set3_teamA}-${m.set3_teamB}" else "") + ") " +
            "Previous Roasts: $previousRoastsText"
        }
    }

    /**
     * Generates a match roast using the secure 3-tier content flow.
     */
    fun generateRoast() {
        if (player1.value.isBlank() || player2.value.isBlank() || player3.value.isBlank() || player4.value.isBlank()) {
            generationState.value = GenerationState.Error("All 4 player fields must be filled!")
            return
        }

        val p1 = player1.value.trim()
        val p2 = player2.value.trim()
        val p3 = player3.value.trim()
        val p4 = player4.value.trim()

        val s1A = set1A.value.toIntOrNull() ?: 6
        val s1B = set1B.value.toIntOrNull() ?: 4
        val s2A = set2A.value.toIntOrNull() ?: 6
        val s2B = set2B.value.toIntOrNull() ?: 4
        val s3A = set3A.value.toIntOrNull() ?: 0
        val s3B = set3B.value.toIntOrNull() ?: 0

        val scoresPairs = listOf(s1A to s1B, s2A to s2B, s3A to s3B)
        val scoresLists = listOf(listOf(s1A, s1B), listOf(s2A, s2B), listOf(s3A, s3B))
        val trigger = TriggerDetector.detect(scoresPairs)
        val tone = selectedTone.value
        val customCtxText = customContext.value.trim().ifBlank { null }

        generationState.value = GenerationState.Loading
        currentRoastIndex.value = 0
        isDetailsExpanded.value = false

        // 1. Pro Lock Check
        if (tone == "SAVAGE!!" && !userTier.isProUser) {
            generationState.value = GenerationState.Error("SAVAGE!! is Pro-only. Upgrade to unlock! 💀")
            return
        }

        viewModelScope.launch {
            try {
                // 2. Online AI path (if quota matches)
                if (userTier.canUseAi()) {
                    // Extract match rivalry history
                    val history = getRivalryHistoryContext(p1, p2, p3, p4)

                    val response = RetrofitClient.service.generateRoast(
                        authHeader = "Bearer ${BuildConfig.SUPABASE_ANON_KEY}",
                        apiKey = BuildConfig.SUPABASE_ANON_KEY,
                        request = RoastRequest(
                            players = listOf(p1, p2, p3, p4),
                            scores = scoresLists,
                            tone = tone.lowercase(),
                            trigger = trigger,
                            tags = selectedTags.value.toList(),
                            history_context = history,
                            tier = userTier.getClientTier(),
                            locale = appSettings.getEffectiveLocale(),
                            custom_context = customCtxText
                        )
                    )

                    userTier.recordAiUsage()
                    refreshTierState()

                    // Serialize response to JSON for database caching
                    val adapter = RetrofitClient.moshiParser.adapter(RoastResponseSchema::class.java)
                    val responseJson = adapter.toJson(response)

                    val matchId = repository.logMatchAndProcessElo(
                        player1 = p1, player2 = p2, player3 = p3, player4 = p4,
                        set1A = s1A, set1B = s1B, set2A = s2A, set2B = s2B, set3A = s3A, set3B = s3B,
                        tone = tone,
                        trigger = trigger,
                        responseText = responseJson,
                        isTemplate = false,
                        customContext = customCtxText
                    )

                    generationState.value = GenerationState.Success(response, matchId, isTemplate = false)
                } else {
                    // 3. Offline template fallback (quota exhausted)
                    val effectiveTone = if (tone == "SAVAGE!!" && !userTier.isProUser) "savage" else tone.lowercase()
                    val templateResponse = templateEngine.generate(
                        trigger = trigger,
                        tone = effectiveTone,
                        players = listOf(p1, p2, p3, p4),
                        scores = scoresPairs,
                        locale = appSettings.getEffectiveLocale()
                    ) ?: throw IllegalStateException("Template not found for trigger=$trigger tone=$effectiveTone")

                    val adapter = RetrofitClient.moshiParser.adapter(RoastResponseSchema::class.java)
                    val responseJson = adapter.toJson(templateResponse)

                    val matchId = repository.logMatchAndProcessElo(
                        player1 = p1, player2 = p2, player3 = p3, player4 = p4,
                        set1A = s1A, set1B = s1B, set2A = s2A, set2B = s2B, set3A = s3A, set3B = s3B,
                        tone = tone,
                        trigger = trigger,
                        responseText = responseJson,
                        isTemplate = true,
                        customContext = customCtxText
                    )

                    generationState.value = GenerationState.Success(templateResponse, matchId, isTemplate = true)
                }
            } catch (e: Exception) {
                // Online failure fallback to Template Engine
                try {
                    val effectiveTone = if (tone == "SAVAGE!!" && !userTier.isProUser) "savage" else tone.lowercase()
                    val templateResponse = templateEngine.generate(
                        trigger = trigger,
                        tone = effectiveTone,
                        players = listOf(p1, p2, p3, p4),
                        scores = scoresPairs,
                        locale = appSettings.getEffectiveLocale()
                    )
                    if (templateResponse != null) {
                        val adapter = RetrofitClient.moshiParser.adapter(RoastResponseSchema::class.java)
                        val responseJson = adapter.toJson(templateResponse)

                        val matchId = repository.logMatchAndProcessElo(
                            player1 = p1, player2 = p2, player3 = p3, player4 = p4,
                            set1A = s1A, set1B = s1B, set2A = s2A, set2B = s2B, set3A = s3A, set3B = s3B,
                            tone = tone,
                            trigger = trigger,
                            responseText = responseJson,
                            isTemplate = true,
                            customContext = customCtxText
                        )
                        generationState.value = GenerationState.Success(templateResponse, matchId, isTemplate = true)
                    } else {
                        generationState.value = GenerationState.Error("Generation failed: ${e.localizedMessage}. Template fallback failed.")
                    }
                } catch (fallbackEx: Exception) {
                    generationState.value = GenerationState.Error("Generation failed: ${e.localizedMessage}. Template exception: ${fallbackEx.localizedMessage}")
                }
            }
        }
    }

    fun updateLocale(locale: String) {
        appSettings.appLocale = locale
        _appLocale.value = locale
    }

    fun upgradeToPro() {
        userTier.setProStatus(true)
        refreshTierState()
    }

    fun refreshTierState() {
        _isProUser.value = userTier.isProUser
        _remainingAiCalls.value = userTier.getRemainingAiCalls()
        _monthlyAiCount.value = userTier.getMonthlyAiCount()
    }

    fun clearState() {
        generationState.value = GenerationState.Idle
    }
}

class BanterViewModelFactory(
    private val application: Application,
    private val repository: PadelRepository,
    private val userTier: UserTier,
    private val appSettings: AppSettings,
    private val templateEngine: TemplateEngine
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BanterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BanterViewModel(application, repository, userTier, appSettings, templateEngine) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
