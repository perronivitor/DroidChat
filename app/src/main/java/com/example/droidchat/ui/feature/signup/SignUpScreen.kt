package com.example.droidchat.ui.feature.signup

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.example.droidchat.ui.theme.DroidChatTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class  SignUpViewModel @Inject constructor() : ViewModel() {

}

@Composable
fun SignUpRoute() {
    SignUpScreen()
}

@Composable
fun SignUpScreen() {

}

@Preview
@Composable
private fun SignUpScreenPreview() {
    DroidChatTheme {
        SignUpScreen()
    }
}
