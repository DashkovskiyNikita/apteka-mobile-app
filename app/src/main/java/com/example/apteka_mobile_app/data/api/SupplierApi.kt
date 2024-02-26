package com.example.apteka_mobile_app.data.api

import retrofit2.http.GET

data class Supplier(
    val id: Int,
    val name: String
)

interface SupplierApi {
    @GET("/suppliers")
    suspend fun getSuppliers(): List<Supplier>
}