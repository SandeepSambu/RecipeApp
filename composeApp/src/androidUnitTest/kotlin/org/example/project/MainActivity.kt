package org.example.project

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.BeforeTest


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class MainActivity {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @BeforeTest
    fun setup() {
        val myViewModel = MyViewModel()
        myViewModel.fetchAndShowData()
        composeTestRule.setContent {
            MyApp(navController = rememberNavController(), myViewModel = myViewModel)
        }
    }

    @Test
    fun searchTextField_shouldUpdateWhenUserTypes() {

        val searchText = "Pasta"

        // Act: Enter search text
        composeTestRule
            .onNodeWithTag("TextField") // Ensure your TextField has a test tag set as "TextField"
            .performTextInput(searchText)

        composeTestRule.waitForIdle() // Wait for UI to update

        Thread.sleep(1000)

        // Assert: Verify the search text is updated in the TextField
        composeTestRule
            .onNodeWithTag("TextField")
            .assertTextEquals(searchText)
    }

    @Test
    fun navController_shouldNavigateToCorrectScreen() {
        composeTestRule.waitForIdle()
        Thread.sleep(1000)
        // Act: Click on a recipe item
        composeTestRule
            .onNodeWithText("Classic Margherita Pizza") // This text should match a recipe name in your composable
            .performClick()

        composeTestRule.waitForIdle() // Wait for navigation to complete

        Thread.sleep(1000)
        // Assert: Verify the recipe detail screen is displayed
        composeTestRule
            .onNodeWithText("Classic Margherita Pizza")
            .assertExists()

        // Act: Navigate back to the home screen by clicking the "Home" button (or icon)
        composeTestRule
            .onNodeWithContentDescription("Home")
            .performClick()

        composeTestRule.waitForIdle() // Wait for navigation to complete

        Thread.sleep(1000)
        // Assert: Verify that the home screen with "Cuisines" is displayed
        composeTestRule
            .onNodeWithText("Cuisines")
            .assertExists()
    }

    @Test
    fun testSearchFunctionality_filtersRecipesByTitle() {
        val searchText = "chocolate"

        // Act: Input search text
        composeTestRule
            .onNodeWithTag("TextField")
            .performTextInput(searchText)

        composeTestRule.waitForIdle() // Wait for search results to update

        // Assert: Verify the search results are filtered
        composeTestRule
            .onNodeWithText("Chocolate Chip Cookies")
            .assertExists() // Assert this recipe exists in filtered results

        composeTestRule
            .onNodeWithText("Classic Margherita Pizza")
            .assertDoesNotExist() // Assert this recipe doesn't exist in filtered results
    }
}