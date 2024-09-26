package org.example.project

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.painterResource
import recipedemo.composeapp.generated.resources.Res
import recipedemo.composeapp.generated.resources.breakfast
import recipedemo.composeapp.generated.resources.dessert
import recipedemo.composeapp.generated.resources.dinner
import recipedemo.composeapp.generated.resources.lunch

// Composable function that displays an image based on the cuisine type of the provided recipe.
@Composable
fun CatergoryImage(recipe: Recipe) {
    val imgRes = when(recipe.cuisine) {
        "Italian" -> Res.drawable.breakfast
        "Asian" -> Res.drawable.lunch
        "American" -> Res.drawable.dinner
        "Mexican" -> Res.drawable.dessert
        else -> Res.drawable.breakfast
    }

    // Display the image using the determined resource and provide content description for accessibility.
    Image(
        painter = painterResource(imgRes),
        contentDescription = recipe.cuisine
    )
}