package com.example.absolutecinema.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.absolutecinema.ui.screens.HomePage
import com.example.absolutecinema.ui.screens.HomeScreen
import com.example.absolutecinema.ui.screens.LoginPage
import com.example.absolutecinema.ui.screens.LoginScreen


@Composable
fun CinemaNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController, startDestination = LoginPage.route, modifier = modifier
    ) {
        composable(route = LoginPage.route) {
            LoginScreen(navigateToHome = { navController.navigate(HomePage.route) })
        }
        composable(route = HomePage.route) {
            HomeScreen()
        }
    }
}
