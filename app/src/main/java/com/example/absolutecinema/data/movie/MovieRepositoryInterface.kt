package com.example.absolutecinema.data.movie

import androidx.paging.PagingData
import com.example.absolutecinema.data.model.response.CastRemoteDataModel
import com.example.absolutecinema.data.model.response.MovieDetailsRemoteDataModel
import com.example.absolutecinema.data.model.response.MovieSearchResultRemoteDataModel
import com.example.absolutecinema.data.model.response.MovieStateRemoteDataModel
import com.example.absolutecinema.data.model.response.ResultPagesRemoteDataModel
import com.example.absolutecinema.data.model.response.ReviewsRemoteDataModel
import com.example.absolutecinema.domain.model.response.MovieSearchResultDomainModel
import kotlinx.coroutines.flow.Flow

interface MovieRepositoryInterface {
    fun getGenreMap(): Flow<Map<Int, String>>

    fun search(word: String): Flow<List<MovieSearchResultRemoteDataModel>>

    fun getSearchPager(word: String): Flow<PagingData<MovieSearchResultDomainModel>>

    fun getDetails(id: Int): Flow<MovieDetailsRemoteDataModel>

    fun getMovieState(id: Int): Flow<MovieStateRemoteDataModel>

    fun getCast(id: Int): Flow<List<CastRemoteDataModel>>

    fun getReviews(id: Int): Flow<ReviewsRemoteDataModel>

    fun getRecommendations(id: Int): Flow<ResultPagesRemoteDataModel>

    fun getWatchList(): Flow<PagingData<MovieSearchResultDomainModel>>

    fun addToWatchlist(movieId: Int, add: Boolean): Flow<Unit>

    fun getPopular(): Flow<ResultPagesRemoteDataModel>

    fun getNowPlayingPager(): Flow<PagingData<MovieSearchResultDomainModel>>

    fun getUpcomingPager(): Flow<PagingData<MovieSearchResultDomainModel>>

    fun getTopRatedPager(): Flow<PagingData<MovieSearchResultDomainModel>>

    fun getPopularPager(): Flow<PagingData<MovieSearchResultDomainModel>>
}