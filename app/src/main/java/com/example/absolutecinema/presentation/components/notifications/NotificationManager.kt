package com.example.absolutecinema.presentation.components.notifications

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object NotificationManager {
    private val _state = MutableStateFlow<(@Composable () -> Unit)?>(null)
    val state = _state.asStateFlow()

    fun show(content: @Composable () -> Unit) {
        _state.value = content
    }

    fun dismiss() {
        _state.value = null
    }
}

@Composable
fun NotificationOverlayHost() {
    val content by NotificationManager.state.collectAsState()

    Box(Modifier.fillMaxSize().padding(top= 40.dp), contentAlignment = Alignment.TopCenter) {
        content?.invoke()
    }
}
