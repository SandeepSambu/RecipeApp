package org.example.project

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToNode
import androidx.navigation.testing.TestNavHostController
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.BeforeTest
import kotlin.test.Test

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class RecipeDetailScreen {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController : TestNavHostController
    private lateinit var myViewModel: MyViewModel
    private lateinit var favouriteRecipes: FavouriteRecipes

    @BeforeTest
    fun setup() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            myViewModel = MyViewModel()
            favouriteRecipes = FavouriteRecipes()
            myViewModel.fetchAndShowData()
            RecipeDetailScreen(
                recipeId = "1",
                navController = navController,
                myViewModel = myViewModel,
                favouriteRecipes = favouriteRecipes
            )
        }
    }

    @Test
    fun test_favouriteIcon() {
        Thread.sleep(2000)
        composeTestRule
            .onNodeWithText("Classic Margherita Pizza")
            .assertExists()
    }

    @Test
    fun test_ToggleFavouriteIcon() {
        Thread.sleep(2000)

        composeTestRule
            .onNodeWithTag("FavouriteIcon")
            .performClick()

        composeTestRule
            .onNodeWithContentDescription("YellowStar")
            .assertExists()
    }
}