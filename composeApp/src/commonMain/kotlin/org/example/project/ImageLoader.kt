package org.example.project

import androidx.compose.ui.graphics.ImageBitmap
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readBytes
import io.ktor.http.ContentType.Image

class ImageLoader(
    private val httpClient: HttpClient // Inject HttpClient to make HTTP requests for loading images.
) {
    // This is a suspend function, meaning it can be called from a coroutine or another suspend function.
    suspend fun loadImage(imageResource: ImageResource): ImageBitmap {
        when(imageResource) {
            is ImageResource.Url -> {
                val imageUrl = imageResource.imageUrl
                return loadFromRemote(imageUrl)
            }
        }
    }

    // Private helper function to load an image from a remote URL.
    // It makes an HTTP GET request to the given URL to fetch the image data.
    private suspend fun loadFromRemote(imageUrl: String): ImageBitmap {
        val response: HttpResponse = httpClient.get(imageUrl)
        val imageBytes= response.readBytes()
        return imageBitmapFromBytes(imageBytes)
    }
}