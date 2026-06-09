package com.example

import android.app.Application
import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.AppDatabase
import com.example.data.PadelRepository
import com.example.ui.BanterViewModel
import com.example.ui.screens.MainScreen
import com.example.ui.theme.MyApplicationTheme
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel8, sdk = [36])
class GreetingScreenshotTest {

  @get:Rule val composeTestRule = createComposeRule()

  @Test
  fun main_screen_screenshot() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
        .allowMainThreadQueries()
        .build()
    val repo = PadelRepository(db)
    val app = context.applicationContext as Application
    val viewModel = BanterViewModel(app, repo)

    composeTestRule.setContent {
      MyApplicationTheme {
        MainScreen(viewModel = viewModel)
      }
    }

    composeTestRule.onRoot().captureRoboImage(filePath = "src/test/screenshots/main_screen.png")
  }

  @Test
  fun match_history_populated_screenshot() = runBlocking {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
        .allowMainThreadQueries()
        .build()
    val repo = PadelRepository(db)
    val app = context.applicationContext as Application
    val viewModel = BanterViewModel(app, repo)

    // Log a sample match to verify DB integration, Moshi JSON parsing, and list layouts
    repo.logMatchAndProcessElo(
        player1 = "Carlos Alcaraz",
        player2 = "Galan",
        player3 = "Coello",
        player4 = "Tapia",
        set1A = 6, set1B = 4,
        set2A = 3, set2B = 6,
        set3A = 7, set3B = 5,
        tone = "Savage",
        trigger = "Choke",
        responseText = """{
          "narrative_trigger_detected": "Choke",
          "player_titles_assigned": {
            "player_1": "Underdog",
            "player_2": "Baseline Hugger",
            "player_3": "Fence Smasher",
            "player_4": "No-Show"
          },
          "roast_cards": [
            {
              "headline": "THE 45-MINUTE FENCE APPRECIATOR",
              "body_text": "Carlos Alcaraz played an absolute shocker, hitting the fence 20 times in a row.",
              "pre_filled_whatsapp": "What a choke! Galan tried his best but card was dropped!"
            }
          ],
          "match_recap": "An epic match indeed. Carlos kept trying. Tapia won the day.",
          "mvp_line": "Tapia - The Fence Smasher",
          "rematch_hype": "Prepare yourselves for the rematch."
        }""".trimIndent()
    )

    composeTestRule.setContent {
      MyApplicationTheme {
        MainScreen(viewModel = viewModel)
      }
    }

    // Since we added a match, the viewmodel should have standard populated items.
    // Let's first make sure it doesn't crash on either screen state
    composeTestRule.onRoot().captureRoboImage(filePath = "src/test/screenshots/main_screen_populated.png")
  }
}
