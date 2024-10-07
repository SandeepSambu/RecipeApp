package org.example.project

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import kotlinx.browser.window

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        val myViewModel = MyViewModel()
        myViewModel.fetchRecipeData()
        myViewModel.fetchProductsData()

        // State to track the current theme (dark or light)
        var isDarkTheme by remember { mutableStateOf(window.matchMedia("(prefers-color-scheme: dark)").matches) }

        // State to hold the search text for recipes
        var searchText by remember { mutableStateOf("") }

        // Instance of FavouriteRecipes to manage favorite recipes
        val favouriteRecipes = FavouriteRecipes()

        // State to manage the current route
        var currentRoute by remember { mutableStateOf<Route>(Route.RecipeList) }

        var expanded by rememberSaveable { mutableStateOf(false) }

        // Function to update the current route based on the URL hash
        val updateRouteFromHash: () -> Unit = {
            val hash = window.location.hash
            currentRoute = when {
                hash.startsWith("#/recipe/") -> Route.RecipeDetail(hash.substringAfter("/recipe/"))
                hash.contains("#/products/") -> Route.ProductList
                else -> Route.RecipeList
            }
        }

        // Listen for hash changes in the URL to update the route
        window.onhashchange = { updateRouteFromHash() }
        updateRouteFromHash()

        // Determine which screen to render based on the current route
        val onRenderRoute = when(currentRoute) {
            is Route.RecipeList -> RecipeListScreen(
                onRecipeSelected = { recipeId ->
                    window.location.hash = "#/recipe/$recipeId" // Update the hash to reflect the selected recipe
                    currentRoute = Route.RecipeDetail(recipeId) // Update current route to RecipeDetail,
                    expanded = false
                },
                onRecipeList = {
                    window.location.hash = "#/"
                    currentRoute = Route.RecipeList
                    expanded = false
                },
                onProductList = {
                    window.location.hash = "#/products/"
                    currentRoute = Route.ProductList
                    expanded = false
                },
                favouriteRecipes = favouriteRecipes,
                isDarkTheme = isDarkTheme,
                searchText = searchText,
                myViewModel = myViewModel,
                expanded = expanded
            )
            is Route.RecipeDetail -> RecipeDetailScreen(
                recipeId = (currentRoute as Route.RecipeDetail).recipeId, // Extract recipeId from the current route
                isDarkTheme = isDarkTheme,
                myViewModel = myViewModel,
                favouriteRecipes = favouriteRecipes
            )

            is Route.ProductList -> ProductListScreen(
                isDarkTheme = isDarkTheme,
                myViewModel = myViewModel,
                expanded = expanded,
                onRecipeList = {
                    window.location.hash = "#/"
                    currentRoute = Route.RecipeList
                    expanded = false
                },
                onProductList = {
                    window.location.hash = "#/products/"
                    currentRoute = Route.ProductList
                    expanded = false
                }
            )
        }
        // Apply MaterialTheme for consistent styling
        MaterialTheme(
            colors = if(isDarkTheme) darkColors() else lightColors()
        ) {
            Surface(
                color = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.onBackground
            ) {
                Column {
                    MyTopAppBar(
                        onHome = {
                            window.location.hash = "#/" // Reset hash to go to RecipeList
                            currentRoute = Route.RecipeList // Set current route to RecipeList
                            expanded = false
                        },
                        searchText = searchText,
                        onSearchTextChange = { newText -> searchText = newText },
                        onThemeChange = { isDarkTheme = !isDarkTheme },
                        expanded = { expanded = !expanded }
                    )
                    MyApp(currentRoute = currentRoute, onRenderRoute = { onRenderRoute })
                }
            }
        }
    }
}

// Composable function that renders the current route in the application
@Composable
fun MyApp(currentRoute: Route, onRenderRoute: () -> Unit) {
    // Render the current route
    onRenderRoute()
}

