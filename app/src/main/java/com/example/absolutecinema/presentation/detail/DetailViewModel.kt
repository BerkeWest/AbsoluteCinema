package com.example.absolutecinema.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absolutecinema.R
import com.example.absolutecinema.domain.base.onError
import com.example.absolutecinema.domain.base.onLoading
import com.example.absolutecinema.domain.base.onSuccess
import com.example.absolutecinema.data.model.response.CastDomainModel
import com.example.absolutecinema.data.model.response.MovieDetailsDomainModel
import com.example.absolutecinema.data.model.response.MovieStateDomainModel
import com.example.absolutecinema.data.model.response.ReviewResultDomainModel
import com.example.absolutecinema.domain.model.response.MovieSearchResultDomainModel
import com.example.absolutecinema.domain.usecase.detail.BookmarkUseCase
import com.example.absolutecinema.domain.usecase.detail.LoadCastUseCase
import com.example.absolutecinema.domain.usecase.detail.LoadDetailsUseCase
import com.example.absolutecinema.domain.usecase.detail.LoadRecommendationsUseCase
import com.example.absolutecinema.domain.usecase.detail.LoadReviewsUseCase
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
    private val loadCastUseCase: LoadCastUseCase,
    private val loadReviewsUseCase: LoadReviewsUseCase,
    private val loadRecommendationsUseCase: LoadRecommendationsUseCase,
    private val bookmarkUseCase: BookmarkUseCase,
) : ViewModel() {

    //Değiştirilebilir UI state
    private val _uiState = MutableStateFlow(DetailUIState())

    //UI için read. only UI state verisi
    val uiState: StateFlow<DetailUIState> = _uiState.asStateFlow()

    //navigation graphden gelen idyi çekerek kaydeder.
    private val movieId: Int = checkNotNull(savedStateHandle["movieId"])

    val tabs = listOf(R.string.about_movie, R.string.reviews, R.string.cast, R.string.recommendations)


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
        }.onSuccess { data ->
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

    private fun getMovieCast() {
        _uiState.update { it.copy(isLoading = true) }
        loadCastUseCase.invoke(LoadCastUseCase.Params(movieId))
            .onSuccess { cast ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        cast = cast
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

    fun onTabSelected(tabIndex: Int) {
        when (tabIndex) {
            1 -> if (_uiState.value.reviews == null) loadMovieReviews()

            2 -> if (_uiState.value.cast == null) getMovieCast()

            3 -> if (_uiState.value.recommendations == null) loadRecommendations()
        }
        _uiState.update { it.copy(selectedTabIndex = tabIndex) }
    }

    private fun loadMovieReviews() {
        _uiState.update { it.copy(isLoading = true) }
        loadReviewsUseCase.invoke(LoadReviewsUseCase.Params(movieId))
            .onSuccess { reviews ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        reviews = reviews.ifEmpty { null }
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

    private fun loadRecommendations(){
        _uiState.update { it.copy(isLoading = true) }
        loadRecommendationsUseCase.invoke(LoadRecommendationsUseCase.Params(movieId))
            .onSuccess { results ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        recommendations = results.ifEmpty { null }
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
}

data class DetailUIState(
    val isLoading: Boolean = false,
    val snackBarMessage: String? = null,
    val selectedTabIndex: Int = 0,
    val movieDetails: MovieDetailsDomainModel? = null,
    val movieState: MovieStateDomainModel? = null,
    val cast: List<CastDomainModel>? = null,
    val reviews: List<ReviewResultDomainModel>? = null,
    val recommendations: List<MovieSearchResultDomainModel>? = null
)
