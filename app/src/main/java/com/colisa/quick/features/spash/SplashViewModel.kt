package com.colisa.quick.features.spash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.colisa.quick.core.data.service.AccountService
import com.colisa.quick.core.data.service.ConfigurationService
import com.colisa.quick.core.data.service.LogService
import com.colisa.quick.core.ui.base.QuickViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


internal const val SPLASH_TIMEOUT = 1000L

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountService: AccountService,
    private val logService: LogService,
    configurationService: ConfigurationService
) : QuickViewModel(logService) {

    init {
        configurationService.fetchConfiguration()
    }

    var showError by mutableStateOf(false)
        private set


    fun onAppStarUp(onSplashFinished: () -> Unit) {
        showError = false
        when (accountService.hasUser()) {
            true -> onSplashFinished()
            else -> createAnonymousUser(onSplashFinished)
        }
    }

    private fun createAnonymousUser(onSplashFinished: () -> Unit) {
        viewModelScope.launch(logErrorExceptionHandler) {
            accountService.createAnonymousAccount { error ->
                if (error != null) {
                    showError = true
                    logService.logNonFatalCrash(error)
                } else {
                    onSplashFinished()
                }
            }
        }
    }

}