package org.example.project

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// NetworkClient class is responsible for making HTTP requests to fetch data.
class NetworkClient {
    // an instance of HttpClient with specific configurations for content negotiation and logging.
    private val client = HttpClient {
        // Install the ContentNegotiation feature to handle JSON serialization/deserialization.
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // Ignore keys not present in the data class.
                prettyPrint = true // Format JSON output for better readability.
            })
        }
        // Install the Logging feature to log HTTP request and response details.
        install(Logging) {
            level = LogLevel.BODY // Set the logging level to BODY to include the request and response bodies in the logs.
        }
    }

    // Suspend function to fetch data from a given URL and return it as a Response object.
    suspend fun fetchRecipeData(url: String): Response {
        return client.get(url).body()
    }

    suspend fun fetchProductData(url: String): ProductList {
        return client.get(url).body()
    }

    // Function to close the HttpClient and release resources.
    fun close() {
        client.close()
    }
}