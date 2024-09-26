package org.example.project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import io.ktor.client.HttpClient
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import recipedemo.composeapp.generated.resources.Res
import recipedemo.composeapp.generated.resources.white_star
import recipedemo.composeapp.generated.resources.yellow_star

@Composable
fun Image(recipe: Recipe, favouriteRecipes: FavouriteRecipes, modifier: Modifier, flag: Boolean) {
    // a coroutine scope for launching suspend functions / asynchronous tasks
    val scope = rememberCoroutineScope()
    // an ImageLoader instance to load images over the network
    val imageLoader = remember { ImageLoader(httpClient = HttpClient()) }

    val isFavourite = favouriteRecipes.isFavourite(recipe)

    // Mutable state to store the loaded image; initially set to null
    var image by remember { mutableStateOf<ImageBitmap?>(null) }

    // Load the image whenever the recipe's image URL changes
    LaunchedEffect(recipe.image) {
        scope.launch {
            try {
                // Attempt to load the image from the provided URL
                val remoteImage  = imageLoader.loadImage(ImageResource.Url(recipe.image))
                image = remoteImage
            } catch (e: Exception) {
                println("Error finding image: ${e.message}")
            }
        }
    }
    //If the image is successfully loaded, display it inside a Box
    if(image!=null) {
        Box {
            Image(
                bitmap = image!!, // Safe to use non-null assertion since we're checking if it's null
                contentDescription = recipe.name,
                modifier = modifier, // Modifier for customizing the image's appearance
                contentScale = ContentScale.FillBounds // Scale the image to fill the given bounds
            )
            if(!flag) {
                IconButton(
                    modifier = Modifier.padding(start = 400.dp),
                    onClick = {
                        // Toggle the recipe as favorite/non favorite when clicked
                        favouriteRecipes.toggleFavourites(recipe)
                    }) {
                    val iconImg = if(isFavourite) {
                        Res.drawable.yellow_star
                    } else {
                        Res.drawable.white_star
                    }
                    Image(
                        painter = painterResource(iconImg),
                        contentDescription = if(isFavourite) "YellowStar" else "WhiteStar",
                        modifier = Modifier.size(30.dp).testTag("FavouriteIcon")
                    )
                }
            }
        }
    }
}