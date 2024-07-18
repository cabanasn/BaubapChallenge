package com.ircl.baubapchallenge.utils

import org.junit.Assert.assertTrue
import org.junit.Test

class ResourceTest {

    @Test
    fun `success contains data`() {
        val data = "Test Data"
        val resource = Resource.Success(data)
        assertTrue(resource.data == data)
    }

    @Test
    fun `error contains message and optional data`() {
        val errorMessage = "Error occurred"
        val errorData = "Error Data"
        val resource = Resource.Error(errorMessage, errorData)
        assertTrue(resource.message == errorMessage && resource.data == errorData)
    }

    @Test
    fun `loading indicates loading state`() {
        val resource = Resource.Loading<Boolean>()
        assertTrue(resource.isLoading)
    }

    @Test
    fun `success without data returns null as data`() {
        val resource = Resource.Success<String>(null)
        assertTrue(resource.data == null)
    }

    @Test
    fun `error without data returns null as data`() {
        val resource = Resource.Error<String>("Error occurred")
        assertTrue(resource.data == null)
    }

    @Test
    fun `success with specific data type returns correct data`() {
        val data = 123 // Example with Int data type
        val resource = Resource.Success(data)
        assertTrue(resource.data == data)
    }

    @Test
    fun `error with specific data type contains correct data`() {
        val errorMessage = "Specific error"
        val errorData = 404 // Example with Int data type
        val resource = Resource.Error(errorMessage, errorData)
        assertTrue(resource.message == errorMessage && resource.data == errorData)
    }

    @Test
    fun `success with complex data type returns correct data`() {
        val data = listOf("Test1", "Test2") // Example with List<String> data type
        val resource = Resource.Success(data)
        assertTrue(resource.data == data)
    }

    @Test
    fun `error with nullable data type handles null data correctly`() {
        val errorMessage = "Nullable error"
        val resource = Resource.Error<Int?>(errorMessage, null)
        assertTrue(resource.message == errorMessage && resource.data == null)
    }

}