package com.example.data

import android.content.Context
import java.time.LocalDate

class UserTier(context: Context) {
    private val prefs = context.getSharedPreferences("padelroast_tier", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_IS_PRO = "is_pro_user"
        private const val KEY_AI_MONTHLY_COUNT = "ai_monthly_count"
        private const val KEY_LAST_MONTH_RESET = "last_month_reset"
        
        const val FREE_MONTHLY_LIMIT = 3
        const val PRO_PREMIUM_LIMIT = 25
    }

    val isProUser: Boolean get() = prefs.getBoolean(KEY_IS_PRO, false)

    /**
     * Returns true if the user can make another AI call.
     * Pro users are unlimited; free users get [FREE_MONTHLY_LIMIT] calls per month.
     */
    fun canUseAi(): Boolean {
        resetIfNewMonth()
        if (isProUser) return true
        return prefs.getInt(KEY_AI_MONTHLY_COUNT, 0) < FREE_MONTHLY_LIMIT
    }

    /**
     * Records one AI usage. Call this after a successful AI response.
     */
    fun recordAiUsage() {
        resetIfNewMonth()
        val current = prefs.getInt(KEY_AI_MONTHLY_COUNT, 0)
        prefs.edit().putInt(KEY_AI_MONTHLY_COUNT, current + 1).apply()
    }

    /**
     * Returns the total AI calls made this month.
     */
    fun getMonthlyAiCount(): Int {
        resetIfNewMonth()
        return prefs.getInt(KEY_AI_MONTHLY_COUNT, 0)
    }

    /**
     * Returns remaining AI calls.
     * For Pro users, this returns 999 (unlimited).
     * For Free users, it returns remaining out of [FREE_MONTHLY_LIMIT].
     */
    fun getRemainingAiCalls(): Int {
        if (isProUser) return 999
        resetIfNewMonth()
        val used = prefs.getInt(KEY_AI_MONTHLY_COUNT, 0)
        return (FREE_MONTHLY_LIMIT - used).coerceAtLeast(0)
    }

    /**
     * Returns the client tier string to send to the Edge Function.
     * Maps to: "free", "pro_premium", or "pro_eco".
     */
    fun getClientTier(): String {
        resetIfNewMonth()
        return if (isProUser) {
            val used = prefs.getInt(KEY_AI_MONTHLY_COUNT, 0)
            if (used < PRO_PREMIUM_LIMIT) "pro_premium" else "pro_eco"
        } else {
            "free"
        }
    }

    /**
     * Resets the monthly counter if the stored month is not current.
     */
    private fun resetIfNewMonth() {
        val now = LocalDate.now()
        val currentMonthKey = "${now.year}-${now.monthValue}" // e.g. "2026-6"
        val lastReset = prefs.getString(KEY_LAST_MONTH_RESET, null)
        if (lastReset != currentMonthKey) {
            prefs.edit()
                .putInt(KEY_AI_MONTHLY_COUNT, 0)
                .putString(KEY_LAST_MONTH_RESET, currentMonthKey)
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
