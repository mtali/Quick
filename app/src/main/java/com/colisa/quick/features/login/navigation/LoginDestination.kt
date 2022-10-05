package com.colisa.quick.features.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.colisa.quick.core.ui.navigation.QuickDestination
import com.colisa.quick.features.login.LoginRoute

object LoginDestination : QuickDestination {
    override val route: String = "login_route"
}

fun NavGraphBuilder.loginGraph(onLoginCompleted: () -> Unit) {
    composable(LoginDestination.route) {
        LoginRoute(onSignInCompleted = onLoginCompleted)
    }
}

