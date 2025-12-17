package com.example.growbox.screen.auth.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory

import com.example.growbox.R
import com.example.growbox.di.AppViewModelProvider
import com.example.growbox.navigation.NavigationDestination
import com.example.growbox.ui.theme.Black
import com.example.growbox.ui.theme.Gray999
import com.example.growbox.ui.theme.Green800
import com.example.growbox.ui.theme.GreenLight
import com.example.growbox.ui.theme.White
import androidx.compose.ui.res.stringResource


object LogInDestination: NavigationDestination {
    override val route = "login_route"
    override val titleRes = R.string.logIn_screen
}

@Composable
fun LogInScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToSingUp: () -> Unit = {}
) {

    val viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val loginState by viewModel.loginState.collectAsState()
    val isLoading = loginState is LoginState.Loading

    // Стан UI
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }



    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginState.Success -> {
                onLoginSuccess()
                viewModel.resetState()
            }
            is LoginState.Error -> {
                // Змінено кастинг на LoginState.Error
                val message = (loginState as LoginState.Error).message
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Long
                )
                viewModel.resetState()
            }
            else -> {}
        }
    }


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = dimensionResource(R.dimen.padding_large)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_giant)))


            Icon(
                painter = painterResource(id = R.drawable.ic_plant),
                contentDescription = stringResource(R.string.content_description_plant_icon),
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.icon_size_huge))
            )
            Spacer( modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))


            Text(
                text = stringResource(R.string.login_title),
                fontSize = dimensionResource(R.dimen.font_size_title).value.sp,
                fontWeight = FontWeight.Bold,
                color = Black
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_small)))
            Text(
                text = stringResource(R.string.login_subtitle),
                fontSize = dimensionResource(R.dimen.font_size_large).value.sp,
                color = Gray999
            )
            Spacer( modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))


            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.login_email_label),
                    fontSize = dimensionResource(R.dimen.font_size_small).value.sp,
                    color = Gray999,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.spacing_small))
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text(text = stringResource(R.string.login_email_placeholder), color = Black) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium)),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Green800,
                        unfocusedBorderColor = White
                    ),
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.login_password_label),
                    fontSize = dimensionResource(R.dimen.font_size_small).value.sp,
                    color = Gray999,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.spacing_small))
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text(stringResource(R.string.login_password_placeholder), color = Black) },
                    singleLine = true,
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(
                                imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                                tint = Green800
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium)),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Green800, unfocusedBorderColor = White)
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))


            Button(
                onClick = {
                    if (!isLoading) viewModel.login(email, password)
                },
                enabled = !isLoading && email.isNotBlank() && password.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.LightGray.copy(alpha = 0.5f)
                ),
                contentPadding = PaddingValues(dimensionResource(R.dimen.padding_zero)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.button_height_large))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = if (isLoading) {
                                Brush.verticalGradient(listOf(Color.Gray, Color.DarkGray))
                            } else {
                                Brush.verticalGradient(colors = listOf(GreenLight, Green800))
                            },
                            shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium))
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = White, modifier = Modifier.size(30.dp))
                    } else {
                        Text(
                            text = stringResource(R.string.login_button),
                            fontSize = dimensionResource(R.dimen.font_size_large).value.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))


            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(R.dimen.padding_extra_large))
            ) {
                Text(
                    text = stringResource(R.string.login_no_account),
                    fontSize = dimensionResource(R.dimen.font_size_large).value.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Black
                )
                Text(
                    text = stringResource(R.string.login_sign_up_link),
                    fontSize = dimensionResource(R.dimen.font_size_large).value.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Green800,
                    modifier = Modifier.clickable { onNavigateToSingUp() }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LogInScreen(onLoginSuccess = {}, onNavigateToSingUp = {})
}