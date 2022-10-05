package com.colisa.quick.features.tasks

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor() : ViewModel() {

    fun onClickAddTask(openSettings: () -> Unit) = openSettings()

    fun onClickSettings(openAddTask: () -> Unit) = openAddTask()
}