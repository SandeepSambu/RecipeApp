package org.example.project

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun RecipeListScreen(
    navController: NavController,
    favouriteRecipes: FavouriteRecipes,
    searchText: String,
    isDarkTheme: Boolean,
    paddingValues: PaddingValues,
    myViewModel: MyViewModel
) {
    val localConfiguration = LocalConfiguration.current

    // State to manage selected cuisine and favorite recipe toggle
    var cuisine by remember { mutableStateOf("") }

    // mutable state to toggle the favourite recipes to display, rememberSaveable to persis the screen rotation
    var favRec by rememberSaveable { mutableStateOf(false) }
    val allRecipes = RecipeRepository(myViewModel).getAllRecipes()

    // Filter recipes based on the search text
    val filteredRecipes = allRecipes.filter { recipe -> recipe.name.startsWith(searchText, ignoreCase = true) }

    // Further filter based on selected cuisine
    val recipes = if(cuisine.isNotEmpty()) filteredRecipes.filter { it.cuisine == cuisine } else filteredRecipes

    // Get favorite recipes based on selected cuisine
    val favRecipes = if(cuisine.isNotEmpty()) favouriteRecipes.getFav_Rec_Category(cuisine) else favouriteRecipes.favRecipes
    MaterialTheme(
        colorScheme = if(isDarkTheme) darkColorScheme() else lightColorScheme()
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground
        ){
            if(localConfiguration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(horizontal = 20.dp)
                        .fillMaxSize()
                ){
                    Column(
                        modifier = Modifier.padding(vertical = 10.dp)
                    ) {
                        Text(
                            text = "Cuisines",
                            fontStyle = FontStyle.Italic,
                            style = MaterialTheme.typography.displaySmall
                        )
                        Spacer(Modifier.height(15.dp))
                        LazyRow (
                            contentPadding = PaddingValues(horizontal = 5.dp)
                        ){
                            items(allRecipes) { recipe ->
                                Box(
                                    modifier = Modifier
                                        .clickable {
                                            cuisine = recipe.cuisine
                                        }
                                        .testTag("Cuisine_${recipe.cuisine}")
                                ){
                                    CategoryImage(recipe = recipe)
                                    Surface(
                                        color = MaterialTheme.colorScheme.surfaceVariant
                                    ) {
                                        Text(
                                            text = recipe.cuisine,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontStyle = FontStyle.Italic,
                                            modifier = Modifier.padding(horizontal = 2.dp)
                                        )
                                    }
                                }
                                Spacer(Modifier.width(10.dp))
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Box(
                        modifier = Modifier
                            .border(shape = CircleShape, color = Color.Black, width = 1.dp)
                            .padding(3.dp)
                            .testTag("FavouriteButton")
                            .clickable { favRec = !favRec }
                    ) {
                        Text(
                            text = "Favourites",
                            modifier = Modifier
                                .padding(horizontal = 20.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Column(
                        modifier = Modifier.padding(vertical = 10.dp)
                    ) {
                        Text(
                            text = "Recipes",
                            fontStyle = FontStyle.Italic,
                            style = MaterialTheme.typography.displaySmall
                        )
                        Spacer(Modifier.height(15.dp))
                        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                            items(if(!favRec) recipes  else favRecipes) { recipe ->
                                RecipeCard(
                                    recipe = recipe,
                                    onClick = {
                                        navController.navigate("recipe_detail/${recipe.id}")
                                    },
                                    favouriteRecipes = favouriteRecipes
                                )
                            }
                        }
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(5.dp))
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                ) {
                    Box(
                        modifier = Modifier
                            .border(shape = CircleShape, color = Color.Black, width = 1.dp)
                            .padding(3.dp)
                            .testTag("FavouriteButton")
                            .clickable { favRec = !favRec }
                    ) {
                        Text(
                            text = "Favourites",
                            modifier = Modifier
                                .padding(horizontal = 20.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxSize()
                    ){
                        Column(
                            modifier = Modifier.padding(vertical = 10.dp)
                        ) {
                            Text(
                                text = "Cuisines",
                                fontStyle = FontStyle.Italic,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Spacer(Modifier.height(15.dp))
                            LazyColumn (
                                contentPadding = PaddingValues(horizontal = 5.dp)
                            ){
                                items(allRecipes) { recipe ->
                                    Box(
                                        modifier = Modifier
                                            .clickable {
                                                cuisine = recipe.cuisine
                                            }
                                            .testTag("Cuisine_${recipe.cuisine}")
                                    ){
                                        CategoryImage(recipe = recipe)
                                        Surface(
                                            color = MaterialTheme.colorScheme.surfaceVariant
                                        ) {
                                            Text(
                                                text = recipe.cuisine,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontStyle = FontStyle.Italic,
                                                modifier = Modifier.padding(horizontal = 2.dp)
                                            )
                                        }
                                    }
                                    Spacer(Modifier.width(10.dp))
                                }
                            }
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        Column(
                            modifier = Modifier.padding(vertical = 10.dp)
                        ) {
                            Text(
                                text = "Recipes",
                                fontStyle = FontStyle.Italic,
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(start = 300.dp)
                            )
                            Spacer(Modifier.height(15.dp))
                            LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                                items(if(!favRec) recipes  else favRecipes) { recipe ->
                                    RecipeCard(
                                        recipe = recipe,
                                        onClick = {
                                            navController.navigate("recipe_detail/${recipe.id}")
                                        },
                                        favouriteRecipes = favouriteRecipes
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun RecipeListPreview() {
    val myViewModel = MyViewModel()
    RecipeListScreen(
        navController = rememberNavController(),
        favouriteRecipes = FavouriteRecipes(),
        searchText = "",
        isDarkTheme = false,
        paddingValues = PaddingValues(),
        myViewModel = myViewModel
    )
}