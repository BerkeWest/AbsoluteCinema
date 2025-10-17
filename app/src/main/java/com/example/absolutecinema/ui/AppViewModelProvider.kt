package com.example.absolutecinema.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.absolutecinema.AbsoluteCinemaApplication
import com.example.absolutecinema.ui.login.AuthViewModel
import com.example.absolutecinema.ui.home.HomeScreenViewModel


object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            AuthViewModel(absolteCinemaApplication().container.authRepository)
        }
        initializer {
            HomeScreenViewModel(absolteCinemaApplication().container.movieRepository)
        }
    }
}

fun CreationExtras.absolteCinemaApplication(): AbsoluteCinemaApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as AbsoluteCinemaApplication)
