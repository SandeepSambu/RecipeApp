package org.example.project

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun MenuBar(navController: NavController) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        shadowElevation = 10.dp,
        modifier = Modifier
            .width(230.dp)
            .fillMaxHeight()
    ) {
        Column(
            modifier = Modifier
                .padding(top = 80.dp, start = 10.dp)
        ) {
            Text(
                text = "Checkout Recipes",
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .clickable { navController.navigate("recipe_list") }
            )
            HorizontalDivider()
            Text(
                text = "Checkout Products",
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .clickable { navController.navigate("product_list") }
            )
        }
    }
}

@Preview
@Composable
fun MenuPreview() {
    MenuBar(navController = rememberNavController())
}