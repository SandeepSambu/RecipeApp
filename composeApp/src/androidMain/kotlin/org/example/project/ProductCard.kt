package org.example.project

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProductCard(product: Products, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .height(300.dp)
            .clickable(onClick = onClick),
        backgroundColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            ProductImage(product = product)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = product.title, style = MaterialTheme.typography.titleLarge)
            Text(text = product.category, style = MaterialTheme.typography.bodyMedium)
        }
    }
}