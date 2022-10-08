package com.colisa.quick.features.settings.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.colisa.quick.core.ui.navigation.QuickDestination
import com.colisa.quick.features.settings.SettingsRoute

object SettingsDestination : QuickDestination {
    override val route: String = "settings_route"
}

@ExperimentalMaterialApi
@ExperimentalLifecycleComposeApi
fun NavGraphBuilder.settingsGraph(
    openLogin: () -> Unit,
    openSignUp: () -> Unit,
    restartApp: () -> Unit
) {
    composable(SettingsDestination.route) {
        SettingsRoute(openLogin = openLogin, openSignUp = openSignUp, restartApp = restartApp)
    }
}