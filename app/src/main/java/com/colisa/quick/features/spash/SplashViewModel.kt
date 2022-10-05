package com.colisa.quick.features.spash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


internal const val SPLASH_TIMEOUT = 1000L

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    var showError by mutableStateOf(false)
        private set


    fun onAppStarUp(onSplashFinished: () -> Unit) {
        viewModelScope.launch {
            delay(1000)
            onSplashFinished()
        }
    }

}