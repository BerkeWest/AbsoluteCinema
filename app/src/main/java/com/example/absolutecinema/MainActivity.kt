package com.example.absolutecinema

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.absolutecinema.ui.CinemaApp
import com.example.absolutecinema.ui.theme.AbsoluteCinemaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AbsoluteCinemaTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CinemaApp()
                }
            }
        }
    }
}