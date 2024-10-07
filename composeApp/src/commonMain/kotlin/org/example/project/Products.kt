package org.example.project

import kotlinx.serialization.Serializable

@Serializable
data class ProductList(
    val products: List<Products>
)

@Serializable
data class Products(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    val reviews: List<Reviews>,
    val images: List<String>
)

@Serializable
data class Reviews(
    val rating: Double,
    val comment: String,
    val reviewerName: String,
    val reviewerEmail: String
)
