package com.example.absolutecinema.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.absolutecinema.R
import com.example.absolutecinema.domain.model.authentication.LoginResult
import com.example.absolutecinema.presentation.navigation.NavigationDestination


object LoginPage : NavigationDestination {
    override val route = "login"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navigateToHome: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel()
) {

    val uiState by authViewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    //Launched Effect, loginState'deki değişimde aktifleşir ve recompositionda tekrarlamaz.
    LaunchedEffect(uiState.loginState) {
        when (val state = uiState.loginState) {
            is LoginResult.Idle -> {
            }

            is LoginResult.Loading -> {
            }

            is LoginResult.Success -> {
                if (state.success) {
                    navigateToHome()
                    authViewModel.onLoginStateConsumed()
                } else {
                    uiState.isError = true
                    uiState.password = ""
                    authViewModel.onLoginStateConsumed()
                }
            }

            is LoginResult.Error -> {
                uiState.isError = true
                uiState.password = ""
                authViewModel.onLoginStateConsumed()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF242A32))
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center,

    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 32.dp)
                    .verticalScroll(
                        rememberScrollState()
                    )
            ) {
                Text(
                    "Welcome to",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
                Image(
                    painter = painterResource(R.drawable.absolute_cinema),
                    contentDescription = "ABSOLUTE CINEMA",
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                )

                OutlinedTextField(
                    value = uiState.username,
                    onValueChange = { newUsername ->
                        authViewModel.onUsernameChange(newUsername)
                    },
                    singleLine = true,
                    shape = shapes.large,
                    isError = uiState.isError,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Username") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    )
                )
                OutlinedTextField(
                    value = uiState.password,
                    singleLine = true,
                    shape = shapes.large,
                    isError = uiState.isError,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { newPassword ->
                        authViewModel.onPasswordChange(newPassword)
                    },
                    label = { Text("Password") },
                    trailingIcon = {
                        IconButton(onClick = { authViewModel.togglePasswordVisibility() }) {
                            Icon(
                                painterResource(uiState.passwordState.icon),
                                contentDescription = uiState.passwordState.description
                            )
                        }
                    },
                    visualTransformation = uiState.passwordState.visualTransformation,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            authViewModel.login(uiState.username, uiState.password)
                        }
                    )

                )
                OutlinedButton(
                    modifier = Modifier.width(150.dp),
                    onClick = {
                        focusManager.clearFocus()
                        authViewModel.login(uiState.username, uiState.password)
                    }
                ) {
                    Text("Login")
                }
            }
        }
    }
}
