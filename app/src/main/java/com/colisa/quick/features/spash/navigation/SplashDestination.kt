package com.colisa.quick.features.spash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.colisa.quick.core.ui.navigation.QuickDestination
import com.colisa.quick.features.spash.SplashRoute

object SplashDestination : QuickDestination {
    override val route: String = "splash_route"
}

fun NavGraphBuilder.splashGraph(onSplashFinished: () -> Unit) {
    composable(SplashDestination.route) {
        SplashRoute(onSplashFinished = onSplashFinished)
    }
}