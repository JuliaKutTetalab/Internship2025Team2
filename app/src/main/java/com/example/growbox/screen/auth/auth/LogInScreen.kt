package com.example.growbox.screen.auth.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.growbox.R
import com.example.growbox.di.AppViewModelProvider
import com.example.growbox.navigation.NavigationDestination
import com.example.growbox.ui.theme.Black
import com.example.growbox.ui.theme.Gray999
import com.example.growbox.ui.theme.Green800
import com.example.growbox.ui.theme.GreenLight
import com.example.growbox.ui.theme.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

object LogInDestination : NavigationDestination {
    override val route = "login_route"
    override val titleRes = R.string.logIn_screen
    override val showBottomBar: Boolean = false
}

@Composable
fun LogInScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToSingUp: () -> Unit = {}
) {
    val viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val loginState by viewModel.loginState.collectAsState()
    val isLoading = loginState is LoginState.Loading
    val snackbarHostState = remember { SnackbarHostState() }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginState.Success -> {
                onLoginSuccess()
                viewModel.resetState()
            }
            is LoginState.Error -> {
                snackbarHostState.showSnackbar(
                    message = (loginState as LoginState.Error).message,
                    duration = SnackbarDuration.Long
                )
                viewModel.resetState()
            }
            else -> {}
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = dimensionResource(R.dimen.padding_large))
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painter = painterResource(id = R.drawable.ic_plant),
                contentDescription = stringResource(R.string.content_description_plant_icon),
                tint = Color.Unspecified,
                modifier = Modifier.size(dimensionResource(R.dimen.login_logo_size))
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium_24)))

            Text(
                text = stringResource(R.string.login_title),
                fontSize = dimensionResource(R.dimen.font_size_title).value.sp,
                fontWeight = FontWeight.Bold,
                color = Black
            )
            Text(
                text = stringResource(R.string.login_subtitle),
                fontSize = dimensionResource(R.dimen.auth_subtitle_size).value.sp,
                color = Gray999,
                modifier = Modifier.padding(top = dimensionResource(R.dimen.spacing_small))
            )

            Spacer(modifier = Modifier.weight(0.6f))

            Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium))) {
                LoginField(
                    label = stringResource(R.string.login_email_label),
                    value = email,
                    onValueChange = { email = it },
                    placeholder = stringResource(R.string.login_email_placeholder)
                )

                LoginField(
                    label = stringResource(R.string.login_password_label),
                    value = password,
                    onValueChange = { password = it },
                    placeholder = stringResource(R.string.login_password_placeholder),
                    isPassword = true,
                    isPasswordVisible = isPasswordVisible,
                    onToggleVisibility = { isPasswordVisible = !isPasswordVisible }
                )
            }

            Spacer(modifier = Modifier.weight(1.2f))

            Button(
                onClick = { viewModel.login(email, password) },
                enabled = !isLoading && email.isNotBlank() && password.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.button_height_large)),
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                ),
                contentPadding = PaddingValues(dimensionResource(R.dimen.padding_zero))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = if (isLoading || email.isBlank() || password.isBlank())
                                Brush.verticalGradient(listOf(Color(0xFFE0E0E0), Color(0xFFBDBDBD)))
                            else
                                Brush.verticalGradient(listOf(GreenLight, Green800)),
                            shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium))
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = White, modifier = Modifier.size(dimensionResource(R.dimen.icon_size_small)))
                    } else {
                        Text(
                            text = stringResource(R.string.login_button),
                            fontSize = dimensionResource(R.dimen.auth_subtitle_size).value.sp,
                            fontWeight = FontWeight.Bold,
                            color = White
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.padding(
                    top = dimensionResource(R.dimen.spacing_medium_24),
                    bottom = dimensionResource(R.dimen.spacing_medium_24)
                ),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.login_no_account) + " ",
                    fontSize = dimensionResource(R.dimen.auth_link_size).value.sp,
                    color = Black
                )
                Text(
                    text = stringResource(R.string.login_sign_up_link),
                    fontSize = dimensionResource(R.dimen.auth_link_size).value.sp,
                    fontWeight = FontWeight.Bold,
                    color = Green800,
                    modifier = Modifier.clickable { onNavigateToSingUp() }
                )
            }

            Spacer(modifier = Modifier.weight(0.2f))
        }
    }
}

@Composable
fun LoginField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false,
    isPasswordVisible: Boolean = false,
    onToggleVisibility: (() -> Unit)? = null
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = Color(0xFFBCBCBC),
            fontSize = dimensionResource(R.dimen.auth_label_size).value.sp,
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_extra_small))
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = if (isPassword && !isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            textStyle = TextStyle(color = Black, fontSize = dimensionResource(R.dimen.auth_subtitle_size).value.sp),
            placeholder = { Text(placeholder, color = Color(0xFFD1D1D1)) },
            trailingIcon = {
                if (isPassword) {
                    IconButton(onClick = onToggleVisibility!!) {
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null,
                            tint = Green800
                        )
                    }
                }
            },
            shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium)),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF9F9F9),
                unfocusedContainerColor = Color(0xFFF9F9F9),
                focusedBorderColor = Green800,
                unfocusedBorderColor = Color(0xFFE8E8E8)
            )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LogInScreen(onLoginSuccess = {}, onNavigateToSingUp = {})
}
