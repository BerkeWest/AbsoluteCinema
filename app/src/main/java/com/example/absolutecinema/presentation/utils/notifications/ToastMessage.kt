package com.example.absolutecinema.presentation.utils.notifications

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import com.example.absolutecinema.R

fun ShowComposeToast(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    Toast(context).apply {
        view = ComposeView(context).apply {
            setContent {
                MaterialTheme {
                    Notification(
                        message = "Notification 32x32 ikon ve görsel alabilir",
                        icon = R.drawable.ic_broken_image,
                        modifier = Modifier,
                        onClick = {},
                        durationMillis = 2000L,
                        onDismiss = {}
                    )
                }
            }
        }
    }.show()
}