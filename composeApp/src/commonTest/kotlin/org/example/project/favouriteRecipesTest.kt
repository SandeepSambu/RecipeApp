package org.example.project

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class favouriteRecipesTest {
    private lateinit var FavouriteRecipes: FavouriteRecipes

    @BeforeTest
    fun setup() {
        FavouriteRecipes = FavouriteRecipes()
    }

    @Test
    fun tes_toggleFavourites_AddRecipe() {
        val recipe = Recipe(1, "Pancakes", emptyList(), emptyList(), "", "Italian")

        FavouriteRecipes.toggleFavourites(recipe)

        assertTrue(FavouriteRecipes.isFavourite(recipe))
        assertEquals(1, FavouriteRecipes.favRecipes.size)
    }

    @Test
    fun test_toggleFavourite_RemoveRecipe() {
        val recipe = Recipe(1, "Pancakes", emptyList(), emptyList(), "", "Italian")
        FavouriteRecipes.toggleFavourites(recipe)

        FavouriteRecipes.toggleFavourites(recipe)

        assertFalse(FavouriteRecipes.isFavourite(recipe))
        assertEquals(0, FavouriteRecipes.favRecipes.size)
    }

    @Test
    fun testGetFav_Rec_Category_Empty() {
        val category = "Italian"

        val result = FavouriteRecipes.getFav_Rec_Category(category)

        assertTrue(result.isEmpty(), "Favourite recipes for category should be empty")
    }

    @Test
    fun testGetFav_Rec_Category_NotEmpty() {
        val recipe1 = Recipe(1, "Pancakes", emptyList(), emptyList(), "", "Italian")
        val recipe2 = Recipe(2, "Spaghetti Carbonara", emptyList(), emptyList(), "", "Indian")
        val recipe3 = Recipe(3, "Tiramisu", emptyList(), emptyList(), "", "American")
        FavouriteRecipes.toggleFavourites(recipe1)
        FavouriteRecipes.toggleFavourites(recipe2)
        FavouriteRecipes.toggleFavourites(recipe3)

        val result = FavouriteRecipes.getFav_Rec_Category("Indian")

        assertEquals(1, result.size, "There should be 1 favourite recipe in the Indian")
        assertTrue(result.contains(recipe2), "The favourite recipe for Indian should be included")
    }

    @Test
    fun testIsFavourite_True() {
        val recipe = Recipe(1, "Pancakes", emptyList(), emptyList(), "", "Italian")
        FavouriteRecipes.toggleFavourites(recipe)

        val result = FavouriteRecipes.isFavourite(recipe)

        assertTrue(result, "Recipe should be marked as favourite")
    }

    @Test
    fun testIsFavourite_False() {
        val recipe = Recipe(1, "Pancakes", emptyList(), emptyList(), "", "Italian")

        val result = FavouriteRecipes.isFavourite(recipe)

        assertFalse(result, "Recipe should not be marked as favourite")
    }
}