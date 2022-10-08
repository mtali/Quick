package com.colisa.quick.features.sign_up

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.colisa.quick.core.common.exts.isValidEmail
import com.colisa.quick.core.common.exts.isValidPassword
import com.colisa.quick.core.common.exts.passwordMatches
import com.colisa.quick.core.data.service.AccountService
import com.colisa.quick.core.data.service.LogService
import com.colisa.quick.core.ui.base.QuickViewModel
import com.colisa.quick.core.ui.snackbar.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.colisa.quick.R.string as AppText

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
) : QuickViewModel(logService) {
    var uiState by mutableStateOf(SignUpUiState())
        private set

    private val email get() = uiState.email
    private val password get() = uiState.password

    fun onEmailChange(newValue: String) {
        uiState = uiState.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState = uiState.copy(password = newValue)
    }

    fun onRepeatPasswordChange(newValue: String) {
        uiState = uiState.copy(repeatPassword = newValue)
    }

    fun onClickSignUp(onSignUpCompleted: () -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (!password.isValidPassword()) {
            SnackbarManager.showMessage(AppText.password_error)
            return
        }

        if (!password.passwordMatches(uiState.repeatPassword)) {
            SnackbarManager.showMessage(AppText.password_match_error)
            return
        }

        launchCatching {
            accountService.linkAccount(email, password)
            onSignUpCompleted()
        }
    }
}

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = ""
)

