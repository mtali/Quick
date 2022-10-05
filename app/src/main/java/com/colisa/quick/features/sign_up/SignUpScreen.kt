package com.colisa.quick.features.sign_up


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.colisa.quick.core.common.exts.spacer
import com.colisa.quick.core.ui.components.QuickAppBar
import com.colisa.quick.R.string as AppText


@Composable
fun SignUpRoute(viewModel: SignUpViewModel = hiltViewModel()) {
    SignUpScreen()
}

@Composable
fun SignUpScreen() {

    Scaffold(
        topBar = {
            QuickAppBar(title = AppText.create_account)
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