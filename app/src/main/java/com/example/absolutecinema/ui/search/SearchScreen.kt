package com.example.absolutecinema.ui.search

import android.graphics.Movie
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.absolutecinema.BuildConfig
import com.example.absolutecinema.R
import com.example.absolutecinema.data.remote.model.request.SearchItem
import com.example.absolutecinema.data.remote.model.response.MovieDetails
import com.example.absolutecinema.ui.AppViewModelProvider
import com.example.absolutecinema.ui.navigation.NavigationDestination

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


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = searchText,
            onValueChange = searchViewModel::onSearchTextChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search") },
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn() {

        }
    }
}

@Composable
fun MovieCard(movie: MovieDetails) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(){
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(BuildConfig.IMAGE_URL + movie.poster_path)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            Column() {
                Text(movie.title, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(14.dp))
                Text("${movie.vote_average}", style = MaterialTheme.typography.titleSmall, )
                Text("genre")
                Text(movie.release_date)
                Text("$movie.runtime")

            }
        }
    }
}