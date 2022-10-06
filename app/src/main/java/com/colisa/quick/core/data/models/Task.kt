package com.colisa.quick.core.data.models

data class Task(
    val id: String = "",
    val title: String = "",
    val priority: String = "",
    val dueDate: String = "",
    val dueTime: String = "",
    val description: String = "",
    val url: String = "",
    val flag: Boolean = false,
    val completed: Boolean = false,
    val userId: String = ""
) {
    fun hasDueDate(): Boolean {
        return dueDate.isNotBlank()
    }

    fun hasDueTime(): Boolean {
        return dueTime.isNotBlank()
    }
}





