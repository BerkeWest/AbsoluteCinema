package com.example.absolutecinema.ui.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.absolutecinema.BuildConfig
import com.example.absolutecinema.R
import com.example.absolutecinema.data.remote.model.request.MovieSearchResult
import java.util.Locale


@Composable
fun MovieCard(movie: MovieSearchResult, onNavigateToDetails: (movieId: Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(vertical = 4.dp, horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        onClick = {
            onNavigateToDetails(movie.id)
        }
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
            Spacer(Modifier.width(5.dp))
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(movie.title, style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .width(200.dp)
                        .height(24.dp), maxLines = 1)
                Spacer(Modifier.height(14.dp))
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 4.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.rating),
                        contentDescription = "Rating",
                        modifier = Modifier.size(20.dp).padding(horizontal = 3.dp),
                        tint = colorResource(id = R.color.Rating))
                    Text(String.format(Locale.US, "%.1f", movie.vote_average),
                        style = MaterialTheme.typography.bodySmall,
                        color = colorResource(id = R.color.Rating),

                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 4.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.ticket),
                        contentDescription = "Genre",
                        modifier = Modifier.size(18.dp).padding(horizontal = 3.dp))
                    //Text(getGenreString(movie.genre_ids))
                }
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 4.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "Release Date",
                        modifier = Modifier.size(18.dp).padding(horizontal = 3.dp)
                        )
                    Text(movie.release_date.take(4), style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Preview
@Composable
fun MovieCardPreview() {
    val movie = MovieSearchResult(
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

    MovieCard(movie, onNavigateToDetails = {

    })
}