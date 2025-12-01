package com.example.absolutecinema.presentation.watchlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.absolutecinema.R
import com.example.absolutecinema.domain.model.response.MovieSearchResultDomainModel
import com.example.absolutecinema.presentation.components.MovieCard
import com.example.absolutecinema.presentation.components.NoResultScreen
import com.example.absolutecinema.presentation.components.NoResultScreenEnum
import kotlinx.coroutines.awaitCancellation

@Composable
fun WatchListScreen(
    watchlistViewModel: WatchListViewModel = hiltViewModel(),
    onNavigateToDetails: (movieId: Int) -> Unit
) {
    val watchlistItems = watchlistViewModel.moviesPagingFlow.collectAsLazyPagingItems()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            watchlistItems.refresh()
            awaitCancellation()
        }
    }

    val loadState = watchlistItems.loadState.refresh
    val isLoading = loadState is LoadState.Loading
    val isError = loadState is LoadState.Error
    val isEmpty = watchlistItems.itemCount == 0 && !isLoading && !isError

    when {
        isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        isError -> {
            val errorState = loadState
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Error: ${errorState.error.localizedMessage}",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
                Button(onClick = { watchlistItems.retry() }) {
                    Text(stringResource(R.string.retry))
                }
            }
        }

        isEmpty -> {
            NoResultScreen(NoResultScreenEnum.WATCHLIST)
        }

        else -> {
            WatchListContent(watchlistItems, onNavigateToDetails)
        }
    }
}

@Composable
fun WatchListContent(
    watchlist: LazyPagingItems<MovieSearchResultDomainModel>,
    onNavigateToDetails: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        items(watchlist.itemCount) { index ->
            val movie = watchlist[index]
            if (movie != null) {
                MovieCard(movie = movie, onNavigateToDetails)
            }
        }

        if (watchlist.loadState.append is LoadState.Loading) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
        }

        if (watchlist.loadState.append is LoadState.Error) {
            item {
                val error = watchlist.loadState.append as LoadState.Error
                Text(
                    text = "Error loading more: ${error.error.localizedMessage}",
                    color = Color.Red,
                    modifier = Modifier.padding(8.dp)
                )
                Button(onClick = { watchlist.retry() }) {
                    Text(stringResource(R.string.retry))
                }
            }
        }
    }
}
