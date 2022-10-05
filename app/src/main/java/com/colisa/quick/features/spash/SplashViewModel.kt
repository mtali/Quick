package com.colisa.quick.features.spash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


internal const val SPLASH_TIMEOUT = 1000L

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    var showError by mutableStateOf(false)
        private set


    fun onAppStarUp(openAndPopUp: (String, String) -> Unit) {
        showError = false
    }

}