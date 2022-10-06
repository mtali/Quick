package com.colisa.quick.features.tasks

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.colisa.quick.core.common.exts.spacer
import com.colisa.quick.core.common.exts.toolbarActions
import com.colisa.quick.core.data.models.Task
import com.colisa.quick.core.ui.components.QuickAppBar
import com.colisa.quick.R.drawable as AppIcon
import com.colisa.quick.R.string as AppText

@ExperimentalMaterialApi
@Composable
fun TasksRoute(
    viewModel: TasksViewModel = hiltViewModel(),
    openSettings: () -> Unit,
    openAddTask: () -> Unit
) {
    DisposableEffect(viewModel) {
        viewModel.addListener()
        viewModel.loadTaskOptions()
        onDispose { viewModel.removeListener() }
    }

    val tasks = viewModel.tasks.values.toList()

    TasksScreen(
        tasks = tasks,
        onAddClick = { viewModel.onClickSettings(openAddTask) },
        onClickSettings = { viewModel.onClickAddTask(openSettings) },
        onTaskCheckedChanged = viewModel::onTaskCheckedChanged
    )
}


@ExperimentalMaterialApi
@Composable
fun TasksScreen(
    tasks: List<Task>,
    onAddClick: () -> Unit,
    onClickSettings: () -> Unit,
    onTaskCheckedChanged: (Task) -> Unit
) {
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
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            item {
                Spacer(modifier = Modifier.spacer())
            }

            items(tasks, key = { it.id }) { task ->
                TaskItem(
                    task = task,
                    options = emptyList(),
                    onCheckChange = { onTaskCheckedChanged(task) },
                    onClickAction = {}
                )
            }
        }
    }
}

