package com.example.droidchat.ui.feature.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.droidchat.ui.validator.FormValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInFormValidator: FormValidator<SignInFormState>,
) : ViewModel() {

    var formState by mutableStateOf(SignInFormState())
        private set

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
        resetFormErrorState()
        if (isValidForm()) {
            formState = formState.copy(isLoading = true)
            //Request API
        }
    }


    private fun resetFormErrorState() {
        formState = formState.copy(
            emailError = null,
            passwordError = null
        )
    }

    private fun isValidForm(): Boolean {
        return !signInFormValidator.validate(formState).also {
            formState = it
        }.hasError
    }
}
