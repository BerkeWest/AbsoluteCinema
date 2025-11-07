package com.example.absolutecinema.data.movie

import com.example.absolutecinema.data.model.response.MovieDetails
import com.example.absolutecinema.data.model.response.MovieSearchResult
import com.example.absolutecinema.data.model.response.MovieState
import com.example.absolutecinema.data.model.response.ResultPages
import kotlinx.coroutines.flow.Flow

interface MovieRepositoryInterface {
    fun getGenreMap(): Flow<Map<Int, String>>

    fun getGenreNamesByIds(genreIds: List<Int>): Flow<String>

    fun search(word: String): Flow<List<MovieSearchResult>>

    fun getDetails(id: Int): Flow<MovieDetails>

    fun getMovieState(id: Int): Flow<MovieState>

    fun getWatchList(): Flow<List<MovieSearchResult>>

    fun addToWatchlist(movieId: Int, add: Boolean): Flow<Unit>

    fun getNowPlaying(): Flow<ResultPages>

    fun getUpcoming(): Flow<ResultPages>

    fun getTopRated(): Flow<ResultPages>

    fun getPopular(): Flow<ResultPages>

}