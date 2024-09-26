package org.example.project

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

// A viewmodel helps the data to persist and handle the data when the ui components are destroyed and recreated
class FavouriteRecipes: ViewModel() {
    // This list is internally modifiable but externally accessible as a read-only list.
    private val _favRecipes = mutableStateListOf<Recipe>()

    // A public getter to access the list of favorite recipes in a read-only format.
    val favRecipes:List<Recipe> get() = _favRecipes

    //manages a list of favourite recipes
    fun toggleFavourites(recipe: Recipe) {
        if(_favRecipes.contains(recipe)) {
            _favRecipes.remove(recipe)
        } else {
            _favRecipes.add(recipe)
        }
    }

    // Function to get a list of favorite recipes filtered by a specific cuisine.
    fun getFav_Rec_Category(cuisine: String): List<Recipe> {
        return favRecipes.filter { it.cuisine == cuisine }
    }

    // Function to check if a recipe is in the favorite list.
    fun isFavourite(recipe: Recipe): Boolean {
        return _favRecipes.contains(recipe)
    }
}