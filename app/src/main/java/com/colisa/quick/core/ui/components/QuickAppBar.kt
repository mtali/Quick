package com.colisa.quick.core.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource


@Composable
fun QuickAppBar(@StringRes title: Int) {
    TopAppBar(
        title = { Text(text = stringResource(id = title)) },
        backgroundColor = toolbarColor()
    )
}


@Composable
fun QuickAppBar(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    @DrawableRes endActionIcon: Int,
    endAction: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = title)) },
        backgroundColor = toolbarColor(),
        actions = {
            Box(modifier = modifier) {
                IconButton(onClick = endAction) {
                    Icon(
                        painter = painterResource(id = endActionIcon),
                        contentDescription = "action"
                    )
                }
            }
        }
    )
}

@Composable
private fun toolbarColor(darkTheme: Boolean = isSystemInDarkTheme()): Color {
    return if (darkTheme) MaterialTheme.colors.secondary else MaterialTheme.colors.primaryVariant
}