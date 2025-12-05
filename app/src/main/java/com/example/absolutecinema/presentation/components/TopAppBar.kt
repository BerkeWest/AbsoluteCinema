package com.example.absolutecinema.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
    accountAccess: Boolean,
    logout: () -> Unit,
    share: () -> Unit = {},
    timeWindowDay: Boolean = true,
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
                Row {
                    IconButton(onClick = bookmark) {
                        Icon(
                            painter = if (isBookmarked) painterResource(R.drawable.bookmark_filled)
                            else painterResource(R.drawable.bookmark),
                            contentDescription = if (isBookmarked) stringResource(R.string.remove_watchlist_button)
                            else stringResource(R.string.add_watchlist_button)
                        )
                    }
                    IconButton(onClick = share) {
                        Icon(
                            painter = painterResource(R.drawable.share),
                            contentDescription = stringResource(R.string.share_button)
                        )
                    }
                }
            } else if (accountAccess) {
                IconButton(onClick = logout) {
                    Icon(
                        painter = painterResource(R.drawable.logout),
                        contentDescription = stringResource(R.string.logout_button)
                    )
                }
            } else if (timeWindowAccess == true) {
                var selected by remember { mutableIntStateOf(0) }
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
        accountAccess = false,
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
        accountAccess = false,
        logout = {},
        timeWindowDay = true,
        timeWindowAccess = true
    )
}
//endregion
