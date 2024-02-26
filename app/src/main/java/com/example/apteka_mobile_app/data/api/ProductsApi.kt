package com.example.apteka_mobile_app.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.math.BigDecimal

data class MedicineProductInfo(
    val id: Int,
    val name: String,
    val vendorCode: String,
    val price: BigDecimal,
    val available: Boolean,
    val description: String
)

interface ProductsApi {
    @GET("/products")
    suspend fun getProducts(
        @Query("name") name: String?,
        @Query("categories") categories: List<Int>?
    ): List<MedicineProductInfo>

    @GET("/products/{id}")
    suspend fun getProductById(@Path("id") id: String): MedicineProductInfo
}