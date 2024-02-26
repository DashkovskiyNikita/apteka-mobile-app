package com.example.apteka_mobile_app.data.api

import retrofit2.http.Body
import retrofit2.http.POST
import java.math.BigDecimal

data class DeliveryProduct(
    val vendorCode: String,
    val amount: Int,
    val price: BigDecimal
)

data class Delivery(
    val supplierId: Int,
    val products: List<DeliveryProduct>
)

interface DeliveryApi {
    @POST("/deliveries/new")
    suspend fun newDelivery(@Body delivery: Delivery)
}