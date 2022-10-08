package com.colisa.quick.core.data.service

import com.colisa.quick.core.data.models.Task
import kotlinx.coroutines.flow.Flow

interface StorageService {

    val tasks: Flow<List<Task>>

    suspend fun getTask(taskId: String): Task?
    suspend fun saveTask(task: Task): String
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(taskId: String)
    suspend fun deleteAllUserTasks(userId: String)

}