package com.example.apteka_mobile_app.ui.common.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apteka_mobile_app.data.NetworkResponse
import com.example.apteka_mobile_app.data.PreferenceManager
import com.example.apteka_mobile_app.data.api.Login
import com.example.apteka_mobile_app.data.repositories.AuthenticationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginState(
    val login: String = "",
    val password: String = "",
    val loginError: Boolean = false,
    val loginSuccess: Boolean = false,
    val loginInProgress: Boolean = false,
)

class LoginViewModel(
    private val authenticationRepository: AuthenticationRepository,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val internalState = MutableStateFlow(LoginState())

    private val state: LoginState
        get() = internalState.value

    val uiState = internalState.asStateFlow()

    fun setLogin(login: String) {
        viewModelScope.launch {
            internalState.update { it.copy(login = login) }
        }
    }

    fun setPassword(password: String) {
        viewModelScope.launch {
            internalState.update { it.copy(password = password) }
        }
    }

    fun resetLoginSuccess() {
        viewModelScope.launch {
            internalState.update { it.copy(loginSuccess = false) }
        }
    }

    fun resetLoginError() {
        viewModelScope.launch {
            internalState.update { it.copy(loginError = false) }
        }
    }

    fun tryLogin() {
        viewModelScope.launch {

            internalState.update { it.copy(loginInProgress = true) }

            val login = Login(login = state.login, password = state.password)

            when (val response = authenticationRepository.login(login = login)) {
                is NetworkResponse.Success -> {
                    with(preferenceManager) {
                        setAccessToken(response.body.access)
                        setRefreshToken(response.body.refresh)
                        setUserType(response.body.userType)
                        setIsUserAuthorized(true)
                    }
                    internalState.update { it.copy(loginSuccess = true) }
                }

                else -> internalState.update { it.copy(loginError = true) }
            }

        }.invokeOnCompletion {
            internalState.update { it.copy(loginInProgress = false) }
        }
    }
}