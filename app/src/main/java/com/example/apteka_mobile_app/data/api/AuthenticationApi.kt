package com.example.apteka_mobile_app.data.api

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

data class Login(
    @SerializedName("login")
    val login: String,
    @SerializedName("password")
    val password: String
)

data class Register(
    @SerializedName("name")
    val name: String,
    @SerializedName("surname")
    val surname: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("email")
    val email: String
)

data class TokenResponse(
    @SerializedName("access")
    val access: String,
    @SerializedName("refresh")
    val refresh: String,
    @SerializedName("userType")
    val userType: UserType
)

data class UserInfo(
    val fullName: String,
    val phone: String
)

enum class UserType {
    CLIENT, EMPLOYEE, NONE
}

interface AuthenticationApi {

    @POST("/auth/login")
    suspend fun login(@Body login: Login): TokenResponse

    @POST("/auth/register")
    suspend fun register(@Body register: Register)

    @GET("/user/info")
    suspend fun getUserInfo(): UserInfo

    @POST("auth/refresh")
    suspend fun refresh(@Header("Authorization") token: String): Response<TokenResponse>
}