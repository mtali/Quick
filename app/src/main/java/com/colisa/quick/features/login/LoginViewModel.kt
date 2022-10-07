package com.colisa.quick.features.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.colisa.quick.core.common.exts.isValidEmail
import com.colisa.quick.core.data.service.AccountService
import com.colisa.quick.core.data.service.LogService
import com.colisa.quick.core.data.service.StorageService
import com.colisa.quick.core.ui.base.QuickViewModel
import com.colisa.quick.core.ui.snackbar.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.colisa.quick.R.string as AppText

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val logService: LogService,
    private val accountService: AccountService,
    private val storageService: StorageService
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

        viewModelScope.launch(showErrorExceptionHandler) {
            val oldUserId = accountService.getUserId()
            accountService.authenticate(email, password) { error ->
                if (error == null) {
                    updateUserId(oldUserId, onSignInCompleted)
                } else onError(error)
            }
        }
    }

    private fun updateUserId(oldUserId: String, onLogInSuccess: () -> Unit) {
        viewModelScope.launch(showErrorExceptionHandler) {
            val newUserId = accountService.getUserId()
            storageService.updateUserId(oldUserId, newUserId) { error ->
                if (error == null) {
                    updateBusyState(false)
                    onLogInSuccess()
                } else {
                    logService.logNonFatalCrash(error)
                }
            }
        }
    }

    private fun updateBusyState(busy: Boolean) {
        uiState = uiState.copy(busy = busy)
    }


    override fun onError(error: Throwable) {
        super.onError(error)
        updateBusyState(false)
    }

}

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val busy: Boolean = false
)