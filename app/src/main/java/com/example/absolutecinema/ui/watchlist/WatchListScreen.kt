package com.example.absolutecinema.ui.watchlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.absolutecinema.R
import com.example.absolutecinema.data.remote.model.request.MovieSearchResult
import com.example.absolutecinema.ui.AppViewModelProvider
import com.example.absolutecinema.ui.card.MovieCard

@Composable
fun WatchListScreen(
    watchlistViewModel: WatchListViewModel = viewModel(factory = AppViewModelProvider.Factory),
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
            EmptyWatchList()
        }

        else -> {
            WatchListContent(uiState.watchlist, onNavigateToDetails)
        }
    }
}

@Composable
fun WatchListContent(watchlist: List<MovieSearchResult>, onNavigateToDetails: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        items(watchlist) { movie ->
            MovieCard(movie = movie, genre= movie.genre, onNavigateToDetails)
        }
    }
}


@Composable
fun EmptyWatchList() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 40.dp, horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.empty_watchlist),
            contentDescription = "Watch List Empty"
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "There Is No Movie Yet!",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall,
            color = Color.White
        )
        Text(
            "Find your movie by Type title, categories, years, etc ",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.width(200.dp),
            color = Color.Gray
        )
    }
}

@Preview
@Composable
fun EmptyWatchListPreview() {
    EmptyWatchList()
}
