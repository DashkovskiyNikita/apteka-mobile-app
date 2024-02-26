package com.example.apteka_mobile_app.data.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.math.BigDecimal

data class OrderProduct(
    val productId: Int,
    val amount: Int
)

data class ClientOrderProductInfo(
    val productId: Int,
    val name: String,
    val fullPrice: BigDecimal,
    val amount: Int
)

data class ClientOrderInfo(
    val orderId: Int,
    val products: List<ClientOrderProductInfo>,
    val totalPrice: BigDecimal,
    val status: OrderStatus
)

enum class OrderStatus {
    CREATED,
    ISSUED,
    CANCELLED
}

interface OrdersApi {
    @POST("/orders")
    suspend fun createNewOrder(@Body products: List<OrderProduct>): ClientOrderInfo

    @GET("/orders")
    suspend fun getClientOrders(): List<ClientOrderInfo>

    @PUT("/orders/{id}/cancel")
    suspend fun cancelOrder(@Path("id") orderId: Int)

    @PUT("/orders/{id}/issue")
    suspend fun issueOrder(
        @Path("id") orderId: Int,
        @Query("prescription") prescription: Boolean
    )

    @GET("/orders/today")
    suspend fun getTodayOrders(): List<ClientOrderInfo>
}