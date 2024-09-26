package org.example.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun CategoryImage(recipe: Recipe) {
    val imageRes = when (recipe.cuisine) {
        "Italian" -> R.drawable.breakfast
        "Indian" -> R.drawable.lunch
        "American" -> R.drawable.dinner
        "Russian" -> R.drawable.dessert
        else -> R.drawable.dessert // Fallback image
    }

    Image(
        painter = painterResource(id = imageRes),
        contentDescription = recipe.cuisine,
        modifier = Modifier
            .size(150.dp)
            .fillMaxWidth() // You can adjust the size as needed
    )
}