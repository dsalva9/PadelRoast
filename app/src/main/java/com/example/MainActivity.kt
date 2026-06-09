package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.data.AppDatabase
import com.example.data.PadelRepository
import com.example.data.UserTier
import com.example.data.AppSettings
import com.example.data.TemplateEngine
import com.example.ui.BanterViewModel
import com.example.ui.BanterViewModelFactory
import com.example.ui.screens.MainScreen
import com.example.ui.theme.MyApplicationTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // 1. Initialize local Room DB, repository & logic dependencies
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = PadelRepository(database)
        val userTier = UserTier(applicationContext)
        val appSettings = AppSettings(applicationContext)
        val templateEngine = TemplateEngine(applicationContext)
        
        // 2. Apply app locale override before activity creation
        val effectiveLocale = appSettings.getEffectiveLocale()
        applyLocale(effectiveLocale)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // 3. Setup ViewModel with custom factory support
        val viewModel = ViewModelProvider(
            this,
            BanterViewModelFactory(application, repository, userTier, appSettings, templateEngine)
        )[BanterViewModel::class.java]

        setContent {
            MyApplicationTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }

    private fun applyLocale(localeTag: String) {
        val locale = if (localeTag == "system") {
            Locale.getDefault()
        } else {
            Locale(localeTag)
        }
        Locale.setDefault(locale)
        val resources = resources
        val configuration = resources.configuration
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}
