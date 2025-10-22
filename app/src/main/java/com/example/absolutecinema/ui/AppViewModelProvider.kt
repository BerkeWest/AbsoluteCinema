package com.example.absolutecinema.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.savedstate.savedState
import com.example.absolutecinema.AbsoluteCinemaApplication
import com.example.absolutecinema.ui.detail.DetailViewModel
import com.example.absolutecinema.ui.login.AuthViewModel
import com.example.absolutecinema.ui.home.HomeScreenViewModel
import com.example.absolutecinema.ui.search.SearchViewModel
import com.example.absolutecinema.ui.watchlist.WatchListViewModel


object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            AuthViewModel(absolteCinemaApplication().container.authRepository)
        }
        initializer {
            HomeScreenViewModel(absolteCinemaApplication().container.movieRepository)
        }
        initializer {
            SearchViewModel(absolteCinemaApplication().container.movieRepository)
        }
        initializer {
            val savedStateHandle = createSavedStateHandle()
            DetailViewModel(savedStateHandle = savedStateHandle, absolteCinemaApplication().container.movieRepository)
        }
        initializer {
            WatchListViewModel(absolteCinemaApplication().container.movieRepository)
        }
    }
}

fun CreationExtras.absolteCinemaApplication(): AbsoluteCinemaApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as AbsoluteCinemaApplication)
