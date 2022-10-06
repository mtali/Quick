package com.colisa.quick.features.sign_up

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.colisa.quick.core.common.exts.isValidEmail
import com.colisa.quick.core.common.exts.isValidPassword
import com.colisa.quick.core.common.exts.passwordMatches
import com.colisa.quick.core.data.service.AccountService
import com.colisa.quick.core.data.service.LogService
import com.colisa.quick.core.data.service.StorageService
import com.colisa.quick.core.ui.base.QuickViewModel
import com.colisa.quick.core.ui.snackbar.SnackbarManager
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.colisa.quick.R.string as AppText

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val logService: LogService,
    private val accountService: AccountService,
    private val storageService: StorageService
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

        viewModelScope.launch(showErrorExceptionHandler) {
            val oldUserId = accountService.getUserId()
            val createAccountTrace = Firebase.performance.newTrace(CREATE_ACCOUNT_TRACE)
            createAccountTrace.start()
            accountService.linkAccount(email, password) { error ->
                createAccountTrace.stop()
                if (error == null) {
                    onSignUpCompleted()
                    updateUserId(oldUserId, onSignUpCompleted)
                } else onError(error)
            }
        }
    }


    private fun updateUserId(oldUserId: String, onSignUpCompleted: () -> Unit) {
        viewModelScope.launch(showErrorExceptionHandler) {
            val newUserId = accountService.getUserId()
            storageService.updateUserId(oldUserId, newUserId) { error ->
                if (error == null)
                    onSignUpCompleted()
                else
                    logService.logNonFatalCrash(error)
            }
            onSignUpCompleted()
        }
    }


    companion object {
        private const val CREATE_ACCOUNT_TRACE = "createAccount"
    }
}

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = ""
)

