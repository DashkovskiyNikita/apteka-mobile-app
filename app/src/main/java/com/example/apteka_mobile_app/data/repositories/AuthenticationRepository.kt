package com.example.apteka_mobile_app.data.repositories

import com.example.apteka_mobile_app.data.api.AuthenticationApi
import com.example.apteka_mobile_app.data.api.Login
import com.example.apteka_mobile_app.data.api.Register
import com.example.apteka_mobile_app.data.networkCall

class AuthenticationRepository(
    private val authenticationApi: AuthenticationApi
) {
    suspend fun login(login: Login) = networkCall {
        authenticationApi.login(login = login)
    }

    suspend fun register(register: Register) = networkCall {
        authenticationApi.register(register = register)
    }

    suspend fun getUserInfo() = networkCall {
        authenticationApi.getUserInfo()
    }

}