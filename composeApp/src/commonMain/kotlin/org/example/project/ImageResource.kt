package org.example.project

// Sealed class ImageResource to represent different types of image sources.
sealed class ImageResource {
    // Data class to represent an image resource sourced from a URL.
    data class Url(val imageUrl: String): ImageResource()
}
