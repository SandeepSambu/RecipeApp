package org.example.project

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.testing.TestNavHostController
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class MyTopAppBarTest {
    private val myViewModel = MyViewModel()
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun searchTextField_updateText() {
        var searchText by mutableStateOf("")
        composeTestRule.setContent {
            MyTopAppBar(
                navController = rememberNavController(),
                searchText = searchText,
                onSearchTextChange = {newText -> searchText = newText},
                onThemeToggle = {}
            )
        }

        composeTestRule
            .onNodeWithTag("TextField")
            .performTextInput("Pasta")

        composeTestRule
            .onNodeWithTag("TextField")
            .assertTextEquals("Pasta")
    }

    @Test
    fun navigationIconButton_navigatesToRecipeListScreen() {
        // Initialize TestNavHostController
        val navController = TestNavHostController(composeTestRule.activity)
        // Add ComposeNavigator manually
        navController.navigatorProvider.addNavigator(ComposeNavigator())
        //NavHost condition should be as same as in the main file
        composeTestRule.setContent {
            NavHost(navController = navController, startDestination = "recipe_list") {
                composable("recipe_list") {
                    RecipeListScreen(
                        navController = navController,
                        favouriteRecipes = FavouriteRecipes(),
                        searchText = "",
                        isDarkTheme = true,
                        paddingValues = PaddingValues(),
                        myViewModel = myViewModel
                    )
                }

                composable("recipe_detail/{recipeId}") { backstackEntry ->
                    val recipeId = backstackEntry.arguments?.getString("recipeId")
                    if(recipeId!=null){
                        RecipeDetailScreen(
                            recipeId = recipeId,
                            navController = navController,
                            myViewModel = myViewModel,
                            favouriteRecipes = FavouriteRecipes()
                        )
                    }
                }
            }

            MyTopAppBar(
                navController = navController,
                searchText = "",
                onSearchTextChange = {},
                onThemeToggle = {}
            )
        }

        composeTestRule
            .onNodeWithContentDescription("Home")
            .performClick()

        assert(navController.currentBackStackEntry?.destination?.route == "recipe_list")
    }

    @Test
    fun themeToggleIconButton() {
        var isDark by mutableStateOf(false)
        composeTestRule.setContent {
            MyTopAppBar(
                navController = rememberNavController(),
                searchText = "",
                onSearchTextChange = {},
                onThemeToggle = { isDark = !isDark }
            )
        }

        composeTestRule
            .onNodeWithContentDescription("Switch")
            .performClick()

        assert(isDark)
    }
}