package com.example.absolutecinema.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.absolutecinema.BuildConfig
import com.example.absolutecinema.R
import com.example.absolutecinema.data.movie.getGenreString
import com.example.absolutecinema.ui.AppViewModelProvider
import com.example.absolutecinema.ui.navigation.NavigationDestination
import com.example.absolutecinema.ui.topbar.TopAppBar
import java.util.Locale

object DetailPage : NavigationDestination {
    override val route = "detail"
}

@Composable
fun DetailScreen(
    id: Int?,
    navigateBack: () -> Unit,
    detailViewModel: DetailViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {

    val uiState by detailViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = "Detail",
                canNavigateBack = true,
                navigateUp = navigateBack,
                canBookmark = true,
                isBookmarked = uiState.movieState?.watchlist ?: false,
                bookmark = { detailViewModel.bookmark() },
            )
        }
    )
    { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFF242A32)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //Banner
            item {
                Box {
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(BuildConfig.IMAGE_URL + uiState.movieDetails?.poster_path)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )

                    //Küçük Poster
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(BuildConfig.IMAGE_URL + uiState.movieDetails?.poster_path)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Poster",
                        modifier = Modifier
                            .size(120.dp)
                            .align(Alignment.BottomStart)
                            .offset(x = 16.dp, y = 60.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(width = 1.dp, Color.Gray, RoundedCornerShape(12.dp))
                    )
                }
            }

            //Title ve Rating
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 70.dp)
                ) {
                    Text(
                        text = uiState.movieDetails?.title ?: "",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.rating),
                            contentDescription = null,
                            tint = Color(0xFFFFA500)
                        )
                        Text(
                            text = String.format(
                                Locale.US, "%.1f",
                                uiState.movieDetails?.vote_average
                            ),
                            color = Color(0xFFFFA500),
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    //İconlu sıra
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        IconText(
                            painterResource(R.drawable.calendar),
                            uiState.movieDetails?.release_date?.take(4) ?: "1000"
                        )
                        IconText(
                            painterResource(R.drawable.time),
                            "${uiState.movieDetails?.runtime} Minutes"
                        )
                        IconText(
                            painterResource(R.drawable.ticket),
                            getGenreString(uiState.movieDetails?.genres?.map { it.name }
                                ?: listOf()))
                    }

                    Spacer(Modifier.height(24.dp))
                }
            }

            //Tablar
            item {
                var selectedTab by remember { mutableIntStateOf(0) }
                val tabs = listOf("About Movie", "Reviews", "Cast")

                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                ) {
                    tabs.forEachIndexed { index, text ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    text,
                                    color = if (selectedTab == index) Color.White else Color.Gray
                                )
                            }
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                when (selectedTab) {
                    0 -> Text(
                        text = uiState.movieDetails?.overview ?: "",
                        color = Color.White,
                        fontSize = 15.sp,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(16.dp)
                    )

                    1 -> Text(
                        "Reviews section placeholder",
                        color = Color.Gray,
                        modifier = Modifier.padding(16.dp)
                    )

                    2 -> Text(
                        "Cast section placeholder",
                        color = Color.Gray,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }

}

@Composable
fun IconText(icon: Painter, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = icon,
            contentDescription = "IconText",
            tint = Color.Gray,
            modifier = Modifier.size(16.dp)
        )
        Spacer(Modifier.width(4.dp))
        Text(text, color = Color.Gray, fontSize = 13.sp)
    }
}
