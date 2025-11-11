package com.example.absolutecinema.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absolutecinema.base.onError
import com.example.absolutecinema.base.onLoading
import com.example.absolutecinema.base.onSuccess
import com.example.absolutecinema.data.model.response.MovieDetailsDomainModel
import com.example.absolutecinema.data.model.response.MovieStateDomainModel
import com.example.absolutecinema.domain.usecase.detail.BookmarkUseCase
import com.example.absolutecinema.domain.usecase.detail.LoadDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val loadDetailsUseCase: LoadDetailsUseCase,
    private val bookmarkUseCase: BookmarkUseCase,
) : ViewModel() {

    //Değiştirilebilir UI state
    private val _uiState = MutableStateFlow(DetailUIState())

    //UI için read. only UI state verisi
    val uiState: StateFlow<DetailUIState> = _uiState.asStateFlow()

    //navigation graphden gelen idyi çekerek kaydeder.
    private val movieId: Int = checkNotNull(savedStateHandle["movieId"])

    init {
        loadDetails()
    }

    private fun loadDetails() {
        _uiState.update { it.copy(isLoading = true) }
        loadDetailsUseCase.invoke(LoadDetailsUseCase.Params(movieId))
            .onSuccess { (movieDetails, movieState) ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        movieDetails = movieDetails,
                        movieState = movieState
                    )
                }
            }.onError { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        snackBarMessage = error.localizedMessage
                    )
                }
            }.launchIn(viewModelScope)
    }


    fun bookmark() {
        bookmarkUseCase.invoke(
            BookmarkUseCase.Params(
                _uiState.value.movieState?.watchlist ?: false,
                _uiState.value.movieDetails?.id ?: 0
            )
        ).onLoading {
            _uiState.update { it.copy(isLoading = true) }
        }.onSuccess {data ->
            _uiState.update {
                it.copy(
                    movieState = it.movieState?.copy(
                        watchlist = data
                    ),
                    isLoading = false
                )
            }
        }.onError {
            _uiState.update {
                it.copy(isLoading = false)
            }
        }.launchIn(viewModelScope)

    }

    fun getGenreString(genres: List<String>): String {
        return genres.joinToString(", ")
    }

}

data class DetailUIState(
    val isLoading: Boolean = false,
    val snackBarMessage: String? = null,
    val movieDetails: MovieDetailsDomainModel? = null,
    val movieState: MovieStateDomainModel? = null
)
