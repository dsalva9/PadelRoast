package com.example.data

object TriggerDetector {

    /**
     * Classifies a match into a narrative trigger based on set scores.
     *
     * @param scores List of (TeamA, TeamB) scores per set. Typically 2-3 entries.
     * @param gameType Currently only "standard" (2v2 best-of-3) is implemented.
     * @return One of: "bagel", "comeback", "choke", "close", "blowout", "dominant", "upset"
     */
    fun detect(scores: List<Pair<Int, Int>>, gameType: String = "standard"): String {
        if (gameType != "standard") return "upset"

        // Filter out non-played sets (0-0)
        val playedSets = scores.filter { (a, b) -> a > 0 || b > 0 }
        if (playedSets.isEmpty()) return "upset"

        // Count sets won by each side
        var teamASets = 0
        var teamBSets = 0
        for ((a, b) in playedSets) {
            if (a > b) teamASets++ else if (b > a) teamBSets++
        }

        val totalSetsPlayed = playedSets.size

        // 1. Bagel — any played set is 6-0 or 0-6
        if (playedSets.any { (a, b) -> (a == 6 && b == 0) || (a == 0 && b == 6) }) {
            return "bagel"
        }

        // For comeback / choke detection we need at least the first set decided
        val firstSet = playedSets.firstOrNull()
        val firstSetWinner = when {
            firstSet == null -> null
            firstSet.first > firstSet.second -> "A"
            firstSet.second > firstSet.first -> "B"
            else -> null // drawn set shouldn't happen, but handle gracefully
        }

        val matchWinner = when {
            teamASets >= 2 -> "A"
            teamBSets >= 2 -> "B"
            else -> null
        }

        // 2. Comeback — lost the first set but won the match (requires 3 sets)
        if (totalSetsPlayed >= 3 && firstSetWinner != null && matchWinner != null && firstSetWinner != matchWinner) {
            return "comeback"
        }

        // 3. Choke — won the first set but lost the match.
        // In best-of-3, comeback and choke describe the same 3-set reversal from opposite
        // perspectives. Since comeback has higher priority and the condition
        // (firstSetWinner != matchWinner) already fires above, "choke" is unreachable here.
        // If a future game type needs them separated, add the logic here.

        // 4. Close — tiebreak set (7-6, 6-7) or all sets decided by ≤1 game
        val hasTiebreak = playedSets.any { (a, b) ->
            (a == 7 && b == 6) || (a == 6 && b == 7)
        }
        val allCloseMargin = playedSets.all { (a, b) ->
            kotlin.math.abs(a - b) <= 1
        }
        if (hasTiebreak || allCloseMargin) {
            return "close"
        }

        // 5. Blowout — clean 2-0 win with every set margin ≥ 3
        if (totalSetsPlayed == 2 && matchWinner != null) {
            val allLargeMargin = playedSets.all { (a, b) -> kotlin.math.abs(a - b) >= 3 }
            if (allLargeMargin) return "blowout"
        }

        // 6. Dominant — clean 2-0 win (any margin)
        if (totalSetsPlayed == 2 && matchWinner != null) {
            return "dominant"
        }

        // 7. Upset — fallback for 3-set matches or anything else
        return "upset"
    }
}
