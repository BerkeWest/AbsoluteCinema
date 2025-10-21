package com.example.absolutecinema.data.movie

import com.example.absolutecinema.data.SessionManager
import com.example.absolutecinema.data.remote.model.request.SearchItem


class MovieRepository(
    private val api: MovieApiService,
    private val sessionManager: SessionManager
) {
    suspend fun search(word: String): List<SearchItem> {
        val response = api.searchMovies(word)
        return response.results
    }

}

fun getGenreString(genres: List<Int>): String {
    return genres.joinToString(", ")
}