package com.colisa.quick.features.tasks

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.colisa.quick.core.data.models.Task
import com.colisa.quick.core.data.service.AccountService
import com.colisa.quick.core.data.service.LogService
import com.colisa.quick.core.data.service.StorageService
import com.colisa.quick.core.ui.base.QuickViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    private val accountService: AccountService
) : QuickViewModel(logService) {

    var tasks = mutableStateMapOf<String, Task>()
        private set

    var options = mutableStateOf<List<String>>(listOf())
        private set

    fun addListener() {
        viewModelScope.launch(showErrorExceptionHandler) {
            storageService.addListener(accountService.getUserId(), ::onDocumentEvent, ::onError)
        }
    }

    fun removeListener() {
        viewModelScope.launch(showErrorExceptionHandler) { storageService.removeListener() }
    }

    fun onTaskCheckedChanged(task: Task) {
        viewModelScope.launch(showErrorExceptionHandler) {
            val updatedTask = task.copy(completed = !task.completed)
            storageService.updateTask(updatedTask) { error ->
                if (error != null) onError(error)
            }
        }
    }

    fun onClickAddTask(openSettings: () -> Unit) = openSettings()

    fun onClickSettings(openAddTask: () -> Unit) = openAddTask()

    fun onClickTaskAction(task: Task, action: String, onTaskActionCompleted: () -> Unit) {

    }

    private fun onClickTaskFlag(task: Task) {
        viewModelScope.launch(showErrorExceptionHandler) {
            val updatedTask = task.copy(flag = !task.flag)
            storageService.updateTask(updatedTask) { error ->
                if (error != null) onError(error)
            }
        }
    }

    private fun onClickDeleteTask(task: Task) {
        viewModelScope.launch(showErrorExceptionHandler) {
            storageService.deleteTask(task.id) { error ->
                if (error != null) onError(error)
            }
        }
    }

    private fun onDocumentEvent(wasDocumentDeleted: Boolean, task: Task) {
        if (wasDocumentDeleted) tasks.remove(task.id) else tasks[task.id] = task
    }

    fun loadTaskOptions() {

    }
}