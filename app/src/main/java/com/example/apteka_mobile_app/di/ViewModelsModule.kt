package com.example.apteka_mobile_app.di

import com.example.apteka_mobile_app.ui.client.cart.CartViewModel
import com.example.apteka_mobile_app.ui.client.products.ProductsViewModel
import com.example.apteka_mobile_app.ui.client.register.RegisterViewModel
import com.example.apteka_mobile_app.ui.common.login.LoginViewModel
import com.example.apteka_mobile_app.ui.common.profile.ProfileViewModel
import com.example.apteka_mobile_app.ui.employee.deliveries.DeliveryViewModel
import com.example.apteka_mobile_app.ui.employee.orders.OrdersViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val viewModelsModule = module {
    viewModelOf(::CartViewModel)
    viewModelOf(::ProductsViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::OrdersViewModel)
    viewModelOf(::DeliveryViewModel)
}