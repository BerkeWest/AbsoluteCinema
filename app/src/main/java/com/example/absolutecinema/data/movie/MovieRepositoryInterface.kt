package com.example.absolutecinema.data.movie

import com.example.absolutecinema.data.model.response.CastRemoteDataModel
import com.example.absolutecinema.data.model.response.MovieDetailsRemoteDataModel
import com.example.absolutecinema.data.model.response.MovieSearchResultRemoteDataModel
import com.example.absolutecinema.data.model.response.MovieStateRemoteDataModel
import com.example.absolutecinema.data.model.response.ResultPagesRemoteDataModel
import kotlinx.coroutines.flow.Flow

interface MovieRepositoryInterface {
    fun getGenreMap(): Flow<Map<Int, String>>

    fun search(word: String): Flow<List<MovieSearchResultRemoteDataModel>>

    fun getDetails(id: Int): Flow<MovieDetailsRemoteDataModel>

    fun getMovieState(id: Int): Flow<MovieStateRemoteDataModel>

    fun getCast(id: Int): Flow<List<CastRemoteDataModel>>

    fun getWatchList(): Flow<ResultPagesRemoteDataModel>

    fun addToWatchlist(movieId: Int, add: Boolean): Flow<Unit>

    fun getNowPlaying(): Flow<ResultPagesRemoteDataModel>

    fun getUpcoming(): Flow<ResultPagesRemoteDataModel>

    fun getTopRated(): Flow<ResultPagesRemoteDataModel>

    fun getPopular(): Flow<ResultPagesRemoteDataModel>

}