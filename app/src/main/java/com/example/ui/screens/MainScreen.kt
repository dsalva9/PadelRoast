package com.example.ui.screens

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.BanterViewModel
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: BanterViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) } // 0 = Banter Deck, 1 = Court Archives, 2 = Settings
    val context = LocalContext.current

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = SlateSurface,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .navigationBarsPadding() // Safely wrap system navigation bar inset height padding
            ) {
                // Tab 1: Banter Deck (Generate Roast)
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = androidx.compose.ui.res.stringResource(com.example.R.string.nav_log_match)
                        )
                    },
                    label = {
                        Text(
                            androidx.compose.ui.res.stringResource(com.example.R.string.nav_log_match),
                            fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = TextLight,
                        unselectedIconColor = TextMuted,
                        selectedTextColor = TextLight,
                        unselectedTextColor = TextMuted,
                        indicatorColor = PadelLime.copy(alpha = 0.3f)
                    ),
                    modifier = Modifier.testTag("nav_deck")
                )

                // Tab 2: Court Archives (History + ELO)
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = androidx.compose.ui.res.stringResource(com.example.R.string.nav_history)
                        )
                    },
                    label = {
                        Text(
                            androidx.compose.ui.res.stringResource(com.example.R.string.nav_history),
                            fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = TextLight,
                        unselectedIconColor = TextMuted,
                        selectedTextColor = TextLight,
                        unselectedTextColor = TextMuted,
                        indicatorColor = PadelLime.copy(alpha = 0.3f)
                    ),
                    modifier = Modifier.testTag("nav_history")
                )

                // Tab 3: Settings
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = androidx.compose.ui.res.stringResource(com.example.R.string.settings_tab)
                        )
                    },
                    label = {
                        Text(
                            androidx.compose.ui.res.stringResource(com.example.R.string.settings_tab),
                            fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = TextLight,
                        unselectedIconColor = TextMuted,
                        selectedTextColor = TextLight,
                        unselectedTextColor = TextMuted,
                        indicatorColor = PadelLime.copy(alpha = 0.3f)
                    ),
                    modifier = Modifier.testTag("nav_settings")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SlateDark)
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> BanterDeckScreen(viewModel = viewModel)
                1 -> MatchHistoryScreen(
                    viewModel = viewModel,
                    onNavigateToDeck = { selectedTab = 0 }
                )
                2 -> {
                    val currentLocale by viewModel.appLocale.collectAsState()
                    val isProUser by viewModel.isProUser.collectAsState()
                    val remainingAiCalls by viewModel.remainingAiCalls.collectAsState()

                    SettingsScreen(
                        currentLocale = currentLocale,
                        onLocaleChanged = { newLocale ->
                            viewModel.updateLocale(newLocale)
                            // Recreate the Activity to apply the new locale configuration
                            (context as? Activity)?.recreate()
                        },
                        isProUser = isProUser,
                        remainingAiCalls = remainingAiCalls,
                        onUpgradeClicked = {
                            viewModel.upgradeToPro()
                        }
                    )
                }
            }
        }
    }
}
