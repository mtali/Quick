package com.colisa.quick.features.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.colisa.quick.core.common.exts.card
import com.colisa.quick.core.common.exts.spacer
import com.colisa.quick.core.ui.components.*
import com.colisa.quick.R.drawable as AppIcon
import com.colisa.quick.R.string as AppText

@Composable
@ExperimentalMaterialApi
fun SettingsRoute(
    viewModel: SettingsViewModel = hiltViewModel(),
    openLogin: () -> Unit,
    openSignUp: () -> Unit,
    restartApp: () -> Unit
) {

    val uiState = viewModel.uiState
    LaunchedEffect(Unit) { viewModel.initialize() }

    SettingsScreen(
        uiState = uiState,
        onClickSignIn = { viewModel.onClickLogIn(openLogin) },
        onClickSignUp = { viewModel.onClickSignUp(openSignUp) },
        onClickDeleteAccount = { viewModel.onClickDeleteAccount(restartApp) },
        onClickSignOut = { viewModel.onClickSignOut(restartApp) }
    )
}


@ExperimentalMaterialApi
@Composable
private fun SettingsScreen(
    uiState: SettingsUiState,
    onClickSignIn: () -> Unit,
    onClickSignUp: () -> Unit,
    onClickDeleteAccount: () -> Unit,
    onClickSignOut: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QuickAppBar(title = AppText.settings)

        Spacer(modifier = Modifier.spacer())

        if (uiState.isAnonymous) {
            RegularCardEditor(
                modifier = Modifier.card(),
                title = AppText.sign_in,
                icon = AppIcon.ic_sign_in,
                content = "",
                onEditClick = onClickSignIn
            )

            RegularCardEditor(
                modifier = Modifier.card(),
                title = AppText.create_account,
                icon = AppIcon.ic_create_account,
                content = "",
                onEditClick = onClickSignUp
            )

        } else {

            SignOutCard(signOut = onClickSignOut)

            DangerousCardEditor(
                modifier = Modifier.card(),
                title = AppText.delete_account_title,
                icon = AppIcon.ic_delete_my_account,
                content = "",
                onEditClick = onClickDeleteAccount
            )
        }

    }

}

@ExperimentalMaterialApi
@Composable
fun SignOutCard(signOut: () -> Unit) {
    var showWarningDialog by remember { mutableStateOf(false) }
    RegularCardEditor(
        modifier = Modifier.card(),
        title = AppText.sign_out_title,
        icon = AppIcon.ic_exit,
        content = "",
        onEditClick = {
            showWarningDialog = true
        }
    )

    if (showWarningDialog) {
        AlertDialog(
            title = { Text(stringResource(AppText.sign_out_title)) },
            text = { Text(stringResource(AppText.sign_out_description)) },
            dismissButton = { DialogCancelButton(AppText.cancel) { showWarningDialog = false } },
            confirmButton = {
                DialogConfirmButton(AppText.sign_out) {
                    signOut()
                    showWarningDialog = false
                }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
}