package com.example.absolutecinema.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.absolutecinema.BuildConfig
import com.example.absolutecinema.R
import com.example.absolutecinema.data.model.response.AuthorDomainModel
import com.example.absolutecinema.data.model.response.CastDomainModel
import com.example.absolutecinema.data.model.response.ReviewResultDomainModel
import com.example.absolutecinema.presentation.navigation.NavigationDestination
import com.example.absolutecinema.presentation.components.TopAppBar
import com.example.absolutecinema.presentation.detail.components.CastMember
import com.example.absolutecinema.presentation.detail.components.IconText
import com.example.absolutecinema.presentation.detail.components.PlaceholderText
import com.example.absolutecinema.presentation.detail.components.Review
import java.util.Locale

object DetailPage : NavigationDestination {
    override val route = "detail"
}

@Composable
fun DetailScreen(
    navigateBack: () -> Unit,
    detailViewModel: DetailViewModel = hiltViewModel(),
) {

    val uiState by detailViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = Color(0xFF242A32),
        topBar = {
            TopAppBar(
                title = stringResource(R.string.details),
                canNavigateBack = true,
                navigateUp = navigateBack,
                canBookmark = true,
                isBookmarked = uiState.movieState?.watchlist ?: false,
                bookmark = { detailViewModel.bookmark() },
                accountAccess = { false },
                logout = { }
            )
        }
    )
    { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding) //top bar ın tuttuğu alana göre padding alır.
                .background(Color(0xFF242A32)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                BannerAndTitle(
                    backdropPath = uiState.movieDetails?.backdropPath,
                    voteAverage = uiState.movieDetails?.voteAverage,
                    posterPath = uiState.movieDetails?.posterPath,
                    title = uiState.movieDetails?.title ?: ""
                )
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 70.dp)//küçük poster için alan oluştur
                ) {
                    Spacer(Modifier.height(20.dp))

                    IconTextRow(
                        releaseDate = uiState.movieDetails?.releaseDate,
                        runtime = uiState.movieDetails?.runtime.toString(),
                        genre = detailViewModel.getGenreString(uiState.movieDetails?.genres?.map { it.name }
                            ?: listOf())
                    )

                    Spacer(Modifier.height(24.dp))
                }
            }

            item {
                DetailTabs(
                    selectedTabIndex = uiState.selectedTabIndex,
                    tabsList = detailViewModel.tabs,
                    onTabSelected = { index -> detailViewModel.onTabSelected(index) },
                    isLoading = uiState.isLoading,
                    overview = uiState.movieDetails?.overview,
                    reviews = uiState.reviews,
                    cast = uiState.cast,
                )
            }
        }
    }

}

@Composable
private fun BannerAndTitle(
    backdropPath: String?,
    voteAverage: Double?,
    posterPath: String?,
    title: String,
    padding: Int = 10
) {
    Box(Modifier.padding(bottom = padding.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(BuildConfig.IMAGE_URL + backdropPath)
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.banner_image),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
        // Rating
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (-16).dp, y = (-16).dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Black.copy(alpha = 0.3f))
                .clickable { }
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.rating),
                contentDescription = stringResource(R.string.rating),
                tint = Color(0xFFFFA500)
            )
            Spacer(Modifier.width(5.dp))
            Text(
                text = String.format(Locale.US, "%.1f", voteAverage),
                color = Color(0xFFFFA500),
                fontWeight = FontWeight.SemiBold
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = 16.dp, y = 60.dp)
        ) {
            //Küçük Poster
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(BuildConfig.IMAGE_URL + posterPath)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = stringResource(R.string.poster_image),
                modifier = Modifier
                    .width(114.dp)
                    .height(171.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(width = (0.1).dp, Color.Gray, RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(15.dp))
            Text(
                text = title,
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                modifier = Modifier
                    .padding(end = 20.dp)
                    .offset(y = 120.dp)
                    .weight(5f)
            )
        }
    }
}

