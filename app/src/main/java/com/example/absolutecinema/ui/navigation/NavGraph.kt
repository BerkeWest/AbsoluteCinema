package com.example.absolutecinema.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.absolutecinema.ui.home.NavigationBarRoute
import com.example.absolutecinema.ui.login.LoginPage
import com.example.absolutecinema.ui.login.LoginScreen
import com.example.absolutecinema.ui.navigationbar.NavigationBarScreen


@Composable
fun CinemaNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController, startDestination = LoginPage.route, modifier = modifier
    ) {
        composable(route = LoginPage.route) {
            LoginScreen(navigateToHome = { navController.navigate(NavigationBarRoute.route) })
        }
        composable(route = NavigationBarRoute.route) {
            NavigationBarScreen()
        }
    }
}
