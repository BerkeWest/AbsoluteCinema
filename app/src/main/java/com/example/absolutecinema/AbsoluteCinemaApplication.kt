package com.example.absolutecinema

import android.app.Application
import com.example.absolutecinema.data.AppContainer


class AbsoluteCinemaApplication: Application() {
    lateinit var container: AppContainer
        private set
    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}