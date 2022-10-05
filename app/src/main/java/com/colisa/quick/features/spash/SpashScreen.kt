package com.colisa.quick.features.spash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.colisa.quick.core.ui.components.BasicButton
import kotlinx.coroutines.delay
import com.colisa.quick.R as AppText


@Composable
fun SplashRoute(
    viewModel: SplashViewModel = hiltViewModel(),
    onSplashFinished: () -> Unit
) {
    SplashScreen(
        showError = viewModel.showError,
        onAppStart = {
            viewModel.onAppStarUp(onSplashFinished)
        }
    )
}

@Composable
fun SplashScreen(
    showError: Boolean,
    onAppStart: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (showError) {
            Text(text = stringResource(id = AppText.string.generic_error))
            BasicButton(
                text = AppText.string.try_again,
                action = onAppStart
            )
        } else {
            CircularProgressIndicator(color = MaterialTheme.colors.onBackground)
        }
    }

    LaunchedEffect(true) {
        delay(SPLASH_TIMEOUT)
        onAppStart()
    }
}