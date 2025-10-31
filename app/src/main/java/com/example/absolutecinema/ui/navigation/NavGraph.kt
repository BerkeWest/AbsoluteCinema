package com.example.absolutecinema.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.absolutecinema.ui.detail.DetailPage
import com.example.absolutecinema.ui.detail.DetailScreen
import com.example.absolutecinema.ui.login.LoginPage
import com.example.absolutecinema.ui.login.LoginScreen
import com.example.absolutecinema.ui.navigationbar.NavigationBarRoute
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
            NavigationBarScreen(onNavigateToDetails = { movieId ->
                navController.navigate(DetailPage.route + "/$movieId")
            })
        }

        composable(
            route = DetailPage.route + "/{movieId}",
            arguments = listOf(
            navArgument("movieId") {
                defaultValue = 0
                type = NavType.IntType
            })) { navBackStack ->
            DetailScreen(
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}
