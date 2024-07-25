package com.example.droidchat.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.droidchat.R
import com.example.droidchat.ui.theme.DroidChatTheme

@Composable
fun PrimaryTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes
    leadingIcon: Int? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    errorMessage: String? = null,
    placeholder: String = "",
    imeAction: ImeAction = ImeAction.Next,
) {
    var passwordVisibility by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
    ) {

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = placeholder)
            },
            leadingIcon = {
                leadingIcon?.let {
                    Image(
                        painter = painterResource(id = leadingIcon),
                        contentDescription = null
                    )
                }
            },
            trailingIcon = {
                if (keyboardType == KeyboardType.Password) {
                    val visibilityIcon = if (passwordVisibility) {
                        R.drawable.ic_visibility
                    } else {
                        R.drawable.ic_visibility_off
                    }

                    Icon(
                        painter = painterResource(id = visibilityIcon),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable {
                            passwordVisibility = !passwordVisibility
                        }
                    )
                }
            },
            visualTransformation = if (keyboardType == KeyboardType.Password) {
                if (passwordVisibility) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                }
            } else {
                VisualTransformation.None
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            singleLine = true,
            shape = CircleShape,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = if (errorMessage != null) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
            )
        )

        errorMessage?.let {
            Text(
                text = it,
                modifier = Modifier.padding(start = 16.dp),
                color = MaterialTheme.colorScheme.error
            )
        }

        /**
         * Implementação pelo BasicTextField
         *
        BasicTextField(
        value = value,
        onValueChange = onValueChange,
        decorationBox = { innerTextField ->
        Surface(shape = CircleShape) {
        Row(
        modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
        ) {
        leadingIcon?.let {
        Image(
        painter = painterResource(id = leadingIcon),
        contentDescription = null
        )

        Spacer(modifier = Modifier.width(8.dp))
        }

        Box(
        modifier = Modifier.weight(1f)
        ) {
        innerTextField()
        }

        Spacer(modifier = Modifier.width(8.dp))

        Image(
        painter = painterResource(id = R.drawable.ic_bottom_nav_profile),
        contentDescription = null
        )
        }
        }
        }
        )

        errorMessage?.let {
        Text(
        text = "Senha é obrigatória",
        modifier = Modifier.padding(start = 16.dp),
        color = ColorError
        )
        }
         **/
    }
}

@Preview
@Composable
private fun PrimaryTextFieldPreview() {
    DroidChatTheme {
        PrimaryTextField(
            value = "",
            onValueChange = {},
            placeholder = "E-mail",
            leadingIcon = R.drawable.ic_envelope,
            keyboardType = KeyboardType.Password
        )
    }
}