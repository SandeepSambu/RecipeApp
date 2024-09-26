package org.example.project

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RecipeRepositoryTest {
    private lateinit var repository : RecipeRepository
    private lateinit var myViewModel: MyViewModel

    @BeforeTest
    fun setup() {
        myViewModel = MyViewModel()
        myViewModel.setTestData(
            listOf(
                Recipe(1, "Classic Margherita Pizza", emptyList(), emptyList(), "", "Italian"),
                Recipe(2, "Chicken Alfredo Pasta", emptyList(), emptyList(), "", "Italian"),
                Recipe(3, "Tandoori Chicken", emptyList(), emptyList(), "", "Indian"),
                Recipe(4, "Chocolate Chip Cookies", emptyList(), emptyList(), "", "Dessert")
            )
        )
        repository = RecipeRepository(myViewModel)
    }

    @Test
    fun test_getRecipesByCategory_return_correct_recipes(){
        val italianRecipes = repository.getRecipesByCategory("Italian")
        assertEquals(2, italianRecipes.size)
        assertEquals("Classic Margherita Pizza", italianRecipes[0].name)
        assertEquals("Chicken Alfredo Pasta", italianRecipes[1].name)
    }

    @Test
    fun test_getAllRecipes_return_all_recipes(){
        val recipes = repository.getAllRecipes()
        println(recipes)
        assertEquals(4, recipes.size)
    }

    //test for case sensitivity
    @Test
    fun test_check_case_sensitive_for_getRecipeByCategory() {
        val indianRecipes = repository.getRecipesByCategory("Indian")
        assertEquals(1, indianRecipes.size)
    }

    //test for no matching category
    @Test
    fun test_when_no_matching_category() {
        val africanRecipes = repository.getRecipesByCategory("African")
        assertTrue(africanRecipes.isEmpty())
    }

    //test when recipeList is empty
    @Test
    fun test_getRecipesByCategory_when_recipeList_is_empty() {
        myViewModel.data.value?.recipes = emptyList()
        val italianRecipes = repository.getRecipesByCategory("Italian")
        assertTrue(italianRecipes.isEmpty())
    }

    //test when one of the fields is empty
    @Test
    fun test_getRecipesByCategory_when_recipeList_have_empty_fields() {
        myViewModel.data.value?.recipes = listOf(
            Recipe(31, "Empty Ingredients", emptyList(), emptyList(), "", "Test"),
            Recipe(32, "Empty Steps", emptyList(), listOf("Ingredient 1"), "", "Test")
        ).toMutableList()
        val testRecipes = repository.getRecipesByCategory("Test")
        assertEquals(2, testRecipes.size)
        assertTrue(testRecipes.any { it.name == "Empty Ingredients" })
        assertTrue(testRecipes.any { it.name == "Empty Steps" })
    }
}