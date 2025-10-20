package com.example.absolutecinema.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.absolutecinema.R
import com.example.absolutecinema.ui.AbsoluteCinemaTopAppBar
import com.example.absolutecinema.ui.AppViewModelProvider
import com.example.absolutecinema.ui.navigation.NavigationDestination
import com.example.absolutecinema.ui.search.SearchScreen

object HomePage : NavigationDestination {
    override val route = "home"
}

data class BotomNavigationItem(
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val destination: String
)


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateTo: (String) -> Unit,
    homeViewModel: HomeScreenViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {

    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                when (selectedItemIndex) {
                    0 -> AbsoluteCinemaTopAppBar(
                        title = "Home",
                        canNavigateBack = false
                    )

                    1 -> AbsoluteCinemaTopAppBar(
                        title = "Search",
                        canNavigateBack = true
                    )

                    2 -> AbsoluteCinemaTopAppBar(
                        title = "Watch List",
                        canNavigateBack = true
                    )
                }
            },
            bottomBar = {
                NavigationBar {
                    homeViewModel.bottomNavigationItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedItemIndex == index,
                            onClick = {
                                selectedItemIndex = index
                                navigateTo(item.destination)
                            },
                            label = { Text(item.title) },
                            alwaysShowLabel = true,
                            icon = {
                                Icon(
                                    painter = painterResource(
                                        id = if (selectedItemIndex == index) {
                                            item.selectedIcon
                                        } else {
                                            item.unselectedIcon
                                        }
                                    ),
                                    contentDescription = item.title
                                )
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        ) {
            HomeBody()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBody() {

    Card(
        modifier = Modifier
            .padding(start = 40.dp, end = 40.dp, top = 300.dp, bottom = 100.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 80.dp)
        ) {
            Text("Entered")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HomeScreenPreview() {

    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }
    val bottomNavigationItems = listOf(
        BotomNavigationItem(
            title = "Home",
            selectedIcon = R.drawable.home_filled,
            unselectedIcon = R.drawable.home,
            destination = "home"
        ),
        BotomNavigationItem(
            title = "Search",
            selectedIcon = R.drawable.search,
            unselectedIcon = R.drawable.search,
            destination = "search"
        ),
        BotomNavigationItem(
            title = "Watch List",
            selectedIcon = R.drawable.watch_list_filled,
            unselectedIcon = R.drawable.watch_list,
            destination = "watchList"
        )
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                when (selectedItemIndex) {
                    0 -> AbsoluteCinemaTopAppBar(
                        title = "Home",
                        canNavigateBack = false
                    )

                    1 -> AbsoluteCinemaTopAppBar(
                        title = "Search",
                        canNavigateBack = true
                    )

                    2 -> AbsoluteCinemaTopAppBar(
                        title = "Watch List",
                        canNavigateBack = true
                    )
                }
            },
            bottomBar = {
                NavigationBar {
                    bottomNavigationItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedItemIndex == index,
                            onClick = {
                                selectedItemIndex = index
                            },
                            label = { Text(item.title) },
                            alwaysShowLabel = true,
                            icon = {
                                Icon(
                                    painter = painterResource(
                                        id = if (selectedItemIndex == index) {
                                            item.selectedIcon
                                        } else {
                                            item.unselectedIcon
                                        }
                                    ),
                                    contentDescription = item.title
                                )
                            },
                        )
                    }
                }
            }
        ) {
            SearchScreen()
        }
    }
}