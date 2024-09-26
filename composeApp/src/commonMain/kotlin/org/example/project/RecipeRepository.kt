package org.example.project

// RecipeRepository class is responsible for managing the retrieval of recipe data.
class RecipeRepository(viewModel: MyViewModel) {
    val data = viewModel.data

    // Function to retrieve recipes filtered by the specified cuisine category.
    fun getRecipesByCategory(cuisine: String): List<Recipe> {
        return data.value?.recipes?.filter { it.cuisine == cuisine } ?: emptyList()
    }

    // Function to retrieve all recipes without any filters.
    fun getAllRecipes(): List<Recipe> {
        return data.value?.recipes ?: emptyList()
    }

}