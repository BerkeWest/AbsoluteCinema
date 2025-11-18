package com.example.absolutecinema.data.movie

import com.example.absolutecinema.data.model.request.WatchListBody
import com.example.absolutecinema.data.model.response.GenreListRemoteDataModel
import com.example.absolutecinema.data.model.response.MovieCastRemoteDataModel
import com.example.absolutecinema.data.model.response.MovieDetailsRemoteDataModel
import com.example.absolutecinema.data.model.response.MovieStateRemoteDataModel
import com.example.absolutecinema.data.model.response.ResultPagesRemoteDataModel
import com.example.absolutecinema.data.model.response.ReviewsRemoteDataModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    //GENRE LIST
    @GET("genre/movie/list")
    suspend fun getGenreList(): GenreListRemoteDataModel


    // SEARCH
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") searchWord: String,
        @Query("include_adult") includeAdult: Boolean = false,
    ): ResultPagesRemoteDataModel


    // HOME
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): ResultPagesRemoteDataModel

    @GET("movie/popular")
    suspend fun getPopularMovies(): ResultPagesRemoteDataModel

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): ResultPagesRemoteDataModel

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): ResultPagesRemoteDataModel


    // DETAILS
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): MovieDetailsRemoteDataModel

    @GET("movie/{movie_id}/account_states")
    suspend fun getMovieAccountStates(
        @Path("movie_id") movieId: Int,
    ): MovieStateRemoteDataModel

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") movieId: Int
    ): MovieCastRemoteDataModel

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int
    ): ReviewsRemoteDataModel


    // WATCHLIST
    @GET("account/{account_id}/watchlist/movies")
    suspend fun getWatchlist(
        @Path("account_id") accountId: Int?,
    ): ResultPagesRemoteDataModel

    @POST("account/{account_id}/watchlist")
    suspend fun addToWatchlist(
        @Path("account_id") accountId: Int?,
        @Body watchListBody: WatchListBody
    )
}