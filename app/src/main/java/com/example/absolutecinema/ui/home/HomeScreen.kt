package com.example.absolutecinema.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.absolutecinema.BuildConfig
import com.example.absolutecinema.R
import com.example.absolutecinema.ui.AppViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeScreenViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onNavigateToDetails: (movieId: Int) -> Unit
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    val tabs = listOf("Now playing", "Upcoming", "Top rated", "Popular")
    var selectedTab by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF242A32))
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 12.dp)
    ) {

        Spacer(modifier = Modifier.height(12.dp))

        // Top movies carousel
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(uiState.topMovies) { movie ->
                Box(
                    modifier = Modifier
                        .width(180.dp)
                        .height(260.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onNavigateToDetails(movie.id) }
                ) {
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
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp)),
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .background(Color(0x80000000))
                            .padding(6.dp)
                    ) {
                        Text(
                            text = movie.title,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    // Rank Number
                    Text(
                        text = (uiState.topMovies.indexOf(movie) + 1).toString(),
                        fontSize = 60.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2A80FF),
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .offset(x = (-8).dp, y = 8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        //Tab row
        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            edgePadding = 0.dp,
            containerColor = Color.Transparent,
            contentColor = Color.White,
            divider = {}
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = {
                        selectedTab = index
                        homeViewModel.loadMoviesForTab(index)
                    },
                    text = {
                        Text(
                            text = title,
                            color = if (selectedTab == index)
                                Color.White else Color.Gray,
                            fontWeight = if (selectedTab == index)
                                FontWeight.Bold else FontWeight.Normal
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
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(uiState.tabResult) { movie ->
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
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                        .height(180.dp)
                        .clickable { onNavigateToDetails(movie.id) }
                )
            }
        }
    }
}
