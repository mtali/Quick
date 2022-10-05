package com.colisa.quick.features.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.colisa.quick.core.data.service.AccountService
import com.colisa.quick.core.data.service.LogService
import com.colisa.quick.core.ui.base.QuickViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService
) : QuickViewModel(logService) {

    var uiState by mutableStateOf(SettingsUiState())

    fun initialize() {
        uiState = SettingsUiState(accountService.isAnonymousUser())
    }

    fun onClickLogIn(openLogin: () -> Unit) = openLogin()

    fun onClickSignUp(openSignUp: () -> Unit) = openSignUp()

    fun onClickDeleteAccount(restartApp: () -> Unit) {
        viewModelScope.launch(showErrorExceptionHandler) {

        }
    }

    fun onClickSignOut(restartApp: () -> Unit) {
        viewModelScope.launch(showErrorExceptionHandler) {
            accountService.signOut()
            restartApp()
        }
    }

}

data class SettingsUiState(
    val isAnonymous: Boolean = true
)
