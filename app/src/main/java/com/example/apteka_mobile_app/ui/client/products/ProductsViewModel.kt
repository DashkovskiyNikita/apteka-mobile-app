package com.example.apteka_mobile_app.ui.client.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apteka_mobile_app.data.NetworkResponse
import com.example.apteka_mobile_app.data.api.Category
import com.example.apteka_mobile_app.data.repositories.CartModel
import com.example.apteka_mobile_app.data.repositories.CartRepository
import com.example.apteka_mobile_app.data.repositories.CategoryRepository
import com.example.apteka_mobile_app.data.repositories.ProductsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

data class ProductsState(
    val loading: Boolean = false,
    val products: List<CartModel> = emptyList(),
    val searchedProducts: List<CartModel> = emptyList(),
    val categories: List<Category> = emptyList(),
    val selectedCategoriesId: List<Int> = emptyList(),
    val search: String = ""
)

class ProductsViewModel(
    private val productsRepository: ProductsRepository,
    private val categoryRepository: CategoryRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val internalState = MutableStateFlow(ProductsState())

    private val state: ProductsState
        get() = internalState.value

    val uiState = internalState.combine(cartRepository.cartFlow) { state, cartProducts ->
        val searchedProducts = state.searchedProducts.map {
            cartProducts.firstOrNull { cart -> it.product.id == cart.product.id } ?: it
        }
        state.copy(searchedProducts = searchedProducts)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, ProductsState())

    fun increaseProductAmount(cartModel: CartModel) {
        viewModelScope.launch {
            if (cartModel.amount > 0) {
                println(cartModel)
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

    fun setSearch(search: String) {
        viewModelScope.launch {
            internalState.update { it.copy(search = search) }
            searchProducts()
        }
    }

    fun resetSearchByName() {
        viewModelScope.launch {
            internalState.update {
                it.copy(search = "", searchedProducts = state.products)
            }
        }
    }

    fun setSearchCategory(categoryId: Int, checked: Boolean) {
        viewModelScope.launch {
            internalState.update {
                if (checked)
                    it.copy(selectedCategoriesId = state.selectedCategoriesId + categoryId)
                else
                    it.copy(selectedCategoriesId = state.selectedCategoriesId - categoryId)
            }
        }
    }

    fun getProductsByCategories() {
        viewModelScope.launch { searchProducts() }
    }

    private suspend fun searchProducts() {
        val searchedProducts = productsRepository.getProducts(
            name = state.search.takeIf { it.isNotEmpty() },
            categories = state.selectedCategoriesId
        )
        when (searchedProducts) {
            is NetworkResponse.Success -> internalState.update {
                it.copy(searchedProducts = searchedProducts.body.map { product -> CartModel(product = product) })
            }

            else -> {}
        }
    }

    fun resetSearchByCategories() {
        viewModelScope.launch {
            internalState.update {
                it.copy(
                    selectedCategoriesId = emptyList(),
                    searchedProducts = state.products
                )
            }
        }
    }

    fun getProductsAndCategories() {
        viewModelScope.launch {

            internalState.update { it.copy(loading = true) }

            val productsJob = launch {
                when (val response = productsRepository.getProducts()) {
                    is NetworkResponse.Success -> internalState.update {
                        val mappedProducts = response.body.map { product -> CartModel(product = product) }
                        it.copy(
                            products = mappedProducts,
                            searchedProducts = mappedProducts
                        )
                    }

                    else -> {}
                }
            }

            val categoriesJob = launch {
                when (val response = categoryRepository.getCategories()) {
                    is NetworkResponse.Success -> internalState.update {
                        it.copy(categories = response.body)
                    }

                    else -> {}
                }
            }

            joinAll(productsJob, categoriesJob)

        }.invokeOnCompletion {
            internalState.update { it.copy(loading = false) }
        }
    }
}