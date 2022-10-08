package com.colisa.quick.features.settings

import com.colisa.quick.core.data.service.AccountService
import com.colisa.quick.core.data.service.LogService
import com.colisa.quick.core.data.service.StorageService
import com.colisa.quick.core.ui.base.QuickViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService,
    private val storageService: StorageService
) : QuickViewModel(logService) {

    val uiState = accountService.currentUser.map { SettingsUiState(it.isAnonymous) }

    fun onClickLogIn(openLogin: () -> Unit) = openLogin()

    fun onClickSignUp(openSignUp: () -> Unit) = openSignUp()

    fun onClickDeleteAccount(restartApp: () -> Unit) {
        launchCatching {
            storageService.deleteAllUserTasks(accountService.currentUserId)
            accountService.deleteAccount()
            restartApp()
        }
    }

    fun onClickSignOut(restartApp: () -> Unit) {
        launchCatching {
            accountService.signOut()
            restartApp()
        }
    }
}

data class SettingsUiState(
    val isAnonymous: Boolean = true
)
