package com.example.absolutecinema.presentation.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.absolutecinema.BuildConfig
import com.example.absolutecinema.R
import com.example.absolutecinema.domain.model.response.MovieSearchResultDomainModel
import java.util.Locale


@Composable
fun MovieCard(movie: MovieSearchResultDomainModel, onNavigateToDetails: (movieId: Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(vertical = 4.dp, horizontal = 8.dp),
        //transparan background
        colors = CardDefaults.cardColors(containerColor = Color(0x00242A32)),
        //kartın kendisine clickte filmin detayına gidilir.
        onClick = {
            onNavigateToDetails(movie.id ?: 0)
        }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current) //Coil kütüphanesiyle resim yükleme isteği atılır.
                    .data(BuildConfig.IMAGE_URL + movie.posterPath)// full url oluşturulur
                    .crossfade(true) // yüklenirken animasyon ekler
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(80.dp)
                    .height(120.dp)
                    .clip(RoundedCornerShape(12.dp)),
            )
            Spacer(Modifier.width(5.dp))
            Column(modifier = Modifier.padding(start = 16.dp)) {

                movie.title?.let {
                    Text(
                        it, style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .width(200.dp)
                            .height(24.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White
                    )
                }

                Spacer(Modifier.height(14.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 4.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.rating),
                        contentDescription = "Rating",
                        modifier = Modifier
                            .size(20.dp)
                            .padding(horizontal = 3.dp),
                        tint = colorResource(id = R.color.Rating)
                    )
                    Text(
                        String.format(Locale.US, "%.1f", movie.voteAverage),
                        style = MaterialTheme.typography.bodySmall,
                        color = colorResource(id = R.color.Rating),
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 4.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ticket),
                        contentDescription = "Genre",
                        modifier = Modifier
                            .size(18.dp)
                            .padding(horizontal = 3.dp),
                        tint = Color.White
                    )
                    movie.genre?.let {
                        Text(
                            it,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 4.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "Release Date",
                        modifier = Modifier
                            .size(18.dp)
                            .padding(horizontal = 3.dp),
                        tint = Color.White
                    )
                    Text(
                        movie.releaseDate?.take(4) ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MovieCardPreview() {
    val movie = MovieSearchResultDomainModel(
        genreIds = listOf(1, 2),
        id = 0,
        originalTitle = "Demon Slayer Kimetsu No Yaiba Infinity Castle",
        overview = "",
        popularity = 0.0,
        posterPath = "",
        releaseDate = "2018",
        title = "Demon Slayer Kimetsu No Yaiba Infinity Castle",
        voteAverage = 7.8,
        genre = "action"
    )

    MovieCard(movie, onNavigateToDetails = {

    })
}