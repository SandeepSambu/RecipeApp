package org.example.project

// ensures type safety and that only valid routes are used
// Sealed class to define valid routes in the application
sealed class Route {
    // Represents the route for the main recipe list screen
    data object RecipeList : Route()
    // Represents the route for the recipe detail screen, containing the recipe ID
    data class RecipeDetail(val recipeId : String) : Route()

    data object ProductList: Route()
}