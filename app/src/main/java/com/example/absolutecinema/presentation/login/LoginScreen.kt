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
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.absolutecinema.R
import com.example.absolutecinema.domain.model.authentication.LoginResult
import com.example.absolutecinema.presentation.login.components.PassWordVisibility
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
                    stringResource(R.string.login_welcome),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
                Image(
                    painter = painterResource(R.drawable.absolute_cinema),
                    contentDescription = stringResource(R.string.app_name),
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                )
                LoginInputField(
                    value = uiState.username,
                    isError = uiState.isError,
                    onValueChange = { newUsername ->
                        authViewModel.onUsernameChange(newUsername)
                    },
                    label = R.string.username,
                    trailingIcon = null,
                    imeAction = ImeAction.Next,
                )
                LoginInputField(
                    value = uiState.password,
                    isError = uiState.isError,
                    onValueChange = { newPassword ->
                        authViewModel.onPasswordChange(newPassword)
                    },
                    label = R.string.password,
                    trailingIcon = {
                        IconButton(onClick = { authViewModel.togglePasswordVisibility() }) {
                            Icon(
                                painterResource(uiState.passwordState.icon),
                                contentDescription = stringResource(uiState.passwordState.description),
                                tint = Color.Black
                            )
                        }
                    },
                    visualTransformation = uiState.passwordState.visualTransformation,
                    imeAction = ImeAction.Done,
                    onKeyboardDone = {
                        focusManager.clearFocus()
                        authViewModel.login(uiState.username, uiState.password)
                    }
                )
                LoginButton(
                    {
                        focusManager.clearFocus()
                        authViewModel.login(uiState.username, uiState.password)
                    }
                )
            }
        }
    }
}

@Composable
private fun LoginInputField(
    value: String,
    isError: Boolean,
    onValueChange: (String) -> Unit,
    label: Int,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    imeAction: ImeAction = ImeAction.Next,
    onKeyboardDone: () -> Unit = {}

) {
    OutlinedTextField(
        value = value,
        singleLine = true,
        shape = shapes.large,
        isError = isError,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = onValueChange,
        label = { Text(stringResource(label), color = Color.Black) },
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = { onKeyboardDone() }
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.DarkGray,
            unfocusedBorderColor = Color.DarkGray,
            focusedLabelColor = Color.Gray,
            unfocusedLabelColor = Color.Gray,
            focusedTextColor = Color.DarkGray,
            unfocusedTextColor = Color.DarkGray,
            cursorColor = Color.DarkGray,
            errorLabelColor = Color.Red,
            errorBorderColor = Color.Red
        )
    )
}

@Composable
private fun LoginButton(
    onClick: () -> Unit,
) {
    OutlinedButton(
        modifier = Modifier.width(150.dp),
        onClick = onClick,
    ) {
        Text(stringResource(R.string.login_button), color = Color.Black)
    }
}

@Preview(showBackground = true)
@Composable
fun LoginInputFieldPreview() {
    var input by remember { mutableStateOf("") }
    var passwordState by remember { mutableStateOf(PassWordVisibility.INVISIBLE) }
    LoginInputField(
        value = input,
        isError = false,
        onValueChange = { input = it },
        label = R.string.password,
        trailingIcon = {
            IconButton(onClick = {
                passwordState =
                    if (passwordState == PassWordVisibility.INVISIBLE) PassWordVisibility.VISIBLE
                    else PassWordVisibility.INVISIBLE
            }
            ) {
                Icon(
                    painterResource(passwordState.state.icon),
                    contentDescription = stringResource(passwordState.state.description),
                    tint = Color.Black
                )
            }
        },
        visualTransformation = passwordState.state.visualTransformation,
        imeAction = ImeAction.Next,
        onKeyboardDone = {}
    )
}

@Preview(showBackground = true)
@Composable
fun LoginButtonPreview() {
    LoginButton(onClick = {})
}
