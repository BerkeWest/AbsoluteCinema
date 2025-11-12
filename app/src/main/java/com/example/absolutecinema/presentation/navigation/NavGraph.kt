package com.example.absolutecinema.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.absolutecinema.presentation.detail.DetailPage
import com.example.absolutecinema.presentation.detail.DetailScreen
import com.example.absolutecinema.presentation.login.LoginPage
import com.example.absolutecinema.presentation.login.LoginScreen
import com.example.absolutecinema.presentation.navigationbar.NavigationBarRoute
import com.example.absolutecinema.presentation.navigationbar.NavigationBarScreen


@Composable
fun CinemaNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController, startDestination = LoginPage.route, modifier = modifier
    ) {
        composable(route = LoginPage.route) {
            LoginScreen(navigateToHome = { navController.navigate(NavigationBarRoute.route) {popUpTo(0)} })
        }
        composable(route = NavigationBarRoute.route) {
            NavigationBarScreen(
                onNavigateToDetails = { movieId ->
                    navController.navigate(DetailPage.route + "/$movieId")
                },
                onLogout = {
                    navController.navigate(LoginPage.route) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable(
            route = DetailPage.route + "/{movieId}",
            arguments = listOf(
                navArgument("movieId") {
                    defaultValue = 0
                    type = NavType.IntType
                })
        ) { navBackStack ->
            DetailScreen(
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}
