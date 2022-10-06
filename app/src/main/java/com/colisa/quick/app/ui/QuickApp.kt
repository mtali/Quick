package com.colisa.quick.app.ui

import android.content.res.Resources
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.colisa.quick.app.navigation.QuickNavHost
import com.colisa.quick.core.ui.snackbar.SnackbarManager
import com.colisa.quick.core.ui.theme.QuickTheme
import kotlinx.coroutines.CoroutineScope

@ExperimentalMaterialApi
@Composable
fun QuickApp() {
    QuickTheme {
        Surface(color = MaterialTheme.colors.background) {
            val appState = rememberAppState()
            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = it,
                        modifier = Modifier.padding(8.dp),
                        snackbar = { data ->
                            Snackbar(data, contentColor = MaterialTheme.colors.onPrimary)
                        }
                    )
                },
                scaffoldState = appState.scaffoldState
            ) { padding ->
                QuickNavHost(
                    modifier = Modifier.padding(padding),
                    appState = appState
                )
            }
        }
    }
}

@Composable
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    resources: Resources = resources(),
) = remember(scaffoldState, navController, snackbarManager, coroutineScope, resources) {
    QuickAppState(
        scaffoldState = scaffoldState,
        navController = navController,
        snackbarManager = snackbarManager,
        coroutineScope = coroutineScope,
        resources = resources
    )
}

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}