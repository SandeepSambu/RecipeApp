package org.example.project

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MyTopAppBar(
    navController: NavController,
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onThemeToggle: () -> Unit,
    expanded: () -> Unit
) {
    Log.d("MyTopAppBar", "Updated Text: $searchText")
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            shadowElevation = 4.dp,
            modifier = Modifier.height(70.dp)
        ) {
            IconButton(onClick = expanded) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
            }
        }
        TopAppBar(
            title = { Text(text = "RecipeApp") }, // Title displayed in the app bar
            backgroundColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground, // sets the Text color bases on the background
            actions = { // Actions to display on the right side of the app bar
                TextField(
                    value = searchText, // Current value of the search text
                    onValueChange = onSearchTextChange, // Callback to update search text
                    leadingIcon = { // Icon displayed at the end of the TextField
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    },
                    placeholder = { // Placeholder text when the field is empty
                        Text("Search")
                    },
                    modifier = Modifier
                        .width(125.dp)
                        .background(
                            MaterialTheme.colorScheme.surface,
                            shape = CircleShape
                        )
                        .testTag("TextField"),
                    shape = CircleShape, // Circular shape for the TextField
                    singleLine = true // Ensure the TextField is single-line
                )

                IconButton(onClick = {
                    navController.navigate("recipe_list")
                }) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home"
                    )
                }

                IconButton(onClick = {
                    onThemeToggle()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.themeswitch),
                        contentDescription = "Switch",
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            elevation = 4.dp, // Elevation of the app bar for shadow effect
            modifier = Modifier
                .fillMaxWidth()
                .size(70.dp)
        )
    }
}

@Preview
@Composable
fun TopAppBarPreview() {
    MyTopAppBar(
        navController = rememberNavController(),
        searchText = "",
        onSearchTextChange = {},
        onThemeToggle = {},
        expanded = {}
    )
}