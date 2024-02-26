package com.example.apteka_mobile_app.di

import com.example.apteka_mobile_app.data.PreferenceManager
import com.example.apteka_mobile_app.data.repositories.AuthenticationRepository
import com.example.apteka_mobile_app.data.repositories.CartRepository
import com.example.apteka_mobile_app.data.repositories.CategoryRepository
import com.example.apteka_mobile_app.data.repositories.DeliveryRepository
import com.example.apteka_mobile_app.data.repositories.OrdersRepository
import com.example.apteka_mobile_app.data.repositories.ProductsRepository
import com.example.apteka_mobile_app.data.repositories.SupplierRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val repositoriesModule = module {
    singleOf(::PreferenceManager)
    singleOf(::AuthenticationRepository)
    singleOf(::CartRepository)
    singleOf(::CategoryRepository)
    singleOf(::OrdersRepository)
    singleOf(::ProductsRepository)
    singleOf(::SupplierRepository)
    singleOf(::DeliveryRepository)
}