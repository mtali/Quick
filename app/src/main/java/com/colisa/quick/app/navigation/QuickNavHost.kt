package com.colisa.quick.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.colisa.quick.app.ui.QuickAppState
import com.colisa.quick.features.spash.navigation.SplashDestination
import com.colisa.quick.features.spash.navigation.splashGraph

@Composable
fun QuickNavHost(
    modifier: Modifier,
    appState: QuickAppState
) {
    NavHost(
        navController = appState.navController,
        startDestination = SplashDestination.route,
        modifier = modifier
    ) {
        splashGraph()
    }
}