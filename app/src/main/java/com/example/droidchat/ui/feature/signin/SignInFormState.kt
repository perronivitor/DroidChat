package com.example.droidchat.ui.feature.signin

import androidx.annotation.IntegerRes

data class SignInFormState(
    val email: String = "",
    @IntegerRes
    val emailError: Int? = null,
    val password: String = "",
    @IntegerRes
    val passwordError: Int? = null,
    val isLoading: Boolean = false
)