package com.example.absolutecinema.ui.navigationbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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
    onNavigateToDetails: (movieId: Int) -> Unit,
    modifier: Modifier = Modifier.background(Color(0xFF242A32))
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentDestination = Destination.entries.find { it.route == currentRoute }

    val startDestination = Destination.HOME
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = currentDestination?.label ?: Destination.HOME.label,
                canNavigateBack = false,
                navigateUp = { },
                canBookmark = false,
                isBookmarked = false,
                bookmark = { },
            )

        },
        bottomBar = {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFF2A80FF))
                )
                NavigationBar(
                    modifier = Modifier.height(120.dp),
                    containerColor = Color(0xFF242A32),
                    windowInsets = NavigationBarDefaults.windowInsets,
                ) {
                    Destination.entries.forEachIndexed { index, destination ->
                        NavigationBarItem(
                            selected = selectedDestination == index,
                            onClick = {
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
                            alwaysShowLabel = true,
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF0296E5),
                                unselectedIconColor = Color.Gray,
                                selectedTextColor = Color(0xFF0296E5),
                                unselectedTextColor = Color.Gray,
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }

        }) { contentPadding ->
        AppNavHost(
            modifier = Modifier
                .padding(contentPadding)
                .background(Color(0xFF242A32)),
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
            .background(Color(0xFF242A32))
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