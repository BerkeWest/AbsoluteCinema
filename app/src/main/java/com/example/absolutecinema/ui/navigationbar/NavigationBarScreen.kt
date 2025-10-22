package com.example.absolutecinema.ui.navigationbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.absolutecinema.R
import com.example.absolutecinema.ui.home.HomeScreen
import com.example.absolutecinema.ui.navigation.NavigationDestination
import com.example.absolutecinema.ui.search.SearchScreen
import com.example.absolutecinema.ui.topbar.TopAppBar
import com.example.absolutecinema.ui.watchlist.WatchListScreen

object NavigationBarRoute : NavigationDestination {
    override val route = "navBar"
}

@Composable
fun NavigationBarScreen(
    onNavigateToDetails: (movieId: Int) -> Unit
) {
    val navController = rememberNavController()
    val startDestination = Destination.HOME
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }
    Scaffold(
        modifier = Modifier,
        bottomBar = {
            NavigationBar(
                windowInsets = NavigationBarDefaults.windowInsets
            ) {
                Destination.entries.forEachIndexed { index, destination ->
                    NavigationBarItem(
                        selected = selectedDestination == index, onClick = {
                            navController.navigate(route = destination.route)
                            selectedDestination = index
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = destination.icon),
                                contentDescription = destination.label
                            )
                        },
                        label = { Text(destination.label) },
                        alwaysShowLabel = true
                    )
                }
            }

        }) { contentPadding ->
        AppNavHost(
            modifier = Modifier.padding(contentPadding),
            navController = navController,
            startDestination = startDestination,
            onNavigateToDetails = onNavigateToDetails
        )
    }
}

@Composable
fun AppNavHost(
    modifier: Modifier,
    navController: NavHostController,
    startDestination: Destination,
    onNavigateToDetails: (movieId: Int) -> Unit
) {
    NavHost(
        navController,
        startDestination = startDestination.route,
        modifier = modifier
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.HOME -> HomeScreen(onNavigateToDetails = onNavigateToDetails)
                    Destination.SEARCH -> SearchScreen(onNavigateToDetails = onNavigateToDetails)
                    Destination.WATCH_LIST -> WatchListScreen(onNavigateToDetails = onNavigateToDetails)
                }
            }
        }
    }
}

enum class Destination(
    val route: String, val label: String, val icon: Int
) {
    HOME("home", "Home", R.drawable.home),
    SEARCH("search", "Search", R.drawable.search),
    WATCH_LIST("watchList", "Watch List", R.drawable.watch_list)
}