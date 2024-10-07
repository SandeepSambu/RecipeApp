package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import kotlinx.browser.window

@Composable
fun ProductListScreen(
    isDarkTheme: Boolean,
    myViewModel: MyViewModel,
    expanded: Boolean,
    onRecipeList: () -> Unit,
    onProductList: () -> Unit
) {
    val products = RecipeRepository(myViewModel).getAllProducts()
    println(products)
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
            Row {
                if(expanded) {
                    AnimatedVisibility(
                        visible = expanded,
                        enter = slideInHorizontally(),
                        exit = slideOutHorizontally(
                            animationSpec = spring(stiffness = Spring.StiffnessHigh)
                        )
                    ) {
                        Row {
                            MenuBar(onRecipeList, onProductList)
                            Divider(modifier = Modifier.width(5.dp).fillMaxHeight())
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 50.dp)
                ){
                    Column(
                        modifier = Modifier.padding(vertical = 10.dp)
                    ) {
                        Text(
                            text = "Products",
                            fontStyle = FontStyle.Italic,
                            style = MaterialTheme.typography.h4
                        )
                        Spacer(Modifier.height(15.dp))
                        LazyVerticalGrid(
                            columns = gridCells, // Dynamic grid cells based on width
                            horizontalArrangement = Arrangement.spacedBy(5.dp) // Space between items
                        ) {
                            items(products) { product -> // Display either all or favorite recipes
                                ProductCard(
                                    product = product,
                                    onClick = { }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}