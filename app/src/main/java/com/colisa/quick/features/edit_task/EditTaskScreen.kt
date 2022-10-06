package com.colisa.quick.features.edit_task

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.colisa.quick.R
import com.colisa.quick.core.common.exts.card
import com.colisa.quick.core.common.exts.fieldModifier
import com.colisa.quick.core.common.exts.spacer
import com.colisa.quick.core.common.exts.toolbarActions
import com.colisa.quick.core.data.models.Priority
import com.colisa.quick.core.data.models.Task
import com.colisa.quick.core.ui.components.BasicField
import com.colisa.quick.core.ui.components.CardSelector
import com.colisa.quick.core.ui.components.QuickAppBar
import com.colisa.quick.core.ui.components.RegularCardEditor
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.colisa.quick.R.drawable as AppIcon
import com.colisa.quick.R.string as AppText


@Composable
@ExperimentalMaterialApi
fun EditTaskRoute(viewModel: EditTaskViewModel = hiltViewModel(), onEditTaskCompleted: () -> Unit) {
    LaunchedEffect(Unit) { viewModel.initialize() }
    EditTaskScreen(
        task = viewModel.task,
        onClickDone = { viewModel.onClickDone(onEditTaskCompleted) },
        onTitleChange = viewModel::onTitleChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onUrlChange = viewModel::onUrlChange,
        onDateChange = viewModel::onDateChange,
        onTimeChange = viewModel::onTimeChange,
        onPriorityChange = viewModel::onPriorityChange,
        onFlagToggle = viewModel::onFlagToggle
    )
}

@Composable
@ExperimentalMaterialApi
private fun EditTaskScreen(
    task: Task,
    onClickDone: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onUrlChange: (String) -> Unit,
    onDateChange: (Long) -> Unit,
    onTimeChange: (Int, Int) -> Unit,
    onPriorityChange: (String) -> Unit,
    onFlagToggle: (String) -> Unit
) {
    Scaffold(
        topBar = {
            QuickAppBar(
                title = R.string.edit_task,
                modifier = Modifier.toolbarActions(),
                endActionIcon = AppIcon.ic_check,
                endAction = {
                    onClickDone()
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val fieldModifier = Modifier.fieldModifier()

            Spacer(modifier = Modifier.spacer())

            BasicField(
                text = AppText.title,
                value = task.title,
                onNewValue = onTitleChange,
                modifier = fieldModifier
            )
            BasicField(
                text = AppText.description,
                value = task.description,
                onNewValue = onDescriptionChange,
                modifier = fieldModifier
            )
            BasicField(
                text = AppText.url,
                value = task.url,
                onNewValue = onUrlChange,
                modifier = fieldModifier
            )

            Spacer(modifier = Modifier.spacer())

            CardEditors(task.dueDate, task.dueTime, onDateChange, onTimeChange)

            CardSelectors(task, onPriorityChange, onFlagToggle)

            Spacer(modifier = Modifier.spacer())
        }
    }
}


@Composable
@ExperimentalMaterialApi
private fun CardSelectors(
    task: Task,
    onPriorityChange: (String) -> Unit,
    onFlagToggle: (String) -> Unit
) {
    val prioritySelection = Priority.getByName(task.priority).name
    CardSelector(
        AppText.priority,
        Priority.getOptions(),
        prioritySelection,
        Modifier.card()
    ) { newValue ->
        onPriorityChange(newValue)
    }

    val flagSelection = EditFlagOption.getByCheckedState(task.flag).name
    CardSelector(
        AppText.flag,
        EditFlagOption.getOptions(),
        flagSelection,
        Modifier.card()
    ) { newValue ->
        onFlagToggle(newValue)
    }
}


@Composable
@ExperimentalMaterialApi
private fun CardEditors(
    dueDate: String,
    dueTime: String,
    onDateChange: (Long) -> Unit,
    onTimeChange: (Int, Int) -> Unit
) {
    val activity = LocalContext.current as AppCompatActivity

    RegularCardEditor(
        modifier = Modifier.card(),
        title = AppText.date,
        icon = AppIcon.ic_calendar,
        content = dueDate
    ) {
        showDatePicker(activity, onDateChange)
    }

    RegularCardEditor(
        modifier = Modifier.card(),
        title = AppText.time,
        icon = AppIcon.ic_clock,
        content = dueTime
    ) {
        showTimePicker(activity, onTimeChange)
    }
}

private fun showDatePicker(activity: AppCompatActivity?, onDateChange: (Long) -> Unit) {
    val picker = MaterialDatePicker.Builder.datePicker().build()
    activity?.let {
        picker.show(it.supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener { timeInMillis ->
            onDateChange(timeInMillis)
        }
    }
}

private fun showTimePicker(activity: AppCompatActivity?, onTimeChange: (Int, Int) -> Unit) {
    val picker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()
    activity?.let {
        picker.show(it.supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener {
            onTimeChange(picker.hour, picker.minute)
        }
    }
}