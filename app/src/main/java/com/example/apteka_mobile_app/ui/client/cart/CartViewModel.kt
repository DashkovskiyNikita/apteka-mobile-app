package com.example.apteka_mobile_app.ui.client.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apteka_mobile_app.data.NetworkResponse
import com.example.apteka_mobile_app.data.api.OrderProduct
import com.example.apteka_mobile_app.data.repositories.CartModel
import com.example.apteka_mobile_app.data.repositories.CartRepository
import com.example.apteka_mobile_app.data.repositories.OrdersRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository,
    private val ordersRepository: OrdersRepository
) : ViewModel() {

    val uiState = cartRepository.cartFlow
        .map { it.toList() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly, emptyList()
        )

    fun increaseProductAmount(cartModel: CartModel) {
        viewModelScope.launch {
            if (cartModel.amount > 0) {
                cartRepository.increaseProductAmount(cartModel)
            } else {
                cartRepository.addProduct(cartModel.product)
            }
        }
    }

    fun decreaseProductAmount(cartModel: CartModel) {
        viewModelScope.launch {
            if (cartModel.amount == 1) {
                cartRepository.removeProduct(productId = cartModel.product.id)
            } else {
                cartRepository.decreaseProductAmount(cartModel)
            }
        }
    }

    fun createOrder() {
        viewModelScope.launch {
            val response = ordersRepository.createNewOrder(
                products = cartRepository.cartFlow.value.map {
                    OrderProduct(
                        productId = it.product.id,
                        amount = it.amount
                    )
                }
            )

            when (response) {
                is NetworkResponse.Success -> cartRepository.clearCart()
                else -> {}
            }
        }
    }
}