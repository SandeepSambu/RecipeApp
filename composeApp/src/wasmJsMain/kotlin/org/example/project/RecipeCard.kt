package org.example.project

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RecipeCard(recipe: Recipe, onClick: () -> Unit, favouriteRecipes: FavouriteRecipes) {
//    val modifier = Modifier.size(200.dp)

    class ModifierClass {
        val modifier = Modifier.size(200.dp)
        val flag = false
    }

    val modifier = ModifierClass()

    // Card component representing a recipe
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick ), // Make the card clickable
        elevation = 4.dp // Elevation for shadow effect
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.padding(16.dp)) {
                RecipeImage(
                    recipe = recipe,
                    favouriteRecipes = favouriteRecipes,
                    imgModifier = modifier.modifier,
                    iconModifier = Modifier,
                    flag = modifier.flag
                )
                Text(text = recipe.name, style = MaterialTheme.typography.h5)
                Text(text = recipe.cuisine, style = MaterialTheme.typography.h6)
            }
        }
    }
}




