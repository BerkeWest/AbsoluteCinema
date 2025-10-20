package com.example.absolutecinema.ui.home

import androidx.lifecycle.ViewModel
import com.example.absolutecinema.data.movie.MovieRepository
import com.example.absolutecinema.R

class HomeScreenViewModel(private val repository: MovieRepository) : ViewModel() {
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
}