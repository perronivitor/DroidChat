package com.example.droidchat.ui.feature.signin

import com.example.droidchat.R
import com.example.droidchat.ui.validator.EmailValidator
import com.example.droidchat.ui.validator.FormValidator
import com.example.droidchat.ui.validator.PasswordValidator

class SignInFormValidator : FormValidator<SignInFormState> {
    override fun validate(formState: SignInFormState): SignInFormState {
        val isEmailValid = EmailValidator.isValid(formState.email)
        val isPasswordValid = PasswordValidator.isValid(formState.password)
        val hasError = listOf(
            isEmailValid,
            isPasswordValid
        ).any { !it }

        return formState.copy(
            emailError = if (!isEmailValid) R.string.error_message_email_invalid else null,
            passwordError = if (!isPasswordValid) R.string.error_message_password_invalid else null,
            hasError = hasError
        )
    }
}