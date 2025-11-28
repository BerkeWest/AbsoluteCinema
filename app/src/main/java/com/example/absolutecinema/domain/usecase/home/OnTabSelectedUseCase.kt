package com.example.absolutecinema.domain.usecase.home

import androidx.paging.PagingData
import com.example.absolutecinema.data.movie.MovieRepository
import com.example.absolutecinema.domain.model.response.MovieSearchResultDomainModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OnTabSelectedUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(params: Params): Flow<PagingData<MovieSearchResultDomainModel>> {
        return when (params.tabIndex) {
            0 -> repository.getNowPlayingPager()
            1 -> repository.getUpcomingPager()
            2 -> repository.getTopRatedPager()
            else -> repository.getPopularPager()
        }
    }

    data class Params(
        val tabIndex: Int
    )
}
