package com.example.absolutecinema.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.absolutecinema.ui.navigation.CinemaNavHost

@Composable
fun CinemaApp(
    navController: NavHostController = rememberNavController()
) {
    CinemaNavHost(navController = navController)
}