@Composable
private fun IconTextRow(
    releaseDate: String? = null,
    runtime: String,
    genre: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = 7.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        IconText(
            painterResource(R.drawable.calendar),
            releaseDate?.take(4) ?: "",
            R.string.release_date
        )
        Spacer(
            modifier = Modifier
                .width(1.dp)
                .height(16.dp)
                .background(Color.Gray.copy(alpha = 0.5f))
        )
        IconText(
            painterResource(R.drawable.time),
            "$runtime Minutes",
            R.string.runtime
        )
        Spacer(
            modifier = Modifier
                .width(1.dp)
                .height(16.dp)
                .background(Color.Gray.copy(alpha = 0.5f))
        )
        IconText(
            painterResource(R.drawable.ticket),
            genre,
            R.string.genre
        )
    }
}

@Composable
private fun DetailTabs(
    selectedTabIndex: Int,
    tabsList: List<Int>,
    onTabSelected: (Int) -> Unit,
    isLoading: Boolean,
    overview: String?,
    reviews: List<ReviewResultDomainModel>?,
    cast: List<CastDomainModel>?
) {
    SecondaryTabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = Color.Transparent,
        contentColor = Color.White,
        divider = {}
    ) {
        tabsList.forEachIndexed { index, text ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                text = {
                    Text(
                        text = stringResource(text),
                        color = if (selectedTabIndex == index) Color.White else Color.Gray,
                        fontWeight = if (selectedTabIndex == index)
                            FontWeight.Bold else FontWeight.Normal
                    )
                }
            )
        }
    }

    Spacer(Modifier.height(8.dp))

    when (selectedTabIndex) {

        0 -> if (overview != null) {
            Text(
                text = overview,
                color = Color.White,
                fontSize = 15.sp,
                lineHeight = 22.sp,
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp),
                style = LocalTextStyle.current.copy(
                    textIndent = TextIndent(firstLine = 24.sp)
                )
            )
        } else if (isLoading) CircularProgressIndicator()
        else PlaceholderText(R.string.no_details)

        1 -> if (reviews != null) {
            Column {
                reviews.forEachIndexed { index, review ->
                    Review(
                        author = review.author,
                        rating = if (review.authorDetails.rating != null) review.authorDetails.rating.toString() else "0.0",
                        content = review.content,
                        avatarPath = review.authorDetails.avatarPath
                    )
                }
            }

        } else if (isLoading) CircularProgressIndicator()
        else PlaceholderText(R.string.no_reviews)

        2 -> if (cast != null) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                modifier = Modifier.height(600.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(items = cast) { cast ->
                    CastMember(
                        name = cast.name,
                        character = cast.character,
                        profilePath = cast.profilePath
                    )
                }
            }
        } else if (isLoading) CircularProgressIndicator()
        else PlaceholderText(R.string.no_cast)
    }
}

//region Previews
@Preview
@Composable
fun BannerAndTitlePreview() {
    BannerAndTitle(
        backdropPath = "",
        voteAverage = 7.6,
        posterPath = "",
        title = "JUJUTSU KAISEN: Execution - Shibuya Incident",
        padding = 70
    )
}

@Preview
@Composable
fun IconTextRowPreview() {
    IconTextRow(
        releaseDate = "2025-09-08",
        runtime = "125",
        genre = "Action, Science Fiction, Horror, Crime, Mystery"
    )
}

@Preview
@Composable
fun DetailTabsPreview() {
    var tabIndex by remember { mutableIntStateOf(0) }

    val overview =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    val reviews = listOf(
        ReviewResultDomainModel(
            author = "John Doe",
            authorDetails = AuthorDomainModel(
                name = "John Doe",
                username = "johndoe",
                avatarPath = null,
                rating = 8.5
            ),
            content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        ),
        ReviewResultDomainModel(
            author = "Jane Doe",
            authorDetails = AuthorDomainModel(
                name = "Jane Doe",
                username = "janedoe",
                avatarPath = null,
                rating = null
            ),
            content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        )
    )

    val cast = listOf(
        CastDomainModel(
            name = "John Doe",
            character = "Character",
            profilePath = null
        ),
        CastDomainModel(
            name = "Jane Doe",
            character = "Character",
            profilePath = null
        )
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DetailTabs(
            selectedTabIndex = tabIndex,
            tabsList = listOf(R.string.about_movie, R.string.reviews, R.string.cast),
            onTabSelected = { tabIndex = it },
            isLoading = false,
            overview = overview,
            reviews = reviews,
            cast = cast
        )
    }
}
//endregion