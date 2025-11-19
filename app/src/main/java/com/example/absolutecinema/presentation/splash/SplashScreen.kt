package com.example.absolutecinema.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.absolutecinema.R
import com.example.absolutecinema.presentation.navigation.NavigationDestination

object SplashPage : NavigationDestination {
    override val route = "splash"
}

@Composable
fun SplashScreen(
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit,
    splashScreenViewModel: SplashScreenViewModel = hiltViewModel()
) {
    val state by splashScreenViewModel.uiState.collectAsState()

    LaunchedEffect(state.hasAccess) {
        if (state.hasAccess == true) {
            navigateToHome()
        } else if (state.hasAccess == false) {
            navigateToLogin()
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF242A32))
    ) {
        Image(
            painter = painterResource(R.drawable.empty_watchlist),
            contentDescription = stringResource(R.string.app_name),
            modifier = Modifier.fillMaxWidth(),
        )
    }

}