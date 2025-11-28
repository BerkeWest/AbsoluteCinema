package com.example.absolutecinema.domain.usecase.search

import androidx.paging.PagingData
import com.example.absolutecinema.data.movie.MovieRepository
import com.example.absolutecinema.domain.model.response.MovieSearchResultDomainModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class SearchUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    operator fun invoke(params: Params): Flow<PagingData<MovieSearchResultDomainModel>> {
        return repository.getSearchPager(params.word)
    }

    data class Params(
        val word: String
    )
}