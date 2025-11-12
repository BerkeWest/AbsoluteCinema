package com.example.absolutecinema.presentation.navigationbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.absolutecinema.R
import com.example.absolutecinema.presentation.home.HomeScreen
import com.example.absolutecinema.presentation.login.AuthViewModel
import com.example.absolutecinema.presentation.navigation.NavigationDestination
import com.example.absolutecinema.presentation.search.SearchScreen
import com.example.absolutecinema.presentation.utils.Notification
import com.example.absolutecinema.presentation.utils.NotificationManager
import com.example.absolutecinema.presentation.utils.TopAppBar
import com.example.absolutecinema.presentation.watchlist.WatchListScreen

object NavigationBarRoute : NavigationDestination {
    override val route = "navBar"
}

@Composable
fun NavigationBarScreen(
    onNavigateToDetails: (movieId: Int) -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentDestination = Destination.entries.find { it.route == currentRoute }

    val startDestination = Destination.HOME
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

    val authViewModel: AuthViewModel = hiltViewModel()

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
                accountAccess = { currentDestination == Destination.WATCH_LIST },
                logout = {
                    authViewModel.logout()
                    onLogout()
                },
            )
        },
        bottomBar = {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.8.dp)
                        .background(Color(0xFF2A80FF))
                )
                NavigationBar(
                    modifier = Modifier.height(90.dp),
                    containerColor = Color(0xFF242A32),
                    windowInsets = NavigationBarDefaults.windowInsets,
                ) {
                    Destination.entries.forEachIndexed { index, destination ->
                        NavigationBarItem(
                            selected = selectedDestination == index,
                            onClick = {
                                navController.navigate(route = destination.route)
                                selectedDestination = index
                                NotificationManager.show {
                                    Notification(
                                        message = index.toString().repeat(3000),
                                        icon = R.drawable.visibility_off,
                                        onClick = { /* navigate */ },
                                        onDismiss = { NotificationManager.dismiss() }
                                    )
                                }
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
        },
        containerColor = Color(0xFF242A32)
    ) { contentPadding ->
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