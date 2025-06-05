package com.example.droidchat.ui.feature.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.droidchat.data.repository.AuthRepository
import com.example.droidchat.model.NetworkException
import com.example.droidchat.ui.feature.signin.SignInViewModel.SignInAction.Error
import com.example.droidchat.ui.validator.FormValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInFormValidator: FormValidator<SignInFormState>,
    private val authRepository: AuthRepository,
) : ViewModel() {

    var formState by mutableStateOf(SignInFormState())
        private set

    private val _signInActionFlow = MutableSharedFlow<SignInAction>()
    val signInActionFlow = _signInActionFlow.asSharedFlow()

    fun onFormEvent(event: SignInFormEvent) {
        when (event) {
            is SignInFormEvent.EmailChanged -> {
                formState = formState.copy(email = event.email, emailError = null)
            }

            is SignInFormEvent.PasswordChanged -> {
                formState = formState.copy(password = event.password, passwordError = null)
            }

            is SignInFormEvent.Submit -> {
                doSignIn()
            }
        }
    }

    private fun doSignIn() {
        if (isValidForm()) {
            formState = formState.copy(isLoading = true)

            viewModelScope.launch {
                authRepository.signIn(
                    username = formState.email,
                    password = formState.password
                ).fold(
                    onSuccess = {
                        formState = formState.copy(isLoading = false)
                        _signInActionFlow.emit(SignInAction.Success)
                    },
                    onFailure = {
                        formState = formState.copy(isLoading = false)
                        (it as? NetworkException.ApiException)?.let { apiException ->
                            when (apiException.statusCode) {
                                401 -> _signInActionFlow.emit(Error.Unauthorized)

                                500 -> _signInActionFlow.emit(Error.Generic)
                            }
                        }
                    }
                )
            }
        }
    }

    private fun isValidForm(): Boolean {
        return !signInFormValidator.validate(formState).also {
            formState = it
        }.hasError
    }

    sealed interface SignInAction {
        data object Success : SignInAction
        data object Idle : SignInAction
        sealed interface Error : SignInAction {
            data object Unauthorized : Error
            data object Generic : Error
        }
    }
}
