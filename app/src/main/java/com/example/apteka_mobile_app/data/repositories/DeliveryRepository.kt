package com.example.apteka_mobile_app.data.repositories

import com.example.apteka_mobile_app.data.api.Delivery
import com.example.apteka_mobile_app.data.api.DeliveryApi
import com.example.apteka_mobile_app.data.networkCall

class DeliveryRepository(private val deliveryApi: DeliveryApi) {
    suspend fun newDelivery(delivery: Delivery) = networkCall {
        deliveryApi.newDelivery(delivery = delivery)
    }
}