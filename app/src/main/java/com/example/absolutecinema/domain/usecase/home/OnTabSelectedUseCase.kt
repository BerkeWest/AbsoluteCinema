package com.example.absolutecinema.domain.usecase.home

import androidx.paging.PagingData
import com.example.absolutecinema.data.movie.MovieRepository
import com.example.absolutecinema.data.paging.TabIndexToTab
import com.example.absolutecinema.domain.model.response.MovieSearchResultDomainModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OnTabSelectedUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(params: Params): Flow<PagingData<MovieSearchResultDomainModel>> {
        return repository.getMoviePager(TabIndexToTab(tabIndex = params.tabIndex))
    }

    data class Params(
        val tabIndex: Int
    )
}
