package com.example.apteka_mobile_app.ui.employee.deliveries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apteka_mobile_app.data.NetworkResponse
import com.example.apteka_mobile_app.data.api.Delivery
import com.example.apteka_mobile_app.data.api.DeliveryProduct
import com.example.apteka_mobile_app.data.api.Supplier
import com.example.apteka_mobile_app.data.repositories.DeliveryRepository
import com.example.apteka_mobile_app.data.repositories.ProductsRepository
import com.example.apteka_mobile_app.data.repositories.SupplierRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import java.math.BigDecimal

data class DeliveryState(
    val isLoading: Boolean = false,
    val selectedSupplier: Supplier = Supplier(id = 0, name = ""),
    val editPriceValue: String = "",
    val editAmount: String = "",
    val suppliersList: List<Supplier> = emptyList(),
    val vendorCodesList: List<String> = emptyList(),
    val deliveryProduct: DeliveryProduct = DeliveryProduct(
        vendorCode = "",
        amount = 0,
        price = BigDecimal.ZERO
    ),
    val deliveryProducts: List<DeliveryProduct> = emptyList()
)

class DeliveryViewModel(
    private val productsRepository: ProductsRepository,
    private val supplierRepository: SupplierRepository,
    private val deliveryRepository: DeliveryRepository
) : ViewModel() {

    private val internalState = MutableStateFlow(DeliveryState())

    private val state: DeliveryState
        get() = internalState.value

    val uiState = internalState.asStateFlow()

    init {
        viewModelScope.launch {
            internalState.update { it.copy(isLoading = true) }
            val suppliersJob = launch { getSuppliers() }
            val productsJob = launch { getProducts() }
            joinAll(suppliersJob, productsJob)
        }.invokeOnCompletion {
            internalState.update { it.copy(isLoading = false) }
        }
    }

    fun setSupplier(supplier: Supplier) {
        viewModelScope.launch {
            internalState.update {
                it.copy(selectedSupplier = supplier)
            }
        }
    }

    fun setVendorCode(vendorCode: String) {
        viewModelScope.launch {
            internalState.update {
                it.copy(deliveryProduct = it.deliveryProduct.copy(vendorCode = vendorCode))
            }
        }
    }

    fun setAmount(amount: String) {
        viewModelScope.launch {
            internalState.update {
                it.copy(editAmount = amount)
            }
        }
    }

    fun setPrice(price: String) {
        viewModelScope.launch {
            internalState.update {
                it.copy(editPriceValue = price)
            }
        }
    }

    fun addProductToDelivery() {
        viewModelScope.launch {
            internalState.update {
                it.copy(
                    deliveryProducts = it.deliveryProducts + state.deliveryProduct.copy(
                        price = BigDecimal(state.editPriceValue),
                        amount = state.editAmount.toInt()
                    ),
                    editPriceValue = "",
                    editAmount = "",
                    deliveryProduct = DeliveryProduct("", 0, BigDecimal.ZERO)
                )
            }
        }
    }

    fun removeDeliveryProduct(deliveryProduct: DeliveryProduct) {
        viewModelScope.launch {
            internalState.update {
                it.copy(deliveryProducts = it.deliveryProducts - deliveryProduct)
            }
        }
    }


    fun newDelivery() {
        viewModelScope.launch {
            val delivery = Delivery(
                supplierId = state.selectedSupplier.id,
                products = state.deliveryProducts
            )

            when (deliveryRepository.newDelivery(delivery)) {
                is NetworkResponse.Success -> internalState.update {
                    it.copy(
                        selectedSupplier = Supplier(0, ""),
                        deliveryProducts = emptyList()
                    )
                }

                else -> {}
            }
        }
    }

    private suspend fun getSuppliers() {
        when (val response = supplierRepository.getSuppliers()) {
            is NetworkResponse.Success -> internalState.update {
                it.copy(suppliersList = response.body)
            }

            else -> {}
        }
    }

    private suspend fun getProducts() {
        when (val response = productsRepository.getProducts()) {
            is NetworkResponse.Success -> internalState.update {
                it.copy(
                    vendorCodesList = response.body.map { product -> product.vendorCode }
                )
            }

            else -> {}
        }
    }

}