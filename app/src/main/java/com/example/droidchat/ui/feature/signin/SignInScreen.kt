package com.example.droidchat.ui.feature.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.droidchat.R
import com.example.droidchat.ui.components.PrimaryButton
import com.example.droidchat.ui.components.PrimaryTextField
import com.example.droidchat.ui.theme.BackgroundGradient

@Composable
fun SignInRoute(
    viewModel: SignInViewModel = viewModel(),
    navigateToSignUp: () -> Unit,
) {
    SignInScreen(
        formState = viewModel.formState,
        onFormEvent = viewModel::onFormEvent,
        onRegisterClick = navigateToSignUp
    )
}

@Composable
fun SignInScreen(
    formState: SignInFormState,
    onFormEvent: (SignInFormEvent) -> Unit,
    onRegisterClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .background(brush = BackgroundGradient)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(78.dp))

        PrimaryTextField(
            value = formState.email,
            onValueChange = { value ->
                onFormEvent(SignInFormEvent.EmailChanged(value))
            },
            modifier = Modifier.padding(
                horizontal = dimensionResource(
                    id = R.dimen.spacing_medium
                )
            ),
            leadingIcon = R.drawable.ic_envelope,
            placeholder = stringResource(id = R.string.feature_login_email),
            keyboardType = KeyboardType.Email,
            errorMessage = formState.emailError?.let { errorMessageId ->
                stringResource(id = errorMessageId)
            }
        )

        Spacer(modifier = Modifier.height(14.dp))


        PrimaryTextField(
            value = formState.password,
            onValueChange = { value ->
                onFormEvent(SignInFormEvent.PasswordChanged(value))
            },
            modifier = Modifier.padding(
                horizontal = dimensionResource(
                    id = R.dimen.spacing_medium
                )
            ),
            placeholder = stringResource(id = R.string.feature_login_password),
            leadingIcon = R.drawable.ic_lock,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            errorMessage = formState.passwordError?.let { errorMessageId ->
                stringResource(id = errorMessageId)
            }
        )

        Spacer(modifier = Modifier.height(98.dp))

        PrimaryButton(
            text = stringResource(id = R.string.feature_login_button),
            isLoading = formState.isLoading,
            modifier = Modifier.padding(
                horizontal = dimensionResource(
                    id = R.dimen.spacing_medium
                )
            ),
            onClick = {
                onFormEvent.invoke(SignInFormEvent.Submit)
            }
        )

        Spacer(modifier = Modifier.height(56.dp))

        val noAccountText = stringResource(id = R.string.feature_login_no_account)
        val registerText = stringResource(id = R.string.feature_login_register)
        val noAccountAndRegisterText = "$noAccountText $registerText"

        val annotatedString = buildAnnotatedString {
            val registerTextStartIndex = noAccountAndRegisterText.indexOf(registerText)
            val registerTextEndIndex = registerTextStartIndex + registerText.length

            append(noAccountAndRegisterText)

            addStyle(
                style = SpanStyle(color = Color.White),
                start = 0,
                end = registerTextEndIndex
            )

            addLink(
                clickable = LinkAnnotation.Clickable(
                    tag = "registerText",
                    styles = TextLinkStyles(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline
                        )
                    ),
                    linkInteractionListener = {
                        onRegisterClick.invoke()
                    }
                ),
                start = registerTextStartIndex,
                end = registerTextEndIndex,
            )
        }

        Text(text = annotatedString)
    }
}

@Preview
@Composable
private fun SignInScreenPreview() {
    SignInScreen(
        formState = SignInFormState(),
        onFormEvent = {},
        onRegisterClick = {}
    )
}