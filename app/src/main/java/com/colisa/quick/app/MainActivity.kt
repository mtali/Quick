package com.colisa.quick.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import com.colisa.quick.app.ui.QuickApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalLifecycleComposeApi
@ExperimentalMaterialApi
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { QuickApp() }
    }
}