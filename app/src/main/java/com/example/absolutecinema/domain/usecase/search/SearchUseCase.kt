package com.example.absolutecinema.domain.usecase.search

import com.example.absolutecinema.data.movie.MovieRepository
import com.example.absolutecinema.di.IoDispatcher
import com.example.absolutecinema.domain.mapper.MovieSearchResultDomainMapper.toDomain
import com.example.absolutecinema.domain.model.response.MovieSearchResultDomainModel
import com.example.absolutecinema.domain.usecase.generic.FlowUseCase
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchUseCase @Inject constructor(
    private val repository: MovieRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<SearchUseCase.Params, List<MovieSearchResultDomainModel>>(dispatcher) {

    override fun execute(params: Params): Flow<List<MovieSearchResultDomainModel>> {
        return repository.search(params.word)
            .map { filteredResult ->
                filteredResult
                    .filter { !it.posterPath.isNullOrBlank() }
                    .map {
                        it.toDomain().copy(
                            genre = repository.getGenreNamesByIds(it.genreIds)
                        )
                    }

            }
    }

    data class Params(
        val word: String
    ) : FlowUseCase.Params()
}