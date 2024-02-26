package com.example.apteka_mobile_app.data.repositories

import com.example.apteka_mobile_app.data.api.ProductsApi
import com.example.apteka_mobile_app.data.networkCall

class ProductsRepository(private val productsApi: ProductsApi) {

    suspend fun getProducts(name: String? = null, categories: List<Int>? = null) = networkCall {
        productsApi.getProducts(name = name, categories = categories)
    }

    suspend fun getProductById(productId: String) = networkCall {
        productsApi.getProductById(id = productId)
    }

}