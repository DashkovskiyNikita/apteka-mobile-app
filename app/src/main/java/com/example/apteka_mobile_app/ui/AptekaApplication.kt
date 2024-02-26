package com.example.apteka_mobile_app.ui

import android.app.Application
import com.example.apteka_mobile_app.di.networkModule
import com.example.apteka_mobile_app.di.repositoriesModule
import com.example.apteka_mobile_app.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AptekaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AptekaApplication)
            modules(networkModule, repositoriesModule, viewModelsModule)
        }
    }
}