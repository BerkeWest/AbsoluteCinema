package com.example.absolutecinema.presentation.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.absolutecinema.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String,
    canNavigateBack: Boolean = false,
    navigateUp: () -> Unit,
    canBookmark: Boolean = false,
    isBookmarked: Boolean,
    bookmark: () -> Unit,
    accountAccess: () -> Boolean,
    logout: () -> Unit,
    timeWindow: String? = null,
    timeWindowAccess: Boolean? = false
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        painter = painterResource(R.drawable.back),
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        actions = {
            if (canBookmark) {
                IconButton(onClick = bookmark) {
                    Icon(
                        painter = if (isBookmarked) painterResource(R.drawable.bookmark_filled)
                        else painterResource(R.drawable.bookmark),
                        contentDescription = if (isBookmarked) stringResource(R.string.remove_watchlist_button)
                        else stringResource(R.string.add_watchlist_button)
                    )
                }
            } else if (accountAccess()) {
                IconButton(onClick = logout) {
                    Icon(
                        painter = painterResource(R.drawable.logout),
                        contentDescription = stringResource(R.string.logout_button)
                    )
                }
            } else if (timeWindowAccess == true) {
                var selected by remember { mutableStateOf(0) }

                DoubleSwitch(
                    selectedIndex = selected,
                    onSelectedChange = { selected = it }
                )

            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF242A32),
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
    )
}

@Composable
fun DoubleSwitch(
    modifier: Modifier = Modifier,
    options: List<String> = listOf("Day", "Week"),
    selectedIndex: Int,
    onSelectedChange: (Int) -> Unit
) {
    val animatedOffset by animateDpAsState(
        targetValue = (selectedIndex * 50).dp, // width of each segment
        label = "switchOffset"
    )

    Box(
        modifier = modifier
            .fillMaxWidth(0.3f)
            .fillMaxHeight(0.5f)
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(50))
            .background(Color(0xFF2A2A2A))
    ) {
        Box(
            modifier = Modifier
                .offset(x = animatedOffset)
                .fillMaxWidth(0.5f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(50))
                .background(Color(0xFF2A80FF))
        )
        Row(Modifier.fillMaxSize()) {
            options.forEachIndexed { index, text ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable { onSelectedChange(index) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = text,
                        color = if (selectedIndex == index) Color.Black else Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}


//region Previews
@Preview
@Composable
fun DetailTopAppBarPreview() {
    var bookmarked by remember { mutableStateOf(false) }
    TopAppBar(
        title = stringResource(R.string.details),
        canNavigateBack = true,
        navigateUp = {},
        canBookmark = true,
        isBookmarked = bookmarked,
        bookmark = { bookmarked = !bookmarked },
        accountAccess = { false },
        logout = {}
    )
}

@Preview
@Composable
fun WatchListTopAppBarPreview() {
    TopAppBar(
        title = stringResource(R.string.watchlist),
        canNavigateBack = false,
        navigateUp = {},
        canBookmark = false,
        isBookmarked = false,
        bookmark = { },
        accountAccess = { false },
        logout = {},
        timeWindow = "Day",
        timeWindowAccess = true
    )
}

@Preview
@Composable
fun DoubleSwitchPreview() {
    DoubleSwitch(
        selectedIndex = 0,
        onSelectedChange = {}
    )
}
//endregion
