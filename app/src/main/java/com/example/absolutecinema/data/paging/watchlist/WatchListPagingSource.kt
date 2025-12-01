package com.example.absolutecinema.data.paging.watchlist

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.absolutecinema.data.model.response.MovieSearchResultRemoteDataModel
import com.example.absolutecinema.data.movie.MovieApiService
import retrofit2.HttpException

class WatchListPagingSource(
    private val accountId: Int?,
    private val api: MovieApiService
) : PagingSource<Int, MovieSearchResultRemoteDataModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieSearchResultRemoteDataModel> {
        return try {
            val page = params.key ?: 1

            val response = api.getWatchlist(accountId = accountId, page = page)

            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page < response.totalPages) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
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