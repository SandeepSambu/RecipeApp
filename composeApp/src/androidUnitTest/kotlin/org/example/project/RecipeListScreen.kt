package org.example.project

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.BeforeTest

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class RecipeListScreen {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController
    private val myViewModel = MyViewModel()

//    @BeforeTest
//    fun setUpNavHost() {
//        composeTestRule.setContent {
//            myViewModel.fetchAndShowData()
//            // Set up TestNavHostController
//            navController = TestNavHostController(LocalContext.current)
//            navController.navigatorProvider.addNavigator(ComposeNavigator())
//            NavHost(navController = navController, startDestination = "recipe_list") {
//                composable("recipe_list") {
//                    RecipeListScreen(
//                        navController = navController,
//                        favouriteRecipes = FavouriteRecipes(),
//                        searchText = "",
//                        isDarkTheme = true,
//                        paddingValues = PaddingValues(),
//                        myViewModel = myViewModel
//                    )
//                }
//                composable("recipe_detail/{recipeId}") { backstackEntry ->
//                    val recipeId = backstackEntry.arguments?.getString("recipeId")
//                    RecipeDetailScreen(
//                        recipeId = recipeId!!,
//                        navController = navController,
//                        myViewModel = myViewModel,
//                        favouriteRecipes = FavouriteRecipes()
//                    )
//                }
//            }
//        }
//    }

    @BeforeTest
    fun setup() {
        composeTestRule.setContent {
            myViewModel.fetchAndShowData()
            // Set up TestNavHostController
            navController = TestNavHostController(LocalContext.current)
            RecipeListScreen(
                navController = navController,
                favouriteRecipes = FavouriteRecipes(),
                searchText = "",
                isDarkTheme = true,
                paddingValues = PaddingValues(),
                myViewModel = myViewModel
            )
        }
    }

    @Test
    fun testRecipeList_displaysAllRecipes() {
        val allRecipes = RecipeRepository(myViewModel).getAllRecipes()
        allRecipes.forEachIndexed { index, recipe ->
            composeTestRule
                .onNodeWithTag("LazyColumn")
                .performScrollToIndex(index)

            composeTestRule
                .onNodeWithText(recipe.name)
                .assertIsDisplayed()
        }
    }

    @Test
    fun testCategoryFilter_displaysRecipesOfSelectedCategory() {
        Thread.sleep(1000)
        // Simulate clicking on a cuisine "Italian"
        composeTestRule
            .onAllNodesWithText("Italian")
            .onFirst()
            .performClick()

        // Only recipes in the "Italian" category should be displayed
        composeTestRule
            .onNodeWithText("Classic Margherita Pizza")
            .assertExists()

        // Not part of the Italian category
        composeTestRule
            .onNodeWithText("Vegetarian Stir-Fry")
            .assertDoesNotExist()
    }

//    @Test
//    fun testRecipeClick_navigatesToRecipeDetail() {
//        Thread.sleep(1000)
//        // Click on a recipe card, simulate navigation
//        composeTestRule
//            .onNodeWithText("Classic Margherita Pizza")
//            .performClick()
//
//        // Assert that navigation happened (you can check the current back stack entry's destination)
//        assertEquals("recipe_detail/{recipeId}", navController.currentDestination?.route)
//    }
}
