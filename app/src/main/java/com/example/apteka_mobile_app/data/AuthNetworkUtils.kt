package com.example.apteka_mobile_app.data

import com.example.apteka_mobile_app.data.api.AuthenticationApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthInterceptor(
    private val preferenceManager: PreferenceManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            preferenceManager.accessTokenFlow.first()
        }
        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "Bearer $token")
        return chain.proceed(request.build())
    }
}

class JwtAuthenticator(
    private val preferenceManager: PreferenceManager,
) : Authenticator, KoinComponent {

    private val authenticationApi: AuthenticationApi by inject()

    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            val refreshToken = preferenceManager.refreshTokenFlow.first()
            val tokenPairResponse = authenticationApi.refresh(token = refreshToken)

            if (!tokenPairResponse.isSuccessful) {
                preferenceManager.setIsUserAuthorized(false)
                return@runBlocking null
            }

            val tokenPair = tokenPairResponse.body() ?: return@runBlocking null

            with(preferenceManager) {
                setAccessToken(tokenPair.access)
                setRefreshToken(tokenPair.refresh)
                setIsUserAuthorized(true)
                setUserType(tokenPair.userType)
            }

            response
                .request
                .newBuilder()
                .addHeader("Authorization", "Bearer ${tokenPair.access}")
                .build()
        }
    }
}