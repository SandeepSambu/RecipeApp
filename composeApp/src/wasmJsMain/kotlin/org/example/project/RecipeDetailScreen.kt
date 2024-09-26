package org.example.project

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.unit.dp

@Composable
fun RecipeDetailScreen(
    recipeId: String,
    isDarkTheme: Boolean,
    myViewModel: MyViewModel,
    favouriteRecipes: FavouriteRecipes
) {
    val recipe = RecipeRepository(myViewModel).getAllRecipes().find { it.id.toString() == recipeId }
//    val modifier = Modifier.fillMaxWidth().height(500.dp)

    class ModifierClass {
        val modifier = Modifier.fillMaxSize()
        val flag = true
    }

    val modifier = ModifierClass()

    // If the recipe is found, display its details
    recipe?.let {
        MaterialTheme(
            colors = if(isDarkTheme) darkColors() else lightColors()
        ) {
            Surface(
                color = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.onBackground, // Text color on the background
                modifier = Modifier.fillMaxSize()
            ) {
                Column(modifier = Modifier.padding(top = 50.dp).verticalScroll(rememberScrollState())) {
                    Box {  // Box to overlap image and text
                        Image(
                            recipe = recipe,
                            favouriteRecipes = favouriteRecipes,
                            modifier = modifier.modifier,
                            flag = modifier.flag
                        )

                        // Dark fade (overlay) behind the column
                        Box(
                            modifier = Modifier
                                .matchParentSize()  // Make it cover the whole box
                                .background(Color.Black.copy(alpha = 0.5f)) // Semi-transparent black
                        )

                        Column(
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Text(text = it.name, style = MaterialTheme.typography.h2, color = Color.White)
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(text = it.cuisine, style = MaterialTheme.typography.h3, color = Color.White)
                            Spacer(modifier = Modifier.padding(vertical = 10.dp)) // Spacer for vertical spacing
                            Text(text = "INGREDIENTS", style = MaterialTheme.typography.h5, color = Color.White)
                            Spacer(modifier = Modifier.padding(vertical = 2.dp))
                            recipe.ingredients.forEachIndexed { index, it ->
                                Text(text = "    ${index + 1}  $it", style = MaterialTheme.typography.h6, color = Color.White)
                                Spacer(modifier = Modifier.padding(vertical = 2.dp))
                            }
                            Spacer(modifier = Modifier.padding(vertical = 10.dp))
                            Text(text = "INSTRUCTIONS TO PREPARE RECIPE", style = MaterialTheme.typography.h5, color = Color.White)
                            Spacer(modifier = Modifier.padding(vertical = 2.dp))
                            recipe.instructions.forEachIndexed { index, it ->
                                Text(text = "    ${index + 1}  $it", style = MaterialTheme.typography.h6, color = Color.White)
                                Spacer(modifier = Modifier.padding(vertical = 1.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}