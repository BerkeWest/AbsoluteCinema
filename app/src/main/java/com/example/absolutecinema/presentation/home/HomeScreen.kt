@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.absolutecinema.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.absolutecinema.BuildConfig
import com.example.absolutecinema.R
import com.example.absolutecinema.domain.model.response.MovieSearchResultDomainModel
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeScreenViewModel = hiltViewModel(),
    onNavigateToDetails: (movieId: Int) -> Unit
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF242A32))
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 12.dp)
        ) {

            Spacer(modifier = Modifier.height(12.dp))

            TopMoviesPager(uiState.topMovies, onNavigateToDetails)

            Spacer(modifier = Modifier.height(16.dp))

            HomeTabsPager(
                tabs = homeViewModel.tabs,
                selectedTabIndex = uiState.selectedTabIndex,
                onTabSelected = { index -> homeViewModel.onTabSelected(index) },
                tab1 = uiState.nowPlaying,
                tab2 = uiState.upcoming,
                tab3 = uiState.topRated,
                tab4 = uiState.popular,
                onNavigateToDetails = onNavigateToDetails,
                isLoading = uiState.isLoading
            )
        }
    }
}

@Composable
fun TopMoviesPager(
    itemList: List<MovieSearchResultDomainModel>,
    onNavigateToDetails: (Int) -> Unit
) {
    if (itemList.isEmpty()) return

    val initialPage = remember(itemList.size) {
        val middle = Int.MAX_VALUE / 2
        middle - (middle % itemList.size)
    }

    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { Int.MAX_VALUE }
    )

    val pagerIsDragged by pagerState.interactionSource.collectIsDraggedAsState()

    val pageInteractionSource = remember { MutableInteractionSource() }
    val pageIsPressed by pageInteractionSource.collectIsPressedAsState()

    val autoAdvance = !pagerIsDragged && !pageIsPressed

    if (autoAdvance) {
        LaunchedEffect(pagerState, pageInteractionSource) {
            while (true) {
                delay(5000)
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
        }
    }

    HorizontalPager(
        state = pagerState,
        pageSize = PageSize.Fixed(220.dp),
        contentPadding = PaddingValues(horizontal = 20.dp),
        pageSpacing = 6.dp
    ) { page ->
        val actualIndex = page % itemList.size
        val movie = itemList[actualIndex]

        val pageOffset = (
                (pagerState.currentPage - page) + pagerState
                    .currentPageOffsetFraction
                ).absoluteValue

        val scale = lerp(
            start = 0.85f,
            stop = 1f,
            fraction = 1f - pageOffset.coerceIn(0f, 1f)
        )

        Box(
            modifier = Modifier
                .width(180.dp)
                .height(260.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale

                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
                .clickable { movie.id?.let(onNavigateToDetails) }
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(BuildConfig.IMAGE_URL + movie.posterPath)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize(0.9f)
                    .align(Alignment.TopEnd)
                    .clip(RoundedCornerShape(12.dp))
            )

            Text(
                text = (actualIndex + 1).toString(),
                fontSize = (60 * scale).sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFACDFFA),
                style = TextStyle(
                    shadow = Shadow(
                        color = Color(0xFF0296E5), // Outline color (cyan)
                        offset = Offset(0f, 0f),
                        blurRadius = 20f // controls how thick the outline looks
                    )
                ),
                modifier = Modifier.align(Alignment.BottomStart)
            )
        }
    }
}

@Composable
private fun HomeTabsPager(
    tabs: List<Int> = emptyList(),
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    tab1: List<MovieSearchResultDomainModel> = emptyList(),
    tab2: List<MovieSearchResultDomainModel> = emptyList(),
    tab3: List<MovieSearchResultDomainModel> = emptyList(),
    tab4: List<MovieSearchResultDomainModel> = emptyList(),
    onNavigateToDetails: (Int) -> Unit,
    isLoading: Boolean
) {
    val pagerState = rememberPagerState(
        initialPage = selectedTabIndex,
        pageCount = { tabs.size }
    )

    LaunchedEffect(selectedTabIndex) {
        if (pagerState.currentPage != selectedTabIndex) {
            if ((selectedTabIndex - pagerState.currentPage).absoluteValue > 1) pagerState.scrollToPage(
                selectedTabIndex
            )
            else pagerState.animateScrollToPage(selectedTabIndex)
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage != selectedTabIndex) {
            onTabSelected(pagerState.currentPage)
        }
    }

    SecondaryTabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = Color.Transparent,
        contentColor = Color.White,
        divider = {}
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = {
                    onTabSelected(index)
                },
                text = {
                    Text(
                        text = stringResource(title),
                        color = if (selectedTabIndex == index) Color.White else Color.Gray,
                        fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                        fontSize = (11.8).sp
                    )
                }
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))

    HorizontalPager(
        state = pagerState,
        pageSize = PageSize.Fill
    ) { page ->
        if (isLoading) CircularProgressIndicator(modifier = Modifier)
        else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(100.dp),
                modifier = Modifier.height(600.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                val currentMovieList = when (page) {
                    0 -> tab1
                    1 -> tab2
                    2 -> tab3
                    3 -> tab4
                    else -> emptyList()
                }

                items(currentMovieList) { movie ->
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(BuildConfig.IMAGE_URL + movie.posterPath)
                            .crossfade(true)
                            .build(),
                        error = painterResource(R.drawable.ic_broken_image),
                        placeholder = painterResource(R.drawable.loading_img),
                        contentDescription = movie.title,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                movie.id?.let { onNavigateToDetails(it) }
                            }
                    )
                }
            }
        }
    }
}

