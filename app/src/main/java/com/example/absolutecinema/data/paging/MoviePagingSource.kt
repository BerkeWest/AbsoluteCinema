package com.example.absolutecinema.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.absolutecinema.data.model.response.MovieSearchResultRemoteDataModel
import com.example.absolutecinema.data.movie.MovieApiService


class MoviePagingSource(
    private val call: PagingEnum,
    private val api: MovieApiService
) : PagingSource<Int, MovieSearchResultRemoteDataModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieSearchResultRemoteDataModel> {
        return try {
            val page = params.key ?: 1

            val response = when (call) {
                PagingEnum.NOW_PLAYING -> api.getNowPlayingMovies(page)
                PagingEnum.UPCOMING -> api.getUpcomingMovies(page)
                PagingEnum.TOP_RATED -> api.getTopRatedMovies(page)
                PagingEnum.POPULAR -> api.getPopularMovies(page)
            }

            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page < response.totalPages) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieSearchResultRemoteDataModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}