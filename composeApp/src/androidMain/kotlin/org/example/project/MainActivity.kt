package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    private val myViewModel = MyViewModel()
//    private val productModel = ProductsModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Initialize the NavController for navigation between screens
            val navController = rememberNavController()
            MyApp(navController = navController, myViewModel = myViewModel)
        }
        myViewModel.fetchRecipeData()
        myViewModel.fetchProductsData()
    }
}

// MyApp function sets up the main UI structure of the app
@Composable
fun MyApp(navController: NavHostController, myViewModel: MyViewModel) {
    // Check if the system is in dark theme and maintain theme state
    val color = isSystemInDarkTheme()
    var isDarkTheme by remember { mutableStateOf(color) }
    var searchText by remember { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }
    MaterialTheme(
        colorScheme = if(isDarkTheme) darkColorScheme() else lightColorScheme()
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground
        ) {
            // Scaffold provides the layout structure with a top bar
            Scaffold(
                topBar = {
                    MyTopAppBar(
                        navController = navController,
                        searchText = searchText,
                        onSearchTextChange = { newText -> searchText = newText },
                        onThemeToggle = { isDarkTheme = !isDarkTheme },
                        expanded = { expanded = !expanded }
                    )
                },
                content = { paddingValues ->
                    RecipeApp(
                        navController = navController,
                        searchText = searchText,
                        isDarkTheme = isDarkTheme,
                        paddingValues = paddingValues,
                        myViewModel = myViewModel,
                        expanded = expanded
                    )
                }
            )
        }
    }
}

// RecipeApp function defines the navigation structure and screens
@Composable
fun RecipeApp(
    navController: NavHostController,
    searchText: String,
    isDarkTheme: Boolean,
    paddingValues: PaddingValues,
    myViewModel: MyViewModel,
    expanded: Boolean
) {
    val favouriteRecipes = FavouriteRecipes()

    // Setup navigation host with defined start destination
    NavHost(navController = navController, startDestination = "recipe_list") {
        composable("recipe_list") {
            RecipeListScreen(
                navController = navController,
                favouriteRecipes = favouriteRecipes,
                searchText = searchText,
                isDarkTheme = isDarkTheme,
                paddingValues = paddingValues,
                myViewModel = myViewModel,
                expanded = expanded
            )
        }

        // Recipe detail screen route with dynamic argument for recipe ID
        composable("recipe_detail/{recipeId}") { backstackEntry ->
            val recipeId = backstackEntry.arguments?.getString("recipeId")
            if(recipeId!=null){
                RecipeDetailScreen(
                    recipeId = recipeId,
                    navController = navController,
                    myViewModel = myViewModel,
                    favouriteRecipes = favouriteRecipes
                )
            }
        }

        composable("product_list") {
            ProductListScreen(
                navController = navController,
                myViewModel = myViewModel,
                paddingValues = paddingValues,
                expanded = expanded
            )
        }
    }
}

//@Preview
//@Composable
//fun RecipeListScreenPreview() {
//    MyApp()
//}