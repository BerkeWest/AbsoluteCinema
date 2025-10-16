package com.example.absolutecinema.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.absolutecinema.CinemaApplication
import com.example.absolutecinema.data.AuthViewModel


object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            AuthViewModel(cinemaApplication().container.authRepository)
        }
    }
}

fun CreationExtras.cinemaApplication(): CinemaApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as CinemaApplication)
