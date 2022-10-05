package com.colisa.quick.core.ui.base

import androidx.lifecycle.ViewModel
import com.colisa.quick.core.common.SnackbarManager
import com.colisa.quick.core.common.SnackbarMessage.Companion.toSnackbarMessage
import com.colisa.quick.core.data.service.LogService
import kotlinx.coroutines.CoroutineExceptionHandler

open class QuickViewModel(private val logService: LogService) : ViewModel() {
    open val showErrorExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError(throwable)
    }

    open val logErrorExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        logService.logNonFatalCrash(throwable)
    }

    open fun onError(error: Throwable) {
        SnackbarManager.showMessage(error.toSnackbarMessage())
        logService.logNonFatalCrash(error)
    }
}