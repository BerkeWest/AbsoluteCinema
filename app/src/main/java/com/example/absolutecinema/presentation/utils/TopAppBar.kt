package com.example.absolutecinema.presentation.utils

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
    logout: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
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
            } else if (accountAccess()) {
                IconButton(onClick = logout) {
                    Icon(
                        painter = painterResource(R.drawable.logout),
                        contentDescription = "Account"
                    )
                }
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

