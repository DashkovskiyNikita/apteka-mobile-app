package com.example.apteka_mobile_app.data.repositories

import com.example.apteka_mobile_app.data.api.MedicineProductInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CartModel(
    val product: MedicineProductInfo,
    val amount: Int = 0
)

class CartRepository {

    private val internalCart: MutableStateFlow<List<CartModel>> = MutableStateFlow(emptyList())

    val cartFlow: StateFlow<List<CartModel>>
        get() = internalCart.asStateFlow()

    fun addProduct(productInfo: MedicineProductInfo) {
        internalCart.update { it + CartModel(product = productInfo, amount = 1) }
    }

    fun increaseProductAmount(cartModel: CartModel) {
        val index = internalCart.value.indexOf(cartModel)
        internalCart.update {
            it.toMutableList().apply {
                this[index] = cartModel.copy(amount = cartModel.amount + 1)
            }
        }
    }

    fun decreaseProductAmount(cartModel: CartModel) {
        val index = internalCart.value.indexOf(cartModel)
        internalCart.update {
            it.toMutableList().apply {
                this[index] = cartModel.copy(amount = cartModel.amount - 1)
            }
        }
    }

    fun removeProduct(productId: Int) {
        val cartModel = internalCart.value.find { it.product.id == productId } ?: return
        internalCart.update { it - cartModel }
    }

    fun clearCart() {
        internalCart.update { emptyList() }
    }

}