package com.example.absolutecinema.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.absolutecinema.presentation.navigation.CinemaNavHost
import com.example.absolutecinema.presentation.utils.NotificationOverlayHost

@Composable
fun CinemaApp(
    navController: NavHostController = rememberNavController()
) {
    CinemaNavHost(navController = navController)
    NotificationOverlayHost()
}