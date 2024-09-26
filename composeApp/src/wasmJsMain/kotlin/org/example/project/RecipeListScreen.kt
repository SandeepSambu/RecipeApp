package org.example.project

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import kotlinx.browser.window

@Composable
fun RecipeListScreen(
    onRecipeSelected: (String) -> Unit, // Callback for when a recipe is selected
    favouriteRecipes: FavouriteRecipes,
    isDarkTheme: Boolean,
    searchText: String,
    myViewModel: MyViewModel
) {
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

    // Determine grid cell count based on window size
    val gridCells = when {
        window.innerWidth >= 700 -> GridCells.Fixed(3)
        window.innerWidth >= 400 -> GridCells.Fixed(2)
        else -> GridCells.Fixed(1)
    }
    MaterialTheme(
        colors = if(isDarkTheme) darkColors() else lightColors()
    ) {
        Surface(
            color = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.onBackground,
            modifier = Modifier.fillMaxSize()
        ){
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 50.dp)
            ){
                Column(
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Text(
                        text = "Cuisines",
                        fontStyle = FontStyle.Italic,
                        style = MaterialTheme.typography.h4
                    )
                    Spacer(Modifier.height(15.dp))
                    LazyRow (
                        contentPadding = PaddingValues(horizontal = 5.dp)
                    ){
                        items(allRecipes) { recipe ->
                            Box(
                                modifier = Modifier.clickable {
                                    cuisine = recipe.cuisine
                                }
                            ){
                                CatergoryImage(recipe = recipe)
                                Surface(
                                    color = MaterialTheme.colors.surface
                                ) {
                                    Text(
                                        text = recipe.cuisine,
                                        style = MaterialTheme.typography.h6,
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
                        .clickable { favRec = !favRec }
                ) {
                    Text(
                        text = "Favourites",
                        modifier = Modifier
                            .padding(horizontal = 20.dp),
                        style = MaterialTheme.typography.h4
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Column(
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Text(
                        text = "Recipes",
                        fontStyle = FontStyle.Italic,
                        style = MaterialTheme.typography.h4
                    )
                    Spacer(Modifier.height(15.dp))
                    LazyVerticalGrid(
                        columns = gridCells, // Dynamic grid cells based on width
                        horizontalArrangement = Arrangement.spacedBy(5.dp) // Space between items
                    ) {
                        items(if(!favRec) recipes else favRecipes) { recipe -> // Display either all or favorite recipes
                            RecipeCard(
                                recipe = recipe,
                                onClick = { onRecipeSelected(recipe.id.toString()) },
                                favouriteRecipes = favouriteRecipes
                            )
                        }
                    }
                }
            }
        }
    }
}



