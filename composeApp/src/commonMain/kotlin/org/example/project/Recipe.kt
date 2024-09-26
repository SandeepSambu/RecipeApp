package org.example.project

import kotlinx.serialization.Serializable

// Data class Recipe represents a recipe entity with its details.
// The @Serializable annotation indicates that this class can be serialized and deserialized using kotlinx.serialization.
@Serializable
data class Recipe(
    val id: Int,
    val name: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val image: String,
    val cuisine: String
)