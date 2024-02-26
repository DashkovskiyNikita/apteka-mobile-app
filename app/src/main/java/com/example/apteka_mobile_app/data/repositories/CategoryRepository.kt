package com.example.apteka_mobile_app.data.repositories

import com.example.apteka_mobile_app.data.api.CategoryApi
import com.example.apteka_mobile_app.data.networkCall

class CategoryRepository(private val categoryApi: CategoryApi) {
    suspend fun getCategories() = networkCall {
        categoryApi.getCategories()
    }
}