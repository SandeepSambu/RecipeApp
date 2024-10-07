package org.example.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import io.ktor.client.HttpClient
import kotlinx.coroutines.launch

@Composable
fun ProductImage(product: Products) {
    val scope = rememberCoroutineScope()

    val imageLoader = remember { ImageLoader(httpClient = HttpClient()) }

    var image by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(product.images[0]) {
        scope.launch {
            try {
                val remoteImage = imageLoader.loadImage(ImageResource.Url(product.images[0]))
                image = remoteImage
            } catch (e: Exception) {
                println("Error finding product image: ${e.message}")
            }
        }
    }

    if(image!=null) {
        Image(
            bitmap = image!!,
            contentDescription = product.title,
            modifier = Modifier.height(200.dp)
        )
    }
}