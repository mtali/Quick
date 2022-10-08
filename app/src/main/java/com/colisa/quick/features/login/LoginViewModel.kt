package com.colisa.quick.features.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.colisa.quick.core.common.exts.isValidEmail
import com.colisa.quick.core.data.service.AccountService
import com.colisa.quick.core.data.service.LogService
import com.colisa.quick.core.ui.base.QuickViewModel
import com.colisa.quick.core.ui.snackbar.SnackbarManager
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.colisa.quick.R.string as AppText

@HiltViewModel
class LoginViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService
) : QuickViewModel(logService) {

    var uiState by mutableStateOf(LoginUiState())
        private set

    private val email get() = uiState.email
    private val password get() = uiState.password

    fun onEmailChanged(newValue: String) {
        uiState = uiState.copy(email = newValue)
    }

    fun onPasswordChanged(newValue: String) {
        uiState = uiState.copy(password = newValue)
    }

    fun onClickSignIn(onSignInCompleted: () -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }
        if (password.isBlank()) {
            SnackbarManager.showMessage(AppText.empty_password_error)
            return
        }

        updateBusyState(busy = true)

        launchCatching {
            try {
                accountService.authenticate(email, password)
            } catch (exc: FirebaseAuthException) {
                updateBusyState(false)
                throw exc
            }
            onSignInCompleted()
        }
    }


    private fun updateBusyState(busy: Boolean) {
        uiState = uiState.copy(busy = busy)
    }


}

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val busy: Boolean = false
)