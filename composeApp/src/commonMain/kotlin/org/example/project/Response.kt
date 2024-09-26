package org.example.project

import kotlinx.serialization.Serializable

// Data class Response represents the structure of the API response containing recipe data.
@Serializable
data class Response(
    var recipes: List<Recipe>,
    val total: Int,
    val skip: Int,
    val limit: Int
)