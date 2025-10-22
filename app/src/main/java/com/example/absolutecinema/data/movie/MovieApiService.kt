package com.example.absolutecinema.data.movie

import com.example.absolutecinema.data.remote.model.request.Account
import com.example.absolutecinema.data.remote.model.request.WatchListBody
import com.example.absolutecinema.data.remote.model.response.HomeResponse
import com.example.absolutecinema.data.remote.model.response.HomeResponseDate
import com.example.absolutecinema.data.remote.model.response.MovieDetails
import com.example.absolutecinema.data.remote.model.response.MovieState
import com.example.absolutecinema.data.remote.model.response.SearchResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    // SEARCH
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") searchWord: String,
        @Query("include_adult") includeAdult: Boolean = false,
    ): SearchResponse

    // HOME
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): HomeResponseDate

    @GET("movie/popular")
    suspend fun getPopularMovies(): HomeResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): HomeResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): HomeResponseDate


    // DETAILS
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): MovieDetails

    @GET("movie/{movie_id}/account_states")
    suspend fun getMovieAccountStates(
        @Path("movie_id") movieId: Int,
    ): MovieState


    // WATCHLIST
    @GET("account/{account_id}/watchlist/movies")
    suspend fun getWatchlist(
        @Path("account_id") accountId: Int?,
    ): SearchResponse

    @POST("account/{account_id}/watchlist")
    suspend fun addToWatchlist(
        @Path("account_id") accountId: Int?,
        @Body watchListBody: WatchListBody
        )




}