package com.colisa.quick.features.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.colisa.quick.R
import com.colisa.quick.core.common.exts.basicButton
import com.colisa.quick.core.common.exts.fieldModifier
import com.colisa.quick.core.common.exts.spacer
import com.colisa.quick.core.ui.components.BasicButton
import com.colisa.quick.core.ui.components.EmailField
import com.colisa.quick.core.ui.components.PasswordField
import com.colisa.quick.core.ui.components.QuickAppBar

@Composable
fun LoginRoute(viewModel: LoginViewModel = hiltViewModel(), onSignInCompleted: () -> Unit) {
    LoginScreen(
        uiState = viewModel.uiState,
        onEmailChanged = viewModel::onEmailChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
        onClickSignIn = { viewModel.onClickSignIn(onSignInCompleted) }
    )
}


@Composable
private fun LoginScreen(
    uiState: LoginUiState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onClickSignIn: () -> Unit
) {
    Scaffold(
        topBar = {
            QuickAppBar(title = R.string.sign_in)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.spacer())

            val fieldModifier = Modifier.fieldModifier()

            EmailField(value = uiState.email, onNewValue = onEmailChanged, modifier = fieldModifier)
            PasswordField(
                value = uiState.password,
                onNewValue = onPasswordChanged,
                modifier = fieldModifier
            )

            BasicButton(
                text = R.string.sign_in,
                modifier = Modifier.basicButton(),
                action = onClickSignIn
            )
        }

    }
}