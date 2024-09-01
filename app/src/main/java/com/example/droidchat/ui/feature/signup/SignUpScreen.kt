package com.example.droidchat.ui.feature.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class  SignUpViewModel @Inject constructor() : ViewModel() {

}

@Composable
fun SignUpRoute(
    viewModel: SignUpViewModel = viewModel(),
){
    SignUpScreen()
}

@Composable
fun SignUpScreen() {
        Column(){

        }
}

@Preview
@Composable
private fun SignUpScreenPreview() {
    SignUpScreen()
}