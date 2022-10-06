package com.colisa.quick.features.edit_task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.colisa.quick.core.data.models.Task
import com.colisa.quick.core.data.service.AccountService
import com.colisa.quick.core.data.service.LogService
import com.colisa.quick.core.data.service.StorageService
import com.colisa.quick.core.ui.base.QuickViewModel
import com.colisa.quick.features.edit_task.navigation.EditTaskDestination
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    private val accountService: AccountService,
    savedStateHandle: SavedStateHandle
) : QuickViewModel(logService) {

    private val taskId: String? = savedStateHandle[EditTaskDestination.taskIdArg]

    var task by mutableStateOf(Task())
        private set

    fun initialize() {
        taskId?.let { id ->
            viewModelScope.launch(showErrorExceptionHandler) {
                storageService.getTask(id, ::onError) {
                    task = it
                }
            }
        }
    }

    fun onTitleChange(newValue: String) {
        task = task.copy(title = newValue)
    }

    fun onDescriptionChange(newValue: String) {
        task = task.copy(description = newValue)
    }

    fun onUrlChange(newValue: String) {
        task = task.copy(url = newValue)
    }

    fun onDateChange(newValue: Long) {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone(UTC))
        calendar.timeInMillis = newValue
        val newDueDate = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH).format(calendar.time)
        task = task.copy(dueDate = newDueDate)
    }

    fun onTimeChange(hour: Int, minute: Int) {
        val newDueTime = "${hour.toClockPattern()}:${minute.toClockPattern()}"
        task = task.copy(dueTime = newDueTime)
    }

    fun onPriorityChange(newValue: String) {
        task = task.copy(priority = newValue)
    }

    fun onFlagToggle(newValue: String) {
        val newFlagOption = EditFlagOption.getBooleanValue(newValue)
        task = task.copy(flag = newFlagOption)
    }

    private fun Int.toClockPattern(): String {
        return if (this < 10) "0$this" else "$this"
    }

    fun onClickDone(editTaskComplete: () -> Unit) {
        viewModelScope.launch(showErrorExceptionHandler) {
            val editedTask = task.copy(userId = accountService.getUserId())
            if (editedTask.id.isBlank()) {
                saveTask(editedTask, editTaskComplete)
            } else {
                updateTask(editedTask, editTaskComplete)
            }
        }
    }

    private fun updateTask(editedTask: Task, editTaskComplete: () -> Unit) {
        val updateTaskTrace = Firebase.performance.newTrace(UPDATE_TASK_TRACE)
        updateTaskTrace.start()
        storageService.updateTask(editedTask) { error ->
            updateTaskTrace.stop()
            if (error == null) editTaskComplete()
            else onError(error)
        }
    }

    private fun saveTask(editedTask: Task, editTaskComplete: () -> Unit) {
        val saveTaskTrace = Firebase.performance.newTrace(SAVE_TASK_TRACE)
        saveTaskTrace.start()
        storageService.saveTask(editedTask) { error ->
            saveTaskTrace.stop()
            if (error == null) editTaskComplete()
            else onError(error)
        }
    }

    companion object {
        private const val UTC = "UTC"
        private const val DATE_FORMAT = "EEE, d MMM yyyy"
        private const val SAVE_TASK_TRACE = "saveTask"
        private const val UPDATE_TASK_TRACE = "updateTask"
    }

}