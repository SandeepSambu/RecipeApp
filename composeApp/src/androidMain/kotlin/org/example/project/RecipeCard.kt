package org.example.project

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RecipeCard(recipe: Recipe, onClick: () -> Unit, favouriteRecipes: FavouriteRecipes) {
//    val modifier = Modifier.height(200.dp)

    val configuration = LocalConfiguration.current

    class ModifierClass {
        val imgModifier = if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT) Modifier.height(200.dp) else Modifier.height(150.dp)
        val iconModifier = Modifier.padding(start = 400.dp)
        val flag = false
    }

    val modifier = ModifierClass()

    // Card component representing a recipe
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick) // Make the card clickable
            .testTag("RecipeCard_${recipe.name}"), // identifier used while testing
        backgroundColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        elevation = 4.dp // Elevation for shadow effect
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            RecipeImage(
                recipe = recipe,
                favouriteRecipes = favouriteRecipes,
                imgModifier = modifier.imgModifier,
                iconModifier = modifier.iconModifier,
                flag = modifier.flag
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = recipe.name, style = MaterialTheme.typography.titleLarge)
            Text(text = recipe.cuisine, style = MaterialTheme.typography.bodyMedium)
        }
    }
}



@Preview
@Composable
fun RecipeCardPreview(){
    val sampleRecipe = Recipe(
        id = 1,
        name = "Pancakes",
        cuisine = "Breakfast",
        ingredients = listOf(
            "2 cups flour",
            "2 tbsp sugar",
            "2 tsp baking powder",
            "1/2 tsp salt",
            "1 1/2 cups milk",
            "2 eggs",
            "2 tbsp melted butter"
        ),
        instructions = listOf(
            "Mix dry ingredients in a bowl.",
            "In another bowl, beat eggs and then add milk and melted butter.",
            "Combine wet and dry ingredients.",
            "Heat a lightly oiled griddle over medium heat.",
            "Pour or scoop the batter onto the griddle, using approximately 1/4 cup for each pancake.",
            "Brown on both sides and serve hot."
            ),
        image = "https://cdn.dummyjson.com/recipe-images/1.webp"
        )

    RecipeCard(recipe = sampleRecipe, onClick = {}, favouriteRecipes = FavouriteRecipes())
}

