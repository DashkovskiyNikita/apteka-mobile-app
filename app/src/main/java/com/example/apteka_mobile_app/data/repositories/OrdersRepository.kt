package com.example.apteka_mobile_app.data.repositories

import com.example.apteka_mobile_app.data.api.OrderProduct
import com.example.apteka_mobile_app.data.api.OrdersApi
import com.example.apteka_mobile_app.data.networkCall

class OrdersRepository(private val ordersApi: OrdersApi) {

    suspend fun createNewOrder(products: List<OrderProduct>) = networkCall {
        ordersApi.createNewOrder(products = products)
    }

    suspend fun getClientOrders() = networkCall {
        ordersApi.getClientOrders()
    }

    suspend fun cancelOrder(orderId: Int) = networkCall {
        ordersApi.cancelOrder(orderId = orderId)
    }

    suspend fun getTodayOrders() = networkCall {
        ordersApi.getTodayOrders()
    }

    suspend fun issueOrder(orderId: Int, prescription: Boolean) = networkCall {
        ordersApi.issueOrder(orderId = orderId, prescription = prescription)
    }
}