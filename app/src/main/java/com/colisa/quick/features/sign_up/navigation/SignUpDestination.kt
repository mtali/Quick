package com.colisa.quick.features.sign_up.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.colisa.quick.core.ui.navigation.QuickDestination
import com.colisa.quick.features.sign_up.SignUpRoute

object SignUpDestination : QuickDestination {
    override val route: String = "sign_up_route"
}

fun NavGraphBuilder.signUpGraph(onSignUpCompleted: () -> Unit) {
    composable(SignUpDestination.route) {
        SignUpRoute(onSignUpCompleted = onSignUpCompleted)
    }
}