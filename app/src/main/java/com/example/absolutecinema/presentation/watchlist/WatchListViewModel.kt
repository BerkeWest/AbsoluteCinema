package com.example.absolutecinema.presentation.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.absolutecinema.domain.model.response.MovieSearchResultDomainModel
import com.example.absolutecinema.domain.usecase.watchlist.LoadWatchListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel @Inject constructor(
    loadWatchListUseCase: LoadWatchListUseCase
) : ViewModel() {

    val moviesPagingFlow: Flow<PagingData<MovieSearchResultDomainModel>> =
        loadWatchListUseCase.invoke()
            .cachedIn(viewModelScope)

}