package com.colisa.quick.features.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.colisa.quick.core.common.exts.toolbarActions
import com.colisa.quick.core.ui.components.QuickAppBar
import com.colisa.quick.R.drawable as AppIcon
import com.colisa.quick.R.string as AppText

@Composable
fun TasksRoute(
    viewModel: TasksViewModel = hiltViewModel(),
    openSettings: () -> Unit,
    openAddTask: () -> Unit
) {
    TasksScreen(
        onAddClick = { viewModel.onClickSettings(openSettings) },
        onClickSettings = { viewModel.onClickAddTask(openAddTask) }
    )
}


@Composable
fun TasksScreen(onAddClick: () -> Unit, onClickSettings: () -> Unit) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                modifier = Modifier.padding(8.dp),
                content = {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "tasks:add")
                }
            )
        },
        topBar = {
            QuickAppBar(
                title = AppText.tasks,
                endActionIcon = AppIcon.ic_settings,
                endAction = onClickSettings,
                modifier = Modifier.toolbarActions()
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

        }
    }
}

