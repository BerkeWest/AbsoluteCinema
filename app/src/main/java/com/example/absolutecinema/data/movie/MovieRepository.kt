package com.example.absolutecinema.data.movie

import com.example.absolutecinema.data.SessionManager
import com.example.absolutecinema.data.remote.model.request.LoginBody
import com.example.absolutecinema.data.remote.model.request.TokenBody


class MovieRepository(
    private val api: MovieApiService,
    private val sessionManager: SessionManager
){

}