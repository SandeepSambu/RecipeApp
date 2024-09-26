package org.example.project

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import recipedemo.composeapp.generated.resources.Res
import recipedemo.composeapp.generated.resources.themeswitch

@Composable
fun MyTopAppBar(
    onHome: () -> Unit,
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onThemeChange: () -> Unit
) {
    TopAppBar(
        title = { Text(text = "MyRecipeApp") }, // Title displayed in the app bar
        backgroundColor = MaterialTheme.colors.background,
        actions = { // Actions to display on the right side of the app bar
            Box(
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                TextField(
                    value = searchText, // Current value of the search text
                    onValueChange = onSearchTextChange , // Callback to update search text
                    trailingIcon = { // Icon displayed at the end of the TextField
                        Icon(Icons.Default.Search, contentDescription = null)
                    },
                    placeholder = { Text("Search") }, // Placeholder text when the field is empty
                    modifier = Modifier
                        .width(500.dp)
                        .background(
                            color = MaterialTheme.colors.surface,
                            shape = CircleShape
                        )
                        .size(500.dp),
                    shape = CircleShape, // Circular shape for the TextField
                    singleLine = true // Ensure the TextField is single-line
                )
            }
            IconButton(onClick = onHome  ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home"
                )
            }
            IconButton(onClick = onThemeChange) {
                Icon(
                    painter = painterResource(Res.drawable.themeswitch),
                    contentDescription = "ThemeSwitch",
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        elevation = 4.dp, // Elevation of the app bar for shadow effect
        modifier = Modifier.fillMaxWidth()
    )
}


