package com.example.absolutecinema.ui.topbar

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.absolutecinema.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    canBookmark: Boolean,
    isBookmarked: Boolean,
    bookmark: () -> Unit,
    modifier: Modifier = Modifier
){
    CenterAlignedTopAppBar(title = { Text(title) },
        modifier = modifier,
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        painter = painterResource(R.drawable.back),
                        contentDescription = "Back Button"
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
                        contentDescription = "WatchListIndicator"
                    )
                }
            }
        }

    )
}

