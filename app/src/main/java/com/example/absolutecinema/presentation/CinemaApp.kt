package com.example.absolutecinema.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.absolutecinema.presentation.login.LoginPage
import com.example.absolutecinema.presentation.navigation.CinemaNavHost
import com.example.absolutecinema.presentation.navigationbar.NavigationBarRoute
import com.example.absolutecinema.presentation.utils.notifications.NotificationOverlayHost

@Composable
fun CinemaApp(
    navController: NavHostController = rememberNavController(),
    viewModel: LaunchViewModel = hiltViewModel()
) {
    val hasAccess by viewModel.hasAccess.collectAsState()

    if (hasAccess != null) {
        val startDestination: String = if (hasAccess == true) {
            NavigationBarRoute.route
        } else {
            LoginPage.route
        }
        CinemaNavHost(navController = navController, startDestination = startDestination)
    }
    NotificationOverlayHost()
}