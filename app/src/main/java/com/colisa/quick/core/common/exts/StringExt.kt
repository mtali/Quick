package com.colisa.quick.core.common.exts

import android.util.Patterns
import java.util.regex.Pattern


private const val MIN_PASS_LENGTH = 6
private const val PASS_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$"

fun String.isValidEmail() = isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()


fun String.isValidPassword() =
    isNotBlank() && (length >= MIN_PASS_LENGTH) && Pattern.compile(PASS_PATTERN)
        .matcher(this).matches()

fun String.passwordMatches(repeated: String): Boolean = this == repeated

fun String.idFromParameter() = this.substring(1, this.length - 1)