package org.example.project

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// MyViewModel class is responsible for managing UI-related data and fetching data from the network.
class MyViewModel {
    private val networkClient = NetworkClient()
    // Mutable state to hold the fetched data, initialized to null.
    private var _recipeData = mutableStateOf<Response?>(null)
    // read-only state for the data.
    val recipeData: State<Response?> = _recipeData

    private var _productData = mutableStateOf<ProductList?>(null)
    val productData: State<ProductList?> = _productData

    // Mutable state to hold error messages, initialized to null.
    private val _error = mutableStateOf<String?>(null)
    // read-only state for the error messages.
    val error : State<String?> = _error

    // Function to fetch data from the network and update the UI state.
    fun fetchRecipeData() {
        // Launch a new coroutine in the Default dispatcher for network operations.
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val recipeData = networkClient.fetchRecipeData("https://dummyjson.com/recipes")
                // Switch to the Main dispatcher to update the UI state with fetched data.
                withContext(Dispatchers.Main) {
                    _recipeData.value = recipeData
                }

            } catch (e: Exception) {
                // Handle any exceptions that occur during data fetching.
                withContext(Dispatchers.Main) {
                    _error.value = "Error fetching data: ${e.message}"
                }
            }
        }
    }

    fun fetchProductsData() {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val productData = networkClient.fetchProductData("https://dummyjson.com/products")
                withContext(Dispatchers.Main) {
                    _productData.value = productData
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = "Error fetching data: ${e.message}"
                }
            }
        }
    }

    // Test helper function to set mock data directly for testing purposes.
    fun setTestData(recipes: List<Recipe>) {
        _recipeData.value = Response(recipes = recipes, total = recipes.size, skip = 0, limit = recipes.size)
    }
}