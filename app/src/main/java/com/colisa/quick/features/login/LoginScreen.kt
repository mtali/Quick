package com.colisa.quick.features.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.colisa.quick.R
import com.colisa.quick.core.common.exts.spacer
import com.colisa.quick.core.ui.components.QuickAppBar

@Composable
fun LoginRoute(viewModel: LoginViewModel = hiltViewModel()) {
    LoginScreen()
}


@Composable
private fun LoginScreen() {
    Scaffold(
        topBar = {
            QuickAppBar(title = R.string.sign_in)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.spacer())
        }

    }
}