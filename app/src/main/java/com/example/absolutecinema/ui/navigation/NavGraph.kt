package com.example.absolutecinema.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.absolutecinema.ui.home.HomePage
import com.example.absolutecinema.ui.home.HomeScreen
import com.example.absolutecinema.ui.search.SearchPage
import com.example.absolutecinema.ui.search.SearchScreen
import com.example.absolutecinema.ui.login.LoginPage
import com.example.absolutecinema.ui.login.LoginScreen


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
            HomeScreen(navigateTo = { navController.navigate(it)})
        }
        composable(route = SearchPage.route) {
            SearchScreen()
        }
    }
}
