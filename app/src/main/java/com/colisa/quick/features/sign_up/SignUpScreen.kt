package com.colisa.quick.features.sign_up


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.colisa.quick.core.common.exts.basicButton
import com.colisa.quick.core.common.exts.fieldModifier
import com.colisa.quick.core.common.exts.spacer
import com.colisa.quick.core.ui.components.*
import com.colisa.quick.R.string as AppText


@Composable
fun SignUpRoute(viewModel: SignUpViewModel = hiltViewModel(), onSignUpCompleted: () -> Unit) {
    SignUpScreen(
        uiState = viewModel.uiState,
        onEmailChanged = viewModel::onEmailChange,
        onPasswordChanged = viewModel::onPasswordChange,
        onRepeatPasswordChanged = viewModel::onRepeatPasswordChange,
        onClickSignUp = { viewModel.onClickSignUp(onSignUpCompleted) }
    )
}

@Composable
fun SignUpScreen(
    uiState: SignUpUiState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onRepeatPasswordChanged: (String) -> Unit,
    onClickSignUp: () -> Unit
) {

    Scaffold(
        topBar = {
            QuickAppBar(title = AppText.create_account)
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
            val fieldModifier = Modifier.fieldModifier()

            Spacer(modifier = Modifier.spacer())

            EmailField(value = uiState.email, onNewValue = onEmailChanged, modifier = fieldModifier)
            PasswordField(
                value = uiState.password,
                onNewValue = onPasswordChanged,
                modifier = fieldModifier
            )
            PasswordRepeatField(
                value = uiState.repeatPassword,
                onNewValue = onRepeatPasswordChanged,
                modifier = fieldModifier
            )

            BasicButton(
                text = AppText.create_account,
                modifier = Modifier.basicButton(),
                action = onClickSignUp
            )
        }

    }

}