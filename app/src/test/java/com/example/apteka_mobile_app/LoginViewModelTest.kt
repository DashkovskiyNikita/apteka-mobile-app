package com.example.apteka_mobile_app

import com.example.apteka_mobile_app.data.NetworkResponse
import com.example.apteka_mobile_app.data.PreferenceManager
import com.example.apteka_mobile_app.data.api.TokenResponse
import com.example.apteka_mobile_app.data.api.UserType
import com.example.apteka_mobile_app.data.repositories.AuthenticationRepository
import com.example.apteka_mobile_app.ui.common.login.LoginViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel
    private lateinit var authenticationRepository: AuthenticationRepository
    private lateinit var preferenceManager: PreferenceManager
    private val dispatchers = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatchers)
        authenticationRepository = mock()
        preferenceManager = mock()
        viewModel = LoginViewModel(authenticationRepository, preferenceManager)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `setLogin should update login`() = runTest {
        viewModel.setLogin("testLogin")
        assertEquals("testLogin", viewModel.uiState.value.login)
    }

    @Test
    fun `setPassword should update password`() = runTest {
        viewModel.setPassword("testPassword")
        assertEquals("testPassword", viewModel.uiState.value.password)
    }

    @Test
    fun `resetLoginSuccess should set loginSuccess to false`() = runTest {
        viewModel.resetLoginSuccess()
        assertEquals(false, viewModel.uiState.value.loginSuccess)
    }

    @Test
    fun `resetLoginError should set loginError to false`() = runTest {
        viewModel.resetLoginError()
        assertEquals(false, viewModel.uiState.value.loginError)
    }

    @Test
    fun `tryLogin with successful response should update preferences and set loginSuccess to true`() =
        runTest {
            whenever(authenticationRepository.login(any()))
                .thenReturn(
                    NetworkResponse.Success(
                        TokenResponse("accessToken", "refreshToken", UserType.CLIENT)
                    )
                )

            viewModel.setLogin("testLogin")
            viewModel.setPassword("testPassword")
            viewModel.tryLogin()

            assertEquals(true, viewModel.uiState.value.loginSuccess)
            assertEquals(false, viewModel.uiState.value.loginError)
            assertEquals(false, viewModel.uiState.value.loginInProgress)
        }

    @Test
    fun `tryLogin with unsuccessful response should set loginError to true`() = runTest {
        whenever(authenticationRepository.login(any())).thenReturn(NetworkResponse.UnknownError(Throwable("Login failed")))

        viewModel.setLogin("testLogin")
        viewModel.setPassword("testPassword")
        viewModel.tryLogin()

        assertEquals(false, viewModel.uiState.value.loginSuccess)
        assertEquals(true, viewModel.uiState.value.loginError)
        assertEquals(false, viewModel.uiState.value.loginInProgress)
    }
}