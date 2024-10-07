package org.example.project

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MenuBar(onRecipeList: () -> Unit, onProductList: () -> Unit) {
    Surface(
        color = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground,
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
                    .onClick(onClick = onRecipeList)
            )
            Divider(modifier = Modifier.width(250.dp).height(2.dp))
            Text(
                text = "Checkout Products",
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .onClick(onClick = onProductList)
            )
        }
    }
}