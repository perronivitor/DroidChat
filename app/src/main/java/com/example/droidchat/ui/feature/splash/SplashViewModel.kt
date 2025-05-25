package com.example.droidchat.ui.feature.splash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.droidchat.data.repository.AuthRepository
import com.example.droidchat.model.NetworkException
import com.example.droidchat.ui.feature.splash.SplashViewModel.AuthenticationState.UserAuthenticated
import com.example.droidchat.ui.feature.splash.SplashViewModel.AuthenticationState.UserNotAuthenticated
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _authenticationState = MutableSharedFlow<AuthenticationState>()
    val authenticationState = _authenticationState.asSharedFlow()

    var showErrorDialogState by mutableStateOf(false)
        private set

    fun checkSession() {
        dismissErrorDialog()

        viewModelScope.launch {
            val accessToken = authRepository.getAccessToken()

            if (accessToken.isNullOrBlank()) {
                _authenticationState.emit(UserNotAuthenticated)
                return@launch
            }

            authRepository.authenticate(accessToken).fold(
                onSuccess = {
                    _authenticationState.emit(UserAuthenticated)
                },
                onFailure = {
                    if (it is NetworkException.ApiException && it.statusCode == 401) {
                        authRepository.cleanAccessToken()
                        _authenticationState.emit(UserNotAuthenticated)
                    } else {
                        showErrorDialogState = true
                    }
                }
            )
        }
    }

    fun dismissErrorDialog() {
        showErrorDialogState = false
    }

    sealed interface AuthenticationState {
        data object UserAuthenticated : AuthenticationState
        data object UserNotAuthenticated : AuthenticationState
    }
}