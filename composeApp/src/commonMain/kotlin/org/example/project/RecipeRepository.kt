package org.example.project

// RecipeRepository class is responsible for managing the retrieval of recipe data.
class RecipeRepository(viewModel: MyViewModel) {
    private val data = viewModel

    // Function to retrieve recipes filtered by the specified cuisine category.
    fun getRecipesByCategory(cuisine: String): List<Recipe> {
        return data.recipeData.value?.recipes?.filter { it.cuisine == cuisine } ?: emptyList()
    }

    // Function to retrieve all recipes without any filters.
    fun getAllRecipes(): List<Recipe> {
        return data.recipeData.value?.recipes ?: emptyList()
    }

    fun getAllProducts(): List<Products> {
        return data.productData.value?.products ?: emptyList()
    }

}