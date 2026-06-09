package com.example.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PadelRepository(private val database: AppDatabase) {
    private val matchDao = database.matchDao()
    private val playerDao = database.playerDao()

    val allMatches: Flow<List<MatchEntity>> = matchDao.getAllMatches()
    val allPlayers: Flow<List<PlayerEntity>> = playerDao.getAllPlayers()

    suspend fun getMostRecentMatch(): MatchEntity? = withContext(Dispatchers.IO) {
        matchDao.getMostRecentMatch()
    }

    suspend fun searchPlayerNames(query: String): List<String> = withContext(Dispatchers.IO) {
        if (query.isBlank()) emptyList() else playerDao.searchPlayerNames("%$query%")
    }

    /**
     * Inserts a match into the local database, calculates set winners,
     * updates Elo ratings and play records for all 4 players in a single transaction.
     */
    suspend fun logMatchAndProcessElo(
        player1: String,
        player2: String,
        player3: String,
        player4: String,
        set1A: Int, set1B: Int,
        set2A: Int, set2B: Int,
        set3A: Int, set3B: Int,
        tone: String,
        trigger: String,
        responseText: String,
        isTemplate: Boolean = false,
        customContext: String? = null
    ): Long = withContext(Dispatchers.IO) {
        
        // 1. Save match record
        val match = MatchEntity(
            date = System.currentTimeMillis(),
            set1_teamA = set1A, set1_teamB = set1B,
            set2_teamA = set2A, set2_teamB = set2B,
            set3_teamA = set3A, set3_teamB = set3B,
            player1 = player1.trim(),
            player2 = player2.trim(),
            player3 = player3.trim(),
            player4 = player4.trim(),
            tone = tone,
            trigger_detected = trigger,
            gemini_response_json = responseText,
            isTemplate = isTemplate,
            customContext = customContext
        )
        val matchId = matchDao.insertMatch(match)

        // 2. Determine match winner
        var teamASets = 0
        var teamBSets = 0

        if (set1A > set1B) teamASets++ else if (set1B > set1A) teamBSets++
        if (set2A > set2B) teamASets++ else if (set2B > set2A) teamBSets++
        // Check if set 3 is actually played (usually not 0-0 unless there is a set result of 0-0 which is invalid in tennis list)
        if (set3A > 0 || set3B > 0) {
            if (set3A > set3B) teamASets++ else if (set3B > set3A) teamBSets++
        }

        val teamAWon = teamASets > teamBSets

        // 3. Process each player's stats
        val teamAPlayers = listOf(player1.trim(), player2.trim())
        val teamBPlayers = listOf(player3.trim(), player4.trim())

        // Team A Players (P1, P2)
        teamAPlayers.forEach { name ->
            if (name.isNotBlank()) {
                val dbPlayer = playerDao.getPlayerByName(name)
                val isWinner = teamAWon
                val newElo = (dbPlayer?.current_elo ?: 1000) + (if (isWinner) 32 else -32)
                val playerRecord = PlayerEntity(
                    id = dbPlayer?.id ?: 0,
                    name = name,
                    matches_played = (dbPlayer?.matches_played ?: 0) + 1,
                    matches_won = (dbPlayer?.matches_won ?: 0) + (if (isWinner) 1 else 0),
                    current_elo = if (newElo < 100) 100 else newElo // Prevent Elo from falling exceptionally low
                )
                playerDao.insertPlayer(playerRecord)
            }
        }

        // Team B Players (P3, P4)
        teamBPlayers.forEach { name ->
            if (name.isNotBlank()) {
                val dbPlayer = playerDao.getPlayerByName(name)
                val isWinner = !teamAWon
                val newElo = (dbPlayer?.current_elo ?: 1000) + (if (isWinner) 32 else -32)
                val playerRecord = PlayerEntity(
                    id = dbPlayer?.id ?: 0,
                    name = name,
                    matches_played = (dbPlayer?.matches_played ?: 0) + 1,
                    matches_won = (dbPlayer?.matches_won ?: 0) + (if (isWinner) 1 else 0),
                    current_elo = if (newElo < 100) 100 else newElo
                )
                playerDao.insertPlayer(playerRecord)
            }
        }

        matchId
    }
}
