package com.example.absolutecinema.ui

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.absolutecinema.R
import com.example.absolutecinema.ui.navigation.CinemaNavHost

@Composable
fun CinemaApp(
    navController: NavHostController = rememberNavController()
) {
    CinemaNavHost(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AbsoluteCinemaTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        painter = painterResource(R.drawable.back),
                        contentDescription = "Back"
                    )
                }
            }
        })
}