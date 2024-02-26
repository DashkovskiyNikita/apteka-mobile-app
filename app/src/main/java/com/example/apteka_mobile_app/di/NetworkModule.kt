package com.example.apteka_mobile_app.di

import com.example.apteka_mobile_app.data.AuthInterceptor
import com.example.apteka_mobile_app.data.JwtAuthenticator
import com.example.apteka_mobile_app.data.api.AuthenticationApi
import com.example.apteka_mobile_app.data.api.CategoryApi
import com.example.apteka_mobile_app.data.api.DeliveryApi
import com.example.apteka_mobile_app.data.api.OrdersApi
import com.example.apteka_mobile_app.data.api.ProductsApi
import com.example.apteka_mobile_app.data.api.SupplierApi
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal val networkModule = module {

    singleOf(::JwtAuthenticator).bind<Authenticator>()

    single {

        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val authInterceptor = AuthInterceptor(get())

        val okHttpClient = OkHttpClient.Builder()
            .authenticator(get())
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

        Retrofit.Builder()
            .baseUrl("https://relaxed-wildly-osprey.ngrok-free.app")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    single {
        get<Retrofit>().create(AuthenticationApi::class.java)
    }

    single {
        get<Retrofit>().create(CategoryApi::class.java)
    }

    single {
        get<Retrofit>().create(OrdersApi::class.java)
    }

    single {
        get<Retrofit>().create(ProductsApi::class.java)
    }

    single {
        get<Retrofit>().create(SupplierApi::class.java)
    }

    single {
        get<Retrofit>().create(DeliveryApi::class.java)
    }
}