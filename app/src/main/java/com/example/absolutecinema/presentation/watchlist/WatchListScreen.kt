package com.example.absolutecinema.presentation.watchlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.example.absolutecinema.domain.model.response.MovieSearchResultDomainModel
import com.example.absolutecinema.presentation.components.MovieCard
import com.example.absolutecinema.presentation.components.NoResultScreen
import com.example.absolutecinema.presentation.components.NoResultScreenEnum

@Composable
fun WatchListScreen(
    watchlistViewModel: WatchListViewModel = hiltViewModel(),
    onNavigateToDetails: (movieId: Int) -> Unit
) {

    val uiState by watchlistViewModel.uiState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            watchlistViewModel.loadWatchList()
        }
    }

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.watchlist.isEmpty() -> {
            NoResultScreen(NoResultScreenEnum.WATCHLIST)
        }

        else -> {
            WatchListContent(uiState.watchlist, onNavigateToDetails)
        }
    }
}

@Composable
fun WatchListContent(
    watchlist: List<MovieSearchResultDomainModel>,
    onNavigateToDetails: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        items(watchlist) { movie ->
            MovieCard(movie = movie, onNavigateToDetails)
        }
    }
}

//region Preview
@Preview
@Composable
fun WatchListContentPreview() {
    val watchlist = listOf(
        MovieSearchResultDomainModel(
            id = 1,
            title = "Avatar: The Way of Water",
            posterPath = "poster1.jpg",
            releaseDate = "2023-01-01",
            voteAverage = 7.5,
            genreIds = listOf(1,2,3),
            originalTitle = "Original Title",
            overview = "",
            popularity = 12.00,
            genre = "Scienci Fiction"
        ),
        MovieSearchResultDomainModel(
            id = 2,
            title = "Demon Slayer: Kimetsu No Yaiba Infinity Castle",
            posterPath = "poster2.jpg",
            releaseDate = "2025-02-01",
            voteAverage = 8.0,
            genreIds = listOf(1,2,3),
            originalTitle = "Original Title",
            overview = "",
            popularity = 12.00,
            genre = "Action, Animation, Horror, Thriller, Comedy"
        )
    )
    WatchListContent(
        watchlist = watchlist,
        onNavigateToDetails = {}
    )
}
// endregion