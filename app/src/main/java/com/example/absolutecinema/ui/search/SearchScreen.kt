package com.example.absolutecinema.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.absolutecinema.BuildConfig
import com.example.absolutecinema.R
import com.example.absolutecinema.data.remote.model.request.SearchItem
import com.example.absolutecinema.ui.AppViewModelProvider
import com.example.absolutecinema.ui.navigation.NavigationDestination
import java.util.Locale

object SearchPage : NavigationDestination {
    override val route = "search"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val searchText by searchViewModel.searchText.collectAsState()
    val isSearching by searchViewModel.isSearching.collectAsState()
    val searchResults by searchViewModel.searchResults.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
    ) {
        TextField(
            value = searchText,
            onValueChange = searchViewModel::onSearchTextChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search") },
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (isSearching) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(searchResults) { movie ->
                MovieCard(movie = movie)
            }
        }
    }
}

@Composable
fun MovieCard(movie: SearchItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        onClick = {}
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(BuildConfig.IMAGE_URL + movie.poster_path)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(100.dp)
                    .height(150.dp)
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(movie.title, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(14.dp))
                Text(
                    String.format(Locale.US, "%.1f", movie.vote_average),
                    style = MaterialTheme.typography.bodySmall
                )
                //Text(getGenreString(movie.genre_ids))
                Text(movie.release_date.take(4), style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Preview
@Composable
fun MovieCardPreview() {
    val movie = SearchItem(
        genre_ids = listOf(1, 2),
        id = 0,
        original_title = "Haha",
        overview = "",
        popularity = 0.0,
        poster_path = "",
        release_date = "2018",
        title = "HAHA",
        vote_average = 7.8
    )

    MovieCard(movie)
}