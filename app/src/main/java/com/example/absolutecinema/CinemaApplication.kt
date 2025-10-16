package com.example.absolutecinema

import android.app.Application
import com.example.absolutecinema.data.AppContainer
import com.example.absolutecinema.data.DefaultAppContainer

class CinemaApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}