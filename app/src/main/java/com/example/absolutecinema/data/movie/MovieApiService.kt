package com.example.absolutecinema.data.movie

import com.example.absolutecinema.data.remote.model.response.HomeResponse
import com.example.absolutecinema.data.remote.model.response.HomeResponseDate
import com.example.absolutecinema.data.remote.model.response.MovieDetails
import com.example.absolutecinema.data.remote.model.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") searchWord: String,
        @Query("include_adult") includeAdult: Boolean = false,
    ): SearchResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): HomeResponseDate

    @GET("movie/popular")
    suspend fun getPopularMovies(): HomeResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): HomeResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): HomeResponseDate

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): MovieDetails





}