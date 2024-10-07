package org.example.project

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ProductListScreen(
    navController: NavController,
    myViewModel: MyViewModel,
    paddingValues: PaddingValues,
    expanded: Boolean
) {
    val exp = !expanded
    val products = RecipeRepository(myViewModel).getAllProducts()
    Surface(
        color = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
        ) {
            Text(text = "Products", style = MaterialTheme.typography.displaySmall,modifier = Modifier.padding(4.dp))
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(products) { product ->
                    ProductCard(
                        product = product,
                        onClick = { navController.navigate("product_detail/${product.id}") }
                    )
                }
            }
        }
        AnimatedVisibility(
            visible = exp,
            enter = slideInHorizontally(),
            exit = slideOutHorizontally()
        ) {
            MenuBar(navController = navController)
        }
    }
}