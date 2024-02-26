package com.example.apteka_mobile_app

import com.example.apteka_mobile_app.data.NetworkResponse
import com.example.apteka_mobile_app.data.api.Login
import com.example.apteka_mobile_app.data.api.TokenResponse
import com.example.apteka_mobile_app.data.api.UserType
import com.example.apteka_mobile_app.data.repositories.AuthenticationRepository
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class AuthenticationRepositoryTest {

    private val authenticationRepository = mock<AuthenticationRepository>()

    @Test
    fun login_shouldReturnSuccess() = runBlocking {

        whenever(authenticationRepository.login(any())).thenReturn(
            NetworkResponse.Success(
                TokenResponse("accessToken", "refreshToken", UserType.CLIENT)
            )
        )

        val result = authenticationRepository.login(Login("testLogin", "testPassword"))
        assertTrue(result is NetworkResponse.Success)
    }

    @Test
    fun login_shouldReturnError() = runBlocking {

        whenever(
            authenticationRepository.login(any())).thenReturn(
            NetworkResponse.UnknownError(Throwable("error"))
        )

        val result = authenticationRepository.login(Login("testLogin", "testPassword"))
        assertTrue(result is NetworkResponse.UnknownError)
    }

}