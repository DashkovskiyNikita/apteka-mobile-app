package com.example.apteka_mobile_app.data.api

import retrofit2.http.GET

data class Category(
    val categoryId: Int,
    val name: String
)

interface CategoryApi {
    @GET("/categories")
    suspend fun getCategories(): List<Category>
}