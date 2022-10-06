package com.colisa.quick.app.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.colisa.quick.app.ui.QuickAppState
import com.colisa.quick.features.edit_task.navigation.EditTaskDestination
import com.colisa.quick.features.edit_task.navigation.editTaskGraph
import com.colisa.quick.features.login.navigation.LoginDestination
import com.colisa.quick.features.login.navigation.loginGraph
import com.colisa.quick.features.settings.navigation.SettingsDestination
import com.colisa.quick.features.settings.navigation.settingsGraph
import com.colisa.quick.features.sign_up.navigation.SignUpDestination
import com.colisa.quick.features.sign_up.navigation.signUpGraph
import com.colisa.quick.features.spash.navigation.SplashDestination
import com.colisa.quick.features.spash.navigation.splashGraph
import com.colisa.quick.features.tasks.navigation.TasksDestination
import com.colisa.quick.features.tasks.navigation.tasksGraph

@ExperimentalMaterialApi
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
        splashGraph(
            onSplashFinished = {
                appState.navigateAndPopUp(TasksDestination.route, SplashDestination.route)
            }
        )

        tasksGraph(
            openSettings = { appState.navigate(SettingsDestination.route) },
            openAddTask = { appState.navigate(EditTaskDestination.createNavRoute()) }
        )

        settingsGraph(
            openLogin = { appState.navigate(LoginDestination.route) },
            openSignUp = { appState.navigate(SignUpDestination.route) },
            restartApp = { appState.clearAndNavigate(SplashDestination.route) }
        )

        signUpGraph(
            onSignUpCompleted = {
                appState.navigateAndPopUp(SettingsDestination.route, SignUpDestination.route)
            }
        )

        loginGraph(
            onLoginCompleted = {
                appState.navigateAndPopUp(SettingsDestination.route, LoginDestination.route)
            }
        )

        editTaskGraph(
            onEditTaskCompleted = { appState.popUp() }
        )
    }
}