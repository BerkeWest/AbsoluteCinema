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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.absolutecinema.BuildConfig
import com.example.absolutecinema.R
import com.example.absolutecinema.presentation.navigation.NavigationDestination
import com.example.absolutecinema.presentation.utils.ShowComposeToast
import com.example.absolutecinema.presentation.utils.TopAppBar
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

            //region Banner
            item {
                Box {
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(BuildConfig.IMAGE_URL + uiState.movieDetails?.backdropPath)
                            .crossfade(true)
                            .build(),
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
                            .clickable {  }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.rating),
                            contentDescription = stringResource(R.string.rating),
                            tint = Color(0xFFFFA500)
                        )
                        Spacer(Modifier.width(5.dp))
                        Text(
                            text = String.format(
                                Locale.US, "%.1f",
                                uiState.movieDetails?.voteAverage
                            ),
                            color = Color(0xFFFFA500),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Row(
                        modifier = Modifier.align(Alignment.BottomStart).offset(
                            x = 16.dp,
                            y = 60.dp
                        ) //Aşağı iterek üst üste durma efektini verir.,
                    ) {
                        //Küçük Poster
                        AsyncImage(
                            model = ImageRequest.Builder(context = LocalContext.current)
                                .data(BuildConfig.IMAGE_URL + uiState.movieDetails?.posterPath)
                                .crossfade(true)
                                .build(),
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
                            text = uiState.movieDetails?.title ?: "",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.offset(y = 120.dp).padding(end = 20.dp)
                        )
                    }
                }
            }
            //endregion
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 70.dp)//küçük poster için alan oluştur
                ) {
                    Spacer(Modifier.height(20.dp))

                    //İconlu sıra
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(space = 7.dp, alignment = Alignment.CenterHorizontally)
                    ) {
                        IconText(
                            painterResource(R.drawable.calendar),
                            uiState.movieDetails?.releaseDate?.take(4) ?: ""
                        )
                        Spacer(
                            modifier = Modifier
                                .width(1.dp)
                                .height(16.dp)
                                .background(Color.Gray.copy(alpha = 0.5f))
                        )
                        IconText(
                            painterResource(R.drawable.time),
                            "${uiState.movieDetails?.runtime} Minutes"
                        )
                        Spacer(
                            modifier = Modifier
                                .width(1.dp)
                                .height(16.dp)
                                .background(Color.Gray.copy(alpha = 0.5f))
                        )
                        IconText(
                            painterResource(R.drawable.ticket),
                            detailViewModel.getGenreString(uiState.movieDetails?.genres?.map { it.name }
                                ?: listOf())
                        )
                    }

                    Spacer(Modifier.height(24.dp))
                }
            }

            //Tablar
            item {
                var selectedTab by remember { mutableIntStateOf(0) }
                val tabs = listOf("About Movie", "Reviews", "Cast")

                SecondaryTabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.Transparent,
                    contentColor = Color.White,
                    divider = {}
                ) {
                    tabs.forEachIndexed { index, text ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    text = text,
                                    color = if (selectedTab == index) Color.White else Color.Gray,
                                    fontWeight = if (selectedTab == index)
                                        FontWeight.Bold else FontWeight.Normal
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
        Text(text, color = Color.Gray, fontSize = 11.sp)
    }
}
