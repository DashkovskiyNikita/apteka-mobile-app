package com.example.apteka_mobile_app.ui.employee.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apteka_mobile_app.data.NetworkResponse
import com.example.apteka_mobile_app.data.api.ClientOrderInfo
import com.example.apteka_mobile_app.data.repositories.OrdersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class OrdersState(
    val orders: List<ClientOrderInfo> = emptyList(),
    val isLoading: Boolean = true
)

class OrdersViewModel(private val ordersRepository: OrdersRepository) : ViewModel() {

    private val internalState = MutableStateFlow(OrdersState())

    private val state: OrdersState
        get() = internalState.value

    val uiState = internalState.asStateFlow()

    init {
        viewModelScope.launch {
            when(val response = ordersRepository.getTodayOrders()){
                is NetworkResponse.Success -> internalState.update {
                    it.copy(orders = response.body)
                }
                else -> {}
            }
        }.invokeOnCompletion {
            internalState.update { it.copy(isLoading = false) }
        }
    }

    fun cancelOrder(orderProduct: ClientOrderInfo){
        viewModelScope.launch {
            when(ordersRepository.cancelOrder(orderId = orderProduct.orderId)){
                is NetworkResponse.Success -> internalState.update {
                    it.copy(orders = state.orders - orderProduct)
                }
                else -> {}
            }
        }
    }

    fun issueOrder(orderProduct: ClientOrderInfo,prescription: Boolean){
        viewModelScope.launch {
            when(
                ordersRepository.issueOrder(
                    orderId = orderProduct.orderId,
                    prescription = prescription
                )
            ){
                is NetworkResponse.Success -> internalState.update {
                    it.copy(orders = state.orders - orderProduct)
                }
                else -> {}
            }
        }
    }


}