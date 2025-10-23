package com.example.absolutecinema.data.movie

import com.example.absolutecinema.data.remote.model.request.WatchListBody
import com.example.absolutecinema.data.remote.model.response.GenreList
import com.example.absolutecinema.data.remote.model.response.MovieDetails
import com.example.absolutecinema.data.remote.model.response.MovieState
import com.example.absolutecinema.data.remote.model.response.ResultPages
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    //GENRE LIST
    @GET("genre/movie/list")
    suspend fun getGenreList(): GenreList


    // SEARCH
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") searchWord: String,
        @Query("include_adult") includeAdult: Boolean = false,
    ): ResultPages

    // HOME
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): ResultPages

    @GET("movie/popular")
    suspend fun getPopularMovies(): ResultPages

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): ResultPages

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): ResultPages


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
    ): ResultPages

    @POST("account/{account_id}/watchlist")
    suspend fun addToWatchlist(
        @Path("account_id") accountId: Int?,
        @Body watchListBody: WatchListBody
    )


}