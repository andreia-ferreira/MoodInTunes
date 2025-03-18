package net.penguin.moodintunes

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DiscoverToPlaylistTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MoodInTunesMainActivity>()

    @Test
    fun navigateToPlaylistAndAddToFavorites() {
        composeTestRule.onNodeWithText("Happy").performClick()

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithContentDescription("Loading").fetchSemanticsNodes().isEmpty()
        }
        composeTestRule.onNodeWithText("Happy Hits").performClick()

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithContentDescription("Loading").fetchSemanticsNodes().isEmpty()
        }
        composeTestRule.onNodeWithContentDescription("Save to collection").performClick()
        composeTestRule.waitUntil(timeoutMillis = 2_000) {
            composeTestRule.onAllNodesWithContentDescription("Save to collection").fetchSemanticsNodes().isEmpty()
        }
        composeTestRule.onNodeWithContentDescription("Remove from collection").assertExists()
    }
}