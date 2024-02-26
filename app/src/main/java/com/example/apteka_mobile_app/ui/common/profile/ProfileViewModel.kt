package com.example.apteka_mobile_app.ui.common.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apteka_mobile_app.data.NetworkResponse
import com.example.apteka_mobile_app.data.PreferenceManager
import com.example.apteka_mobile_app.data.repositories.AuthenticationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileState(
    val loading: Boolean = true,
    val fullName: String = "",
    val phone: String = "",
    val navigateToLoginScreen: Boolean = false
)

class ProfileViewModel(
    private val preferenceManager: PreferenceManager,
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val internalState = MutableStateFlow(ProfileState())

    val uiState = internalState.asStateFlow()

    init {
        viewModelScope.launch {
            when (val response = authenticationRepository.getUserInfo()) {
                is NetworkResponse.Success -> internalState.update {
                    val userInfo = response.body
                    it.copy(fullName = userInfo.fullName, phone = userInfo.phone)
                }
                else -> {}
            }
        }.invokeOnCompletion {
            internalState.update {
                it.copy(loading = false)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            with(preferenceManager) {
                setAccessToken("")
                setRefreshToken("")
                setIsUserAuthorized(false)
            }
            internalState.update { it.copy(navigateToLoginScreen = true) }
        }
    }

    fun resetNavigateToLoginScreen() {
        viewModelScope.launch {
            internalState.update { it.copy(navigateToLoginScreen = false) }
        }
    }

}