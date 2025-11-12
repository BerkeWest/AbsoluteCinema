package com.example.absolutecinema.presentation.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NotificationHost(
    notificationData: NotificationData?,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp), // top spacing for safe area
        contentAlignment = Alignment.TopCenter
    ) {
        notificationData?.let { data ->
            Notification(
                message = data.message,
                icon = data.icon,
                onClick = data.onClick,
                onDismiss = onDismiss
            )
        }
    }
}

data class NotificationData(
    val message: String,
    val icon: Int,
    val onClick: () -> Unit
)