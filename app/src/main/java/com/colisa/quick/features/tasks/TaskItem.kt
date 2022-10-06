package com.colisa.quick.features.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.colisa.quick.core.common.exts.contextMenu
import com.colisa.quick.core.data.models.Task
import com.colisa.quick.core.ui.components.DropdownContextMenu
import com.colisa.quick.core.ui.theme.DarkOrange
import com.colisa.quick.R.drawable as AppIcon

@ExperimentalMaterialApi
@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    task: Task,
    options: List<String>,
    onCheckChange: () -> Unit,
    onClickAction: (String) -> Unit
) {
    Card(
        modifier = modifier.padding(8.dp, 0.dp, 8.dp, 8.dp),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        ) {
            Checkbox(
                checked = task.completed,
                onCheckedChange = { onCheckChange() },
                modifier = Modifier.padding(8.dp, 0.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(text = task.title, style = MaterialTheme.typography.subtitle2)
                if (task.hasDueDate() || task.hasDueTime()) {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Text(text = getDueDateAndTime(task), fontSize = 12.sp)
                    }
                }
            }

            if (task.flag) {
                Icon(
                    painter = painterResource(AppIcon.ic_flag),
                    tint = DarkOrange,
                    contentDescription = "Flag"
                )
            }
            DropdownContextMenu(
                options = options,
                modifier = Modifier.contextMenu(),
                onActionClick = onClickAction
            )
        }
    }
}

private fun getDueDateAndTime(task: Task): String {
    val stringBuilder = StringBuilder("")

    if (task.hasDueDate()) {
        stringBuilder.append(task.dueDate)
        stringBuilder.append(" ")
    }

    if (task.hasDueTime()) {
        stringBuilder.append("at ")
        stringBuilder.append(task.dueTime)
    }

    return stringBuilder.toString()
}
