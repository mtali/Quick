package com.colisa.quick.features.tasks

import androidx.compose.runtime.mutableStateOf
import com.colisa.quick.core.data.models.Task
import com.colisa.quick.core.data.service.ConfigurationService
import com.colisa.quick.core.data.service.LogService
import com.colisa.quick.core.data.service.StorageService
import com.colisa.quick.core.ui.base.QuickViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    private val configurationService: ConfigurationService
) : QuickViewModel(logService) {

    val options = mutableStateOf<List<String>>(listOf())

    val tasks = storageService.tasks

    fun loadTaskOptions() {
        val hasEditOption = configurationService.isShowTaskEditButtonConfig
        options.value = TaskActionOption.getOptions(hasEditOption)
    }

    fun onTaskCheckedChanged(task: Task) {
        launchCatching { storageService.updateTask(task.copy(completed = !task.completed)) }
    }

    fun onClickAddTask(openSettings: () -> Unit) = openSettings()

    fun onClickSettings(openAddTask: () -> Unit) = openAddTask()

    fun onClickTaskAction(task: Task, action: String, openEditTask: (String) -> Unit) {
        when (TaskActionOption.getByTitle(action)) {
            TaskActionOption.EditTask -> openEditTask(task.id)
            TaskActionOption.ToggleFlag -> onClickTaskFlag(task)
            TaskActionOption.DeleteTask -> onClickDeleteTask(task)
        }
    }

    private fun onClickTaskFlag(task: Task) {
        launchCatching { storageService.updateTask(task.copy(flag = !task.flag)) }
    }

    private fun onClickDeleteTask(task: Task) {
        launchCatching { storageService.deleteTask(task.id) }
    }

}