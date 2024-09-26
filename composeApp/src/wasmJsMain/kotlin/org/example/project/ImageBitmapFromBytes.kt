package org.example.project

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image

// Function to convert a byte array representing an encoded image into an ImageBitmap.
// This is useful for loading images that have been received in a raw format (like from a network response).
actual fun imageBitmapFromBytes(encodedImageData: ByteArray): ImageBitmap {
    // The makeFromEncoded method decodes the byte array into an Image. "toComposeImageBitmap()", converts the Image to a Compose ImageBitmap for use in Jetpack Compose.
    return Image.makeFromEncoded(encodedImageData).toComposeImageBitmap()
}