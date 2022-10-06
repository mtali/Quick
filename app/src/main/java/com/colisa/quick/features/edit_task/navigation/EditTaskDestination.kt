package com.colisa.quick.features.edit_task.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.colisa.quick.core.ui.navigation.QuickDestination
import com.colisa.quick.features.edit_task.EditTaskRoute

object EditTaskDestination : QuickDestination {
    const val taskIdArg = "taskId"
    override val route: String = "edit_task_route?$taskIdArg={$taskIdArg}"

    // TODO: Revisit and improve this - route hardcoded :(
    fun createNavRoute(taskId: String? = null): String {
        return if (!taskId.isNullOrBlank()) {
            "edit_task_route?$taskIdArg=$taskId"
        } else {
            route
        }
    }
}

@ExperimentalMaterialApi
fun NavGraphBuilder.editTaskGraph(onEditTaskCompleted: () -> Unit) {
    composable(
        route = EditTaskDestination.route,
        arguments = listOf(
            navArgument(EditTaskDestination.taskIdArg) {
                type = NavType.StringType
                defaultValue = null
                nullable = true
            }
        )
    ) {
        EditTaskRoute(onEditTaskCompleted = onEditTaskCompleted)
    }
}