package org.example.project

import androidx.compose.ui.graphics.ImageBitmap

// This function takes a ByteArray of encoded image data and returns an ImageBitmap
expect fun imageBitmapFromBytes(encodedImageData: ByteArray): ImageBitmap