package com.colisa.quick.app.ui

import android.content.res.Resources
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import com.colisa.quick.core.ui.snackbar.SnackbarManager
import com.colisa.quick.core.ui.snackbar.SnackbarMessage.Companion.toMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@Stable
class QuickAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    val snackbarManager: SnackbarManager,
    val coroutineScope: CoroutineScope,
    val resources: Resources
) {
    init {
        coroutineScope.launch {
            snackbarManager.snackbarMessages.filterNotNull().collect { snackbarMessage ->
                val text = snackbarMessage.toMessage(resources)
                scaffoldState.snackbarHostState.showSnackbar(text)
            }
        }
    }

    fun popUp() {
        navController.popBackStack()
    }

    fun navigate(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
        }
    }

    fun navigateAndPopUp(route: String, popUpRoute: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(popUpRoute) {
                inclusive = true
            }
        }
    }

    fun clearAndNavigate(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(0) {
                inclusive = true
            }
        }
    }
}

