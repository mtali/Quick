package com.colisa.quick.core.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.colisa.quick.R.drawable as AppIcon
import com.colisa.quick.R.string as AppText

@Composable
fun BasicField(
    modifier: Modifier = Modifier,
    @StringRes text: Int,
    value: String,
    onNewValue: (String) -> Unit,
) {
    OutlinedTextField(
        singleLine = true,
        modifier = modifier,
        value = value,
        onValueChange = onNewValue,
        placeholder = {
            Text(text = stringResource(id = text))
        }
    )
}

@Composable
fun EmailField(modifier: Modifier = Modifier, value: String, onNewValue: (String) -> Unit) {
    OutlinedTextField(
        singleLine = true,
        modifier = modifier,
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { Text(stringResource(AppText.email)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email") }
    )
}

@Composable
fun PasswordField(modifier: Modifier = Modifier, value: String, onNewValue: (String) -> Unit) {
    PasswordField(
        value = value,
        placeholder = AppText.password,
        onNewValue = onNewValue,
        modifier = modifier
    )
}

@Composable
fun PasswordRepeatField(
    modifier: Modifier = Modifier,
    value: String,
    onNewValue: (String) -> Unit
) {
    PasswordField(
        value = value,
        placeholder = AppText.repeat_password,
        onNewValue = onNewValue,
        modifier = modifier
    )
}

@Composable
private fun PasswordField(
    modifier: Modifier = Modifier,
    value: String,
    @StringRes placeholder: Int,
    onNewValue: (String) -> Unit,
) {
    var isVisible by remember { mutableStateOf(false) }

    val icon =
        if (isVisible)
            painterResource(id = AppIcon.ic_visibility_on)
        else
            painterResource(id = AppIcon.ic_visibility_off)


    val visualTransformation =
        if (isVisible) VisualTransformation.None else PasswordVisualTransformation()

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { Text(text = stringResource(placeholder)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock") },
        trailingIcon = {
            IconButton(onClick = { isVisible = !isVisible }) {
                Icon(painter = icon, contentDescription = "Visibility")
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = visualTransformation
    )


}