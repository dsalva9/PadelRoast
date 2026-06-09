package com.example.data

import android.content.Context
import java.util.Locale

class AppSettings(context: Context) {
    private val prefs = context.getSharedPreferences("padelroast_settings", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_APP_LOCALE = "app_locale"
        val SUPPORTED_LOCALES = listOf("system", "en", "es", "fr", "it", "nl", "sv")
    }

    var appLocale: String
        get() = prefs.getString(KEY_APP_LOCALE, "system") ?: "system"
        set(value) {
            prefs.edit().putString(KEY_APP_LOCALE, value).apply()
        }

    /**
     * Resolves the effective locale code.
     * If [appLocale] is "system", checks the device locale against [SUPPORTED_LOCALES].
     * Falls back to "en" when the device locale is not supported.
     */
    fun getEffectiveLocale(): String {
        if (appLocale != "system") return appLocale
        val deviceLocale = Locale.getDefault().language
        // drop(1) excludes "system" from the comparison
        return if (deviceLocale in SUPPORTED_LOCALES.drop(1)) deviceLocale else "en"
    }
}
