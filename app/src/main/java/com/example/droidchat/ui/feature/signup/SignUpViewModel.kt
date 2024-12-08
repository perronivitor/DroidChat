package com.example.droidchat.ui.feature.signup

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.droidchat.R
import com.example.droidchat.data.repository.AuthRepository
import com.example.droidchat.model.CreateAccount
import com.example.droidchat.model.NetworkException
import com.example.droidchat.ui.validator.FormValidator
import com.example.droidchat.util.image.ImageCompressor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val formValidator: FormValidator<SignUpFormState>,
    private val authRepository: AuthRepository,
    private val imageCompressor: ImageCompressor,
) : ViewModel() {

    var formState by mutableStateOf(SignUpFormState())
        private set

    fun onFormEvent(event: SignUpFormEvent) {
        when (event) {
            is SignUpFormEvent.ProfilePhotoUriChanged -> {
                formState = formState.copy(profilePictureUri = event.uri)
                compressImageAndUpdateState(uri = event.uri)
            }

            is SignUpFormEvent.FirstNameChanged -> {
                formState = formState.copy(firstName = event.firstName, firstNameError = null)
            }

            is SignUpFormEvent.LastNameChanged -> {
                formState = formState.copy(lastName = event.lastName, lastNameError = null)
            }

            is SignUpFormEvent.EmailChanged -> {
                formState = formState.copy(email = event.email, emailError = null)
            }

            is SignUpFormEvent.PasswordChanged -> {
                formState = formState.copy(password = event.password, passwordError = null)
                updatePasswordExtraText()
            }

            is SignUpFormEvent.PasswordConfirmationChanged -> {
                formState = formState.copy(passwordConfirmation = event.passwordConfirmation)
                updatePasswordExtraText()
            }

            SignUpFormEvent.OpenProfilePictureModalBottomSheet -> {
                formState = formState.copy(isProfilePictureModalBottomSheetOpen = true)
            }

            SignUpFormEvent.CloseProfilePictureModalBottomSheet -> {
                formState = formState.copy(isProfilePictureModalBottomSheetOpen = false)
            }

            is SignUpFormEvent.Submit -> {
                doSignUp()
            }

        }
    }

    private fun compressImageAndUpdateState(uri: Uri) {
        viewModelScope.launch {
            try {
                formState = formState.copy(isCompressingImage = true)

                val compressedImageFile = imageCompressor.compressAndResizeImage(imageUri = uri)
                formState = formState.copy(profilePictureUri = compressedImageFile.toUri())
            } catch (e: Exception) {
                formState = formState.copy(profilePictureUri = uri)
            } finally {
                formState = formState.copy(isCompressingImage = false)
            }
        }
    }

    private fun updatePasswordExtraText() {
        val isPasswordMatch =
            formState.password.isNotBlank() && formState.password == formState.passwordConfirmation

        formState = formState.copy(
            passwordExtraTextId = if (isPasswordMatch) {
                R.string.feature_sign_up_passwords_match
            } else {
                null
            }
        )
    }

    private fun doSignUp() {
        if (isValidForm()) {
            formState = formState.copy(isLoading = true)
            viewModelScope.launch {
                authRepository.signUp(
                    createAccount = CreateAccount(
                        username = "",
                        password = "",
                        firstName = formState.firstName,
                        lastName = formState.lastName,
                        profilePictureId = null,
                    )
                ).fold(
                    onSuccess = {
                        formState = formState.copy(isLoading = false)
                    },
                    onFailure = {
                        formState = formState.copy(
                            isLoading = false,
                            apiErrorMessageResId = if (it is NetworkException.ApiException) {
                                when (it.statusCode) {
                                    400 -> R.string.error_message_api_form_validation_failed
                                    409 -> R.string.error_message_user_with_username_already_exists
                                    else -> R.string.common_generic_error_title
                                }
                            } else R.string.common_generic_error_title
                        )
                    }
                )
            }
        }
    }

    private fun isValidForm(): Boolean {
        return !formValidator.validate(formState).also {
            formState = it
        }.hasError
    }

    fun errorMessageShown() {
        formState = formState.copy(apiErrorMessageResId = null)
    }
}