//region Previews
val previewItemList = listOf(
    MovieSearchResultDomainModel(
        genreIds = listOf(1, 2, 3),
        id = 2,
        originalTitle = "Demon Slayer",
        overview = "Overview",
        popularity = 1.0,
        posterPath = "posterPath",
        releaseDate = "2",
        title = "Demon Slayer",
        voteAverage = 4.8,
        genre = "Anime, Action, Animation, Drama, Fantasy"
    ),
    MovieSearchResultDomainModel(
        genreIds = listOf(1, 2, 3),
        id = 1,
        originalTitle = "Demon Slayer",
        overview = "Overview",
        popularity = 1.0,
        posterPath = "posterPath",
        releaseDate = "2",
        title = "Demon Slayer",
        voteAverage = 4.8,
        genre = "Anime, Action, Animation, Drama, Fantasy"
    ),
    MovieSearchResultDomainModel(
        genreIds = listOf(1, 2, 3),
        id = 3,
        originalTitle = "Demon Slayer",
        overview = "Overview",
        popularity = 1.0,
        posterPath = "posterPath",
        releaseDate = "2",
        title = "Demon Slayer",
        voteAverage = 4.8,
        genre = "Anime, Action, Animation, Drama, Fantasy"
    ),
    MovieSearchResultDomainModel(
        genreIds = listOf(1, 2, 3),
        id = 4,
        originalTitle = "Demon Slayer",
        overview = "Overview",
        popularity = 1.0,
        posterPath = "posterPath",
        releaseDate = "2",
        title = "Demon Slayer",
        voteAverage = 4.8,
        genre = "Anime, Action, Animation, Drama, Fantasy"
    ),
    MovieSearchResultDomainModel(
        genreIds = listOf(1, 2, 3),
        id = 5,
        originalTitle = "Demon Slayer",
        overview = "Overview",
        popularity = 1.0,
        posterPath = "posterPath",
        releaseDate = "2",
        title = "Demon Slayer",
        voteAverage = 4.8,
        genre = "Anime, Action, Animation, Drama, Fantasy"
    ),
    MovieSearchResultDomainModel(
        genreIds = listOf(1, 2, 3),
        id = 6,
        originalTitle = "Demon Slayer",
        overview = "Overview",
        popularity = 1.0,
        posterPath = "posterPath",
        releaseDate = "2",
        title = "Demon Slayer",
        voteAverage = 4.8,
        genre = "Anime, Action, Animation, Drama, Fantasy"
    ),
    MovieSearchResultDomainModel(
        genreIds = listOf(1, 2, 3),
        id = 7,
        originalTitle = "Demon Slayer",
        overview = "Overview",
        popularity = 1.0,
        posterPath = "posterPath",
        releaseDate = "2",
        title = "Demon Slayer",
        voteAverage = 4.8,
        genre = "Anime, Action, Animation, Drama, Fantasy"
    ),
    MovieSearchResultDomainModel(
        genreIds = listOf(1, 2, 3),
        id = 8,
        originalTitle = "Demon Slayer",
        overview = "Overview",
        popularity = 1.0,
        posterPath = "posterPath",
        releaseDate = "2",
        title = "Demon Slayer",
        voteAverage = 4.8,
        genre = "Anime, Action, Animation, Drama, Fantasy"
    ),
    MovieSearchResultDomainModel(
        genreIds = listOf(1, 2, 3),
        id = 9,
        originalTitle = "Demon Slayer",
        overview = "Overview",
        popularity = 1.0,
        posterPath = "posterPath",
        releaseDate = "2",
        title = "Demon Slayer",
        voteAverage = 4.8,
        genre = "Anime, Action, Animation, Drama, Fantasy"
    ), MovieSearchResultDomainModel(
        genreIds = listOf(1, 2, 3),
        id = 0,
        originalTitle = "Demon Slayer",
        overview = "Overview",
        popularity = 1.0,
        posterPath = "posterPath",
        releaseDate = "2",
        title = "Demon Slayer",
        voteAverage = 4.8,
        genre = "Anime, Action, Animation, Drama, Fantasy"
    )
)

@Preview
@Composable
fun TopMoviesPagerPreview() {
    TopMoviesPager(
        itemList = previewItemList,
        onNavigateToDetails = {}
    )
}

@Preview
@Composable
fun HomeTabsPagerPreview() {
    var tab by remember { mutableIntStateOf(0) }
    Column {
        HomeTabsPager(
            tabs = listOf(
                R.string.now_playing,
                R.string.upcoming,
                R.string.top_rated,
                R.string.popular
            ),
            selectedTabIndex = tab,
            onTabSelected = { tab = it },
            tab1 = previewItemList,
            tab2 = previewItemList,
            tab3 = emptyList(),
            tab4 = previewItemList,
            onNavigateToDetails = {},
            isLoading = false
        )
    }
}
//endregion
