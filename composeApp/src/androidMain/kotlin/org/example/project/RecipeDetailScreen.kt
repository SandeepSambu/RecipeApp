package org.example.project

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun RecipeDetailScreen(
    recipeId: String,
    navController: NavController,
    myViewModel: MyViewModel,
    favouriteRecipes: FavouriteRecipes
) {
    val recipe = RecipeRepository(myViewModel).getAllRecipes().find { it.id.toString() == recipeId }
//    val modifier = Modifier.fillMaxWidth()
    val configuration = LocalConfiguration.current

    class ModifierClass {
        val imgModifier =
            if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                Modifier.fillMaxWidth()
            } else {
                Modifier
                    .fillMaxWidth()
                    .height(700.dp)
            }
        val iconModifier =
            if(configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                if(configuration.screenWidthDp >= 1000) Modifier.padding(start = 1200.dp ) else Modifier.padding(start = 900.dp)
            } else {
                if(configuration.screenWidthDp > 400) Modifier.padding(start = 700.dp) else Modifier.padding(start = 400.dp)
            }
        val flag = false
    }

    val modifier = ModifierClass()

    // If the recipe is found, display its details
    recipe?.let {
        if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Surface(
                color = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground // Text color on the background
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 65.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Box { // Box to overlap image and text
                        RecipeImage(
                            recipe = recipe,
                            favouriteRecipes = favouriteRecipes,
                            imgModifier = modifier.imgModifier,
                            iconModifier = modifier.iconModifier,
                            flag = modifier.flag
                        )
                        IconButton(onClick = {
                            navController.navigate("recipe_list")
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                    Column(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(text = it.name, style = MaterialTheme.typography.headlineLarge, modifier = Modifier.testTag("abc"))
                        Spacer(modifier = Modifier.height(5.dp))   // Spacer for vertical spacing
                        Text(text = it.cuisine, style = MaterialTheme.typography.headlineMedium)
                        Spacer(modifier = Modifier.padding(vertical = 10.dp))
                        Text(text = "INGREDIENTS", style = MaterialTheme.typography.headlineSmall)
                        Spacer(modifier = Modifier.padding(vertical = 2.dp))
                        recipe.ingredients.forEach {
                            Text(text = it, style = MaterialTheme.typography.bodyLarge)
                            Spacer(modifier = Modifier.padding(vertical = 2.dp))
                        }
                        Spacer(modifier = Modifier.padding(vertical = 10.dp))
                        Text(text = "INSTRUCTIONS TO PREPARE RECIPE", style = MaterialTheme.typography.headlineSmall)
                        Spacer(modifier = Modifier.padding(vertical = 2.dp))
                        recipe.instructions.forEach {
                            Text(text = it, style = MaterialTheme.typography.bodyLarge)
                            Spacer(modifier = Modifier.padding(vertical = 1.dp))
                        }
                    }
                }
            }
        } else {
            Surface(
                color = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 65.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Box {  // Box to overlap image and text
                        RecipeImage(
                            recipe = recipe,
                            favouriteRecipes = favouriteRecipes,
                            imgModifier = modifier.imgModifier,
                            iconModifier = modifier.iconModifier,
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
                            IconButton(onClick = {
                                navController.navigate("recipe_list")
                            }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                            Text(text = it.name, style = androidx.compose.material.MaterialTheme.typography.h2, color = Color.White)
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(text = it.cuisine, style = androidx.compose.material.MaterialTheme.typography.h3, color = Color.White)
                            Spacer(modifier = Modifier.padding(vertical = 10.dp)) // Spacer for vertical spacing
                            Text(text = "INGREDIENTS", style = androidx.compose.material.MaterialTheme.typography.h5, color = Color.White)
                            Spacer(modifier = Modifier.padding(vertical = 2.dp))
                            recipe.ingredients.forEachIndexed { index, it ->
                                Text(text = "    ${index + 1}  $it", style = androidx.compose.material.MaterialTheme.typography.h6, color = Color.White)
                                Spacer(modifier = Modifier.padding(vertical = 2.dp))
                            }
                            Spacer(modifier = Modifier.padding(vertical = 10.dp))
                            Text(text = "INSTRUCTIONS TO PREPARE RECIPE", style = androidx.compose.material.MaterialTheme.typography.h5, color = Color.White)
                            Spacer(modifier = Modifier.padding(vertical = 2.dp))
                            recipe.instructions.forEachIndexed { index, it ->
                                Text(text = "    ${index + 1}  $it", style = androidx.compose.material.MaterialTheme.typography.h6, color = Color.White)
                                Spacer(modifier = Modifier.padding(vertical = 1.dp))
                            }
                        }
                    }
                }
            }
        }
    }?: run {
        Text(text = "Recipe Not Found")
    }
}

@Preview
@Composable
fun RecipeDetailScreenPreview() {
    RecipeDetailScreen(
        recipeId = "1",
        navController = rememberNavController(),
        myViewModel = MyViewModel(),
        favouriteRecipes = FavouriteRecipes()
    )
}