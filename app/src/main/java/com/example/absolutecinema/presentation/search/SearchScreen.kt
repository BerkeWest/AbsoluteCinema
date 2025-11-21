package com.example.absolutecinema.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.absolutecinema.R
import com.example.absolutecinema.domain.model.response.MovieSearchResultDomainModel
import com.example.absolutecinema.presentation.utils.MovieCard
import com.example.absolutecinema.presentation.utils.NoResultScreen
import com.example.absolutecinema.presentation.utils.NoResultScreenEnum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    onNavigateToDetails: (movieId: Int) -> Unit
) {
    val uiState by searchViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
    ) {
        SearchBar(uiState.searchText, searchViewModel::onSearchTextChange)
        Spacer(modifier = Modifier.height(16.dp))
        SearchResults(
            searchResult = uiState.searchResults,
            searchAttempted = uiState.searchAttempted,
            isSearching = uiState.isSearching,
            onNavigateToDetails = onNavigateToDetails
        )
    }
}

@Composable
private fun SearchBar(
    text: String,
    onSearched: (String) -> Unit
) {
    TextField(
        value = text,
        onValueChange = {
            it
            onSearched.invoke(it)
        },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(stringResource(R.string.search)) },
        singleLine = true,
        shape = MaterialTheme.shapes.large,

        trailingIcon = {
            Icon(
                painterResource(R.drawable.search),
                contentDescription = stringResource(R.string.search)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFF3A3F47),
            unfocusedContainerColor = Color(0xFF67686D),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedPlaceholderColor = Color.White,
            unfocusedPlaceholderColor = Color.White,
        )

    )
}

@Composable
private fun SearchResults(
    searchResult: List<MovieSearchResultDomainModel>,
    searchAttempted: Boolean,
    isSearching: Boolean,
    onNavigateToDetails: (Int) -> Unit,
) {
    Column {
        if (searchResult.isEmpty() && searchAttempted) {
            NoResultScreen(NoResultScreenEnum.SEARCH)
        } else if (isSearching) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(searchResult) { movie ->
                    MovieCard(
                        movie = movie,
                        onNavigateToDetails = onNavigateToDetails
                    )
                }
            }
        }
    }
}

//region Previews

@Preview
@Composable
fun PreviewSearchBar() {
    var text by remember { mutableStateOf("") }
    SearchBar(text) {
        text = it
    }
}

@Preview
@Composable
fun PreviewNoSearchResults() {
    SearchResults(
        searchResult = emptyList(),
        searchAttempted = true,
        isSearching = false,
        onNavigateToDetails = {}
    )
}

@Preview
@Composable
fun PreviewLoadingSearchResults() {
    SearchResults(
        searchResult = emptyList(),
        searchAttempted = false,
        isSearching = true,
        onNavigateToDetails = {}
    )
}

@Preview
@Composable
fun PreviewSearchResults() {
    SearchResults(
        searchResult = listOf(
            MovieSearchResultDomainModel(
                genreIds = listOf(1, 2, 3),
                id = 1,
                originalTitle = "Demon Slayer",
                overview = "Overview",
                popularity = 1.0,
                posterPath = "posterPath",
                releaseDate = "2020",
                title = "Demon Slayer",
                voteAverage = 4.8,
                genre = "Anime, Action, Animation, Drama, Fantasy"
            ),
            MovieSearchResultDomainModel(
                genreIds = listOf(1, 2, 3),
                id = 2,
                originalTitle = "Star Wars",
                overview = "Overview",
                popularity = 1.0,
                posterPath = "posterPath",
                releaseDate = "1900",
                title = "Star Wars",
                voteAverage = 4.5,
                genre = "Action, Sci-Fi"
            )
        ),
        searchAttempted = true,
        isSearching = false,
        onNavigateToDetails = {}
    )
}

//endregion