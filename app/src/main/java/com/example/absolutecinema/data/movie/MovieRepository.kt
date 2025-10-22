package com.example.absolutecinema.data.movie

import com.example.absolutecinema.data.SessionManager
import com.example.absolutecinema.data.remote.model.request.MovieSearchResult
import com.example.absolutecinema.data.remote.model.request.WatchListBody
import com.example.absolutecinema.data.remote.model.response.MovieDetails
import com.example.absolutecinema.data.remote.model.response.MovieState
import com.example.absolutecinema.data.remote.model.response.SearchResponse


class MovieRepository(
    private val api: MovieApiService,
    private val sessionManager: SessionManager
) {
    suspend fun search(word: String): List<MovieSearchResult> {
        val response = api.searchMovies(word)
        return response.results
    }

    suspend fun getDetails(id: Int): MovieDetails {
        val response = api.getMovieDetails(id)
        return response
    }

    suspend fun getMovieState(id: Int): MovieState {
        val response = api.getMovieAccountStates(id)
        return response
    }

    suspend fun getWatchList(): List<MovieSearchResult> {
        val accountId = sessionManager.accountId
        val response = api.getWatchlist(accountId)
        return response.results
    }

    suspend fun addToWatchlist(movieId: Int, add: Boolean) {
        val accountId = sessionManager.accountId
        val watchListBody = WatchListBody(media_id = movieId, watchlist = add)
        api.addToWatchlist(accountId, watchListBody)
    }


}

fun getGenreString(genres: List<String>): String {
    return genres.joinToString(", ")
}