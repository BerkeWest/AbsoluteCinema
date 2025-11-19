package com.example.absolutecinema.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.absolutecinema.BuildConfig
import com.example.absolutecinema.R

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

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(uiState.topMovies) { movie ->
                    Box(
                        modifier = Modifier
                            .width(180.dp)
                            .height(260.dp)
                            .clickable { movie.id?.let { onNavigateToDetails(it) } }
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(context = LocalContext.current)
                                .data(BuildConfig.IMAGE_URL + movie.posterPath)
                                .crossfade(true)
                                .build(),
                            error = painterResource(R.drawable.ic_broken_image),
                            placeholder = painterResource(R.drawable.loading_img),
                            contentDescription = movie.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .width(160.dp)
                                .height(230.dp)
                                .clip(RoundedCornerShape(12.dp)),
                        )

                        // Rank Number
                        Text(
                            text = (uiState.topMovies.indexOf(movie) + 1).toString(),
                            fontSize = 72.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFACDFFA),
                            style = TextStyle(
                                shadow = Shadow(
                                    color = Color(0xFF0296E5), // Outline color (cyan)
                                    offset = Offset(0f, 0f),
                                    blurRadius = 20f // controls how thick the outline looks
                                )
                            ),
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            //Tab Row
            SecondaryTabRow(
                selectedTabIndex = uiState.selectedTabIndex,
                containerColor = Color.Transparent,
                contentColor = Color.White,
                divider = {}
            ) {
                homeViewModel.tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = uiState.selectedTabIndex == index,
                        onClick = {
                            homeViewModel.onTabSelected(index)
                        },
                        text = {
                            Text(
                                text = stringResource(title),
                                color = if (uiState.selectedTabIndex == index) Color.White else Color.Gray,
                                fontWeight = if (uiState.selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                                fontSize = (11.8).sp
                            )
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            //Movie posters grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.height(600.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                val currentMovieList = when (uiState.selectedTabIndex) {
                    0 -> uiState.nowPlaying
                    1 -> uiState.upcoming
                    2 -> uiState.topRated
                    3 -> uiState.popular
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
                        contentScale = ContentScale.Crop,
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
