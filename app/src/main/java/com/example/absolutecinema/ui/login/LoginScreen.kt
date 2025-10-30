package com.example.absolutecinema.ui.login

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.absolutecinema.R
import com.example.absolutecinema.ui.AppViewModelProvider
import com.example.absolutecinema.ui.navigation.NavigationDestination


object LoginPage : NavigationDestination {
    override val route = "login"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navigateToHome: () -> Unit,
    authViewModel: AuthViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val loginState by authViewModel.loginState.collectAsState()
    var isError by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    //Launched Effect, loginState'deki değişimde aktifleşir ve recompositionda tekrarlamaz.
    when (loginState) {
        true -> {
            // Giriş başarılı veya zaten oturum açıktı, ana sayfaya yönlendir.
            navigateToHome()
            authViewModel.onLoginStateConsumed()
        }

        false -> {
            // Giriş başarısız oldu, hata göster.
            isError = true
            password = ""
            authViewModel.onLoginStateConsumed()
        }

        null -> {
            // Bu başlangıç durumudur veya state tüketildikten sonraki halidir, hiçbir şey yapma.
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
                    value = username,
                    onValueChange = {
                        username = it
                        if (isError) isError = false
                    },
                    singleLine = true,
                    shape = shapes.large,
                    isError = isError,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Username") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    )
                )
                OutlinedTextField(
                    value = password,
                    singleLine = true,
                    shape = shapes.large,
                    isError = isError,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        password = it
                        if (isError) isError = false
                    },
                    label = { Text("Password") },
                    //visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            authViewModel.login(username, password)
                        }
                    )

                )
                OutlinedButton(
                    modifier = Modifier.width(150.dp),
                    onClick = {
                        focusManager.clearFocus()
                        authViewModel.login(username, password)
                    }
                ) {
                    Text("Login")
                }
            }
        }
    }
}
