package com.example.apteka_mobile_app.ui.client.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apteka_mobile_app.data.NetworkResponse
import com.example.apteka_mobile_app.data.api.Register
import com.example.apteka_mobile_app.data.repositories.AuthenticationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

val nameSurnameRegex = "^[a-zA-Zа-яА-ЯёЁ]+([\\-'\\s][a-zA-Zа-яА-ЯёЁ]+)?$".toRegex()
val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex()
val phoneRegex = "^\\+?[0-9\\s-]+$".toRegex()
val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}\$".toRegex()

data class RegisterState(
    val name: String = "",
    val surname: String = "",
    val phone: String = "",
    val email: String = "",
    val password: String = "",
    val registerError: Boolean = false,
    val registerErrorText: String = "",
    val registerSuccess: Boolean = false,
    val registerInProgress: Boolean = false
)

class RegisterViewModel(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val internalState = MutableStateFlow(RegisterState())

    private val state: RegisterState
        get() = internalState.value

    val uiState = internalState.asStateFlow()

    fun setName(name: String) {
        viewModelScope.launch {
            internalState.update { it.copy(name = name) }
        }
    }

    fun setSurname(surname: String) {
        viewModelScope.launch {
            internalState.update { it.copy(surname = surname) }
        }
    }

    fun setPhone(phone: String) {
        viewModelScope.launch {
            internalState.update { it.copy(phone = phone) }
        }
    }

    fun setEmail(email: String) {
        viewModelScope.launch {
            internalState.update { it.copy(email = email) }
        }
    }

    fun setPassword(password: String) {
        viewModelScope.launch {
            internalState.update { it.copy(password = password) }
        }
    }

    fun resetRegisterError() {
        viewModelScope.launch {
            internalState.update { it.copy(registerError = false) }
        }
    }

    fun resetRegisterSuccess() {
        viewModelScope.launch {
            internalState.update { it.copy(registerSuccess = false) }
        }
    }

    fun tryRegister() {
        viewModelScope.launch {

            internalState.update {
                it.copy(registerInProgress = true)
            }

            if (!nameSurnameRegex.matches(state.name)) {
                internalState.update {
                    it.copy(
                        registerError = true,
                        registerErrorText = "Имя не может быть пустым"
                    )
                }
                return@launch
            }

            if (!nameSurnameRegex.matches(state.surname)) {
                internalState.update {
                    it.copy(
                        registerError = true,
                        registerErrorText = "Фамилия не может быть пустой"
                    )
                }
                return@launch
            }

            if (!phoneRegex.matches(state.phone)) {
                internalState.update {
                    it.copy(
                        registerError = true,
                        registerErrorText = "Неверный номер"
                    )
                }
                return@launch
            }

            if (!emailRegex.matches(state.email)) {
                internalState.update {
                    it.copy(
                        registerError = true,
                        registerErrorText = "Неверный адрес почты"
                    )
                }
                return@launch
            }

            if (!passwordRegex.matches(state.password)) {
                internalState.update {
                    it.copy(
                        registerError = true,
                        registerErrorText = "Неверный пароль"
                    )
                }
                return@launch
            }

            val register = Register(
                name = state.name,
                surname = state.surname,
                phone = state.phone,
                email = state.email,
                password = state.password
            )

            when (authenticationRepository.register(register)) {
                is NetworkResponse.Success -> internalState.update {
                    it.copy(registerSuccess = true)
                }

                else -> internalState.update {
                    it.copy(
                        registerError = true,
                        registerErrorText = "Что-то пошло не так повторите еще раз"
                    )
                }
            }
        }.invokeOnCompletion {
            internalState.update { it.copy(registerInProgress = false) }
        }
    }
}