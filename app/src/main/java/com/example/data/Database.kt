package com.example.data

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "matches")
data class MatchEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Long,
    val set1_teamA: Int,
    val set1_teamB: Int,
    val set2_teamA: Int,
    val set2_teamB: Int,
    val set3_teamA: Int,
    val set3_teamB: Int,
    val player1: String, // Team A Player 1
    val player2: String, // Team A Player 2
    val player3: String, // Team B Player 1
    val player4: String, // Team B Player 2
    val tone: String,
    val trigger_detected: String,
    val gemini_response_json: String // Strict JSON format of generated roast details
)

@Entity(
    tableName = "players",
    indices = [Index(value = ["name"], unique = true)]
)
data class PlayerEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val matches_played: Int = 0,
    val matches_won: Int = 0,
    val current_elo: Int = 1000
)

@Dao
interface MatchDao {
    @Query("SELECT * FROM matches ORDER BY date DESC")
    fun getAllMatches(): Flow<List<MatchEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatch(match: MatchEntity): Long

    @Query("SELECT * FROM matches ORDER BY date DESC LIMIT 1")
    suspend fun getMostRecentMatch(): MatchEntity?
}

@Dao
interface PlayerDao {
    @Query("SELECT * FROM players ORDER BY current_elo DESC")
    fun getAllPlayers(): Flow<List<PlayerEntity>>

    @Query("SELECT * FROM players WHERE name = :name LIMIT 1")
    suspend fun getPlayerByName(name: String): PlayerEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: PlayerEntity): Long

    @Update
    suspend fun updatePlayer(player: PlayerEntity)

    @Query("SELECT name FROM players WHERE name LIKE :query LIMIT 5")
    suspend fun searchPlayerNames(query: String): List<String>
}

@Database(entities = [MatchEntity::class, PlayerEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun matchDao(): MatchDao
    abstract fun playerDao(): PlayerDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "padelroast_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
