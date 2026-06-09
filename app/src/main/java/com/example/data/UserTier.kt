package com.example.data

import android.content.Context
import java.time.LocalDate

class UserTier(context: Context) {
    private val prefs = context.getSharedPreferences("padelroast_tier", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_IS_PRO = "is_pro_user"
        private const val KEY_DAILY_COUNT = "daily_ai_count"
        private const val KEY_LAST_RESET_DATE = "last_reset_date"
        private const val DAILY_AI_LIMIT = 3
    }

    val isProUser: Boolean get() = prefs.getBoolean(KEY_IS_PRO, false)

    /**
     * Returns true if the user can make another AI call.
     * Pro users are unlimited; free users get [DAILY_AI_LIMIT] calls per day.
     */
    fun canUseAi(): Boolean {
        if (isProUser) return true
        resetIfNewDay()
        return prefs.getInt(KEY_DAILY_COUNT, 0) < DAILY_AI_LIMIT
    }

    /**
     * Records one AI usage. Call this after a successful AI response.
     */
    fun recordAiUsage() {
        resetIfNewDay()
        val current = prefs.getInt(KEY_DAILY_COUNT, 0)
        prefs.edit().putInt(KEY_DAILY_COUNT, current + 1).apply()
    }

    /**
     * Returns remaining AI calls for UI display.
     * Pro users get 999 (effectively unlimited).
     */
    fun getRemainingAiCalls(): Int {
        if (isProUser) return 999
        resetIfNewDay()
        val used = prefs.getInt(KEY_DAILY_COUNT, 0)
        return (DAILY_AI_LIMIT - used).coerceAtLeast(0)
    }

    /**
     * Resets the daily counter if the stored date is not today.
     */
    private fun resetIfNewDay() {
        val today = LocalDate.now().toString() // e.g. "2026-06-08"
        val lastReset = prefs.getString(KEY_LAST_RESET_DATE, null)
        if (lastReset != today) {
            prefs.edit()
                .putInt(KEY_DAILY_COUNT, 0)
                .putString(KEY_LAST_RESET_DATE, today)
                .apply()
        }
    }

    /**
     * Sets the pro/free status. Call this from your IAP purchase flow.
     */
    fun setProStatus(isPro: Boolean) {
        prefs.edit().putBoolean(KEY_IS_PRO, isPro).apply()
    }
}
