package com.example.absolutecinema.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.absolutecinema.R
import com.example.absolutecinema.ui.AppViewModelProvider
import com.example.absolutecinema.ui.card.MovieCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onNavigateToDetails: (movieId: Int) -> Unit
) {
    val uiState by searchViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
    ) {
        TextField(
            value = uiState.searchText,
            onValueChange = searchViewModel::onSearchTextChange, //pass the value to the function on change
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search") },
            singleLine = true,
            shape = MaterialTheme.shapes.large,

            trailingIcon = {
                Icon(painterResource(R.drawable.search), contentDescription = "Search Icon")
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
        Spacer(modifier = Modifier.height(16.dp))
        if (uiState.searchResults.isEmpty() && uiState.searchAttempted) {
            NoResultScreen()
        } else if (uiState.isSearching) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize() ,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(uiState.searchResults) { movie ->
                    MovieCard(
                        movie = movie,
                        onNavigateToDetails = onNavigateToDetails
                    )
                }
            }
        }
    }
}


@Composable
fun NoResultScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 40.dp, horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painterResource(R.drawable.no_result),
            contentDescription = "No Result"
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "We Are Sorry, We Can Not Find The Movie :(",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall,
            color = Color.White
        )
        Text(
            "Find your movie by type title, categories, years, etc.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}