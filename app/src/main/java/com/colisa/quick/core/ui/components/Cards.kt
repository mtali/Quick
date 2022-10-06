package com.colisa.quick.core.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.colisa.quick.core.common.exts.dropdownSelector


@Composable
@ExperimentalMaterialApi
fun RegularCardEditor(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    @DrawableRes icon: Int,
    content: String,
    onEditClick: () -> Unit,
) {
    CardEditor(
        modifier = modifier,
        title = title,
        icon = icon,
        content = content,
        onEditClick = onEditClick,
        highlightColor = MaterialTheme.colors.onSurface
    )
}

@Composable
@ExperimentalMaterialApi
fun DangerousCardEditor(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    @DrawableRes icon: Int,
    content: String,
    onEditClick: () -> Unit,
) {
    CardEditor(
        modifier = modifier,
        title = title,
        icon = icon,
        content = content,
        onEditClick = onEditClick,
        highlightColor = MaterialTheme.colors.primary
    )
}


@ExperimentalMaterialApi
@Composable
private fun CardEditor(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    @DrawableRes icon: Int,
    content: String,
    onEditClick: () -> Unit,
    highlightColor: Color
) {
    Card(
        backgroundColor = MaterialTheme.colors.onPrimary,
        modifier = modifier,
        onClick = onEditClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = title),
                color = highlightColor,
                modifier = Modifier.weight(1f)
            )
            if (content.isNotBlank()) {
                Text(text = content, modifier = Modifier.padding(16.dp, 0.dp))
            }

            Icon(
                painter = painterResource(id = icon),
                contentDescription = "card-editor:icon",
                tint = highlightColor
            )
        }
    }
}

@Composable
@ExperimentalMaterialApi
fun CardSelector(
    @StringRes label: Int,
    options: List<String>,
    selection: String,
    modifier: Modifier,
    onNewValue: (String) -> Unit
) {
    Card(backgroundColor = MaterialTheme.colors.onPrimary, modifier = modifier) {
        DropdownSelector(
            modifier = Modifier.dropdownSelector(),
            label = label,
            options = options,
            selection = selection,
            onNewValue = onNewValue
        )
    }
}
