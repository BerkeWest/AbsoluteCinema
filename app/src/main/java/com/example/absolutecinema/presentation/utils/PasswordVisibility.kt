package com.example.absolutecinema.presentation.utils

import androidx.annotation.StringRes
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.absolutecinema.R

data class PasswordState(
    var visible: Boolean = false,
    var icon: Int = R.drawable.visibility_on,
    @StringRes var description: Int = R.string.show_password,
    var visualTransformation: VisualTransformation = PasswordVisualTransformation()
)

enum class PassWordVisibility(
    val state: PasswordState
) {
    VISIBLE(
        PasswordState(
            visible = true,
            icon = R.drawable.visibility_off,
            description = R.string.hide_password,
            visualTransformation = VisualTransformation.None
        )
    ),
    INVISIBLE(
        PasswordState(
            visible = false,
            icon = R.drawable.visibility_on,
            description = R.string.show_password,
            visualTransformation = PasswordVisualTransformation()
        )
    )
}