package com.colisa.quick.features.spash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.colisa.quick.core.data.service.AccountService
import com.colisa.quick.core.data.service.ConfigurationService
import com.colisa.quick.core.data.service.LogService
import com.colisa.quick.core.ui.base.QuickViewModel
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


internal const val SPLASH_TIMEOUT = 1000L

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService,
    configurationService: ConfigurationService
) : QuickViewModel(logService) {

    var showError by mutableStateOf(false)
        private set

    init {
        launchCatching { configurationService.fetchConfiguration() }
    }

    fun onAppStarUp(onSplashFinished: () -> Unit) {
        showError = false
        if (accountService.hasUser) onSplashFinished()
        else createAnonymousUser(onSplashFinished)
    }

    private fun createAnonymousUser(onSplashFinished: () -> Unit) {
        launchCatching {
            try {
                accountService.createAnonymousAccount()
            } catch (exc: FirebaseAuthException) {
                showError = true
                throw exc
            }
            onSplashFinished()
        }
    }

}