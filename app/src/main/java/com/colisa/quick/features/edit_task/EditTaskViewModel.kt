package com.colisa.quick.features.edit_task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import com.colisa.quick.core.data.models.Task
import com.colisa.quick.core.data.service.LogService
import com.colisa.quick.core.data.service.StorageService
import com.colisa.quick.core.ui.base.QuickViewModel
import com.colisa.quick.features.edit_task.navigation.EditTaskDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    savedStateHandle: SavedStateHandle
) : QuickViewModel(logService) {

    private val taskId: String? = savedStateHandle[EditTaskDestination.taskIdArg]

    var task by mutableStateOf(Task())
        private set

    fun initialize() {
        launchCatching {
            taskId?.let { id ->
                task = storageService.getTask(id) ?: Task()
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
        launchCatching {
            if (task.id.isBlank()) storageService.saveTask(task)
            else storageService.updateTask(task)
            editTaskComplete()
        }
    }

    companion object {
        private const val UTC = "UTC"
        private const val DATE_FORMAT = "EEE, d MMM yyyy"
    }

}