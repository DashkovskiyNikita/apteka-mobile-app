package com.example.apteka_mobile_app.data.repositories

import com.example.apteka_mobile_app.data.api.SupplierApi
import com.example.apteka_mobile_app.data.networkCall

class SupplierRepository(private val supplierApi: SupplierApi) {
    suspend fun getSuppliers() = networkCall {
        supplierApi.getSuppliers()
    }
}