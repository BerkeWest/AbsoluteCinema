package com.example.absolutecinema.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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

    //Launched Effect, loginState'deki değişimde aktifleşir ve recompositionda tekrarlamaz.
    LaunchedEffect(loginState) {
        if (loginState == true) {
            navigateToHome()
        } else if (loginState == false) {
            isError = true
            username = ""
            password = ""
        }
    }
    Card(
        modifier = Modifier
            .padding(start = 40.dp, end = 40.dp, top = 300.dp, bottom = 100.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 80.dp)
        ) {

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
                modifier = Modifier.width(350.dp),
                onValueChange = {
                    password = it
                    if (isError) isError = false
                },
                label = { Text("Password") },
                //visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                )
            )
            OutlinedButton(
                onClick = {
                    authViewModel.login(username, password)
                }
            ) {
                Text("Login")
            }
        }
    }
}
