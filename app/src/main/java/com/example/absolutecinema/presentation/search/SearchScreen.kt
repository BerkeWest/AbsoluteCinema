package com.example.absolutecinema.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.absolutecinema.R
import com.example.absolutecinema.domain.model.response.MovieSearchResultDomainModel
import com.example.absolutecinema.presentation.components.MovieCard
import com.example.absolutecinema.presentation.components.NoResultScreen
import com.example.absolutecinema.presentation.components.NoResultScreenEnum
import com.example.absolutecinema.presentation.theme.FocusedSearchBarColor
import com.example.absolutecinema.presentation.theme.UnfocusedSearchBarColor
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    onNavigateToDetails: (movieId: Int) -> Unit
) {
    val uiState by searchViewModel.uiState.collectAsState()
    val searchResultFlow = searchViewModel.searchResultFlow

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
    ) {
        SearchBar(uiState.searchText, searchViewModel::onSearchTextChange)
        Spacer(modifier = Modifier.height(16.dp))
        SearchResults(
            searchResult = searchResultFlow,
            searchAttempted = uiState.searchAttempted,
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
            focusedContainerColor = FocusedSearchBarColor,
            unfocusedContainerColor = UnfocusedSearchBarColor,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedPlaceholderColor = Color.White,
            unfocusedPlaceholderColor = Color.White,
        )

    )
}

@Composable
private fun SearchResults(
    searchResult: Flow<PagingData<MovieSearchResultDomainModel>>,
    searchAttempted: Boolean,
    onNavigateToDetails: (Int) -> Unit,
) {
    val results = searchResult.collectAsLazyPagingItems()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (results.loadState.refresh is LoadState.Loading && searchAttempted) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(top = 24.dp)
            )
        } else if (results.itemCount == 0 && searchAttempted) {
            NoResultScreen(NoResultScreenEnum.SEARCH)
        } else {
            LazyColumn {
                items(results.itemCount) { index ->
                    val movie = results[index]
                    if (movie != null) {
                        MovieCard(
                            movie = movie,
                            onNavigateToDetails = onNavigateToDetails
                        )
                    }
                }

                // Show loading footer if appending more data
                if (results.loadState.append is LoadState.Loading) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

/*
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
        searchResult = PreviewItems.movieList,
        searchAttempted = true,
        isSearching = false,
        onNavigateToDetails = {}
    )
}

//endregion

 */