package com.example.absolutecinema.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.absolutecinema.R

enum class NoResultScreenEnum {
    SEARCH,
    WATCHLIST
}

@Composable
fun NoResultScreen(screenName: NoResultScreenEnum) {

    var imageId: Int
    var title: String
    val description = stringResource(R.string.no_result_description)

    when (screenName) {
        NoResultScreenEnum.SEARCH -> {
            imageId = R.drawable.no_result
            title = stringResource(R.string.no_search_result)
        }
        NoResultScreenEnum.WATCHLIST -> {
            imageId = R.drawable.empty_watchlist
            title = stringResource(R.string.watchlist_empty)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 40.dp, horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painterResource(imageId),
            contentDescription = stringResource(R.string.no_result)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall,
            color = Color.White
        )
        Text(
            description,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}

@Preview
@Composable
fun NoSearchResultScreenPreview(){
    NoResultScreen(NoResultScreenEnum.SEARCH)
}

@Preview
@Composable
fun NoWatchlistResultScreenPreview(){
    NoResultScreen(NoResultScreenEnum.WATCHLIST)
}