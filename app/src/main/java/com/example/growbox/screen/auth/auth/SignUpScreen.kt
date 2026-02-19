package com.example.growbox.screen.auth.auth

import androidx.compose.runtime.Composable
import com.example.growbox.navigation.NavigationDestination
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.growbox.R
import com.example.growbox.ui.theme.Black
import com.example.growbox.ui.theme.Gray999
import com.example.growbox.ui.theme.Green800
import com.example.growbox.ui.theme.GreenLight
import com.example.growbox.ui.theme.White
import com.example.growbox.utils.isPasswordValid
import com.example.growbox.di.AppViewModelProvider

object SignUpDestination : NavigationDestination {
    override val route = "signup_route"
    override val titleRes = R.string.signUp_screen
    override val showBottomBar: Boolean = false
}

@Composable
fun SignUpScreen(
    onRegistrationSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val viewModel: SignUpViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val signUpState by viewModel.signUpState.collectAsState()
    val isLoading = signUpState is SignUpState.Loading
    val snackbarHostState = remember { SnackbarHostState() }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    val isEmailFormatValid = email.contains("@") && email.contains(".")
    val isPasswordStrong = isPasswordValid(password)
    val doPasswordsMatch = confirmPassword == password && password.isNotEmpty()

    LaunchedEffect(signUpState) {
        when (signUpState) {
            is SignUpState.Success -> {
                onRegistrationSuccess()
                viewModel.resetState()
            }
            is SignUpState.Error -> {
                snackbarHostState.showSnackbar((signUpState as SignUpState.Error).message)
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
            Spacer(modifier = Modifier.weight(0.7f))

            Icon(
                painter = painterResource(id = R.drawable.ic_plant),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(dimensionResource(R.dimen.icon_size_huge))
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium_24)))

            Text(
                text = stringResource(R.string.signup_title),
                fontSize = dimensionResource(R.dimen.signup_title_size).value.sp,
                fontWeight = FontWeight.Bold,
                color = Black
            )
            Text(
                text = stringResource(R.string.signup_subtitle),
                fontSize = dimensionResource(R.dimen.auth_link_size).value.sp,
                color = Gray999
            )

            Spacer(modifier = Modifier.weight(0.5f))

            Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small_12))) {
                AppDesignField(
                    label = stringResource(R.string.signup_email_label),
                    value = email,
                    onValueChange = { email = it },
                    placeholder = stringResource(R.string.signup_email_placeholder),
                    isValid = isEmailFormatValid,
                    isError = email.isNotEmpty() && !isEmailFormatValid,
                    errorText = stringResource(R.string.signup_email_valid_error)
                )

                AppDesignField(
                    label = stringResource(id = R.string.signup_password_label),
                    value = password,
                    onValueChange = { password = it },
                    placeholder = stringResource(id = R.string.signup_password_placeholder),
                    isPassword = true,
                    isPasswordVisible = isPasswordVisible,
                    onToggleVisibility = { isPasswordVisible = !isPasswordVisible },
                    isValid = isPasswordStrong,
                    isError = password.isNotEmpty() && !isPasswordStrong,
                    errorText = stringResource(id = R.string.signup_password_valid_error)
                )

                AppDesignField(
                    label = stringResource(R.string.signup_confirm_password_label),
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    placeholder = stringResource(id = R.string.signup_confirm_password_placeholder),
                    isPassword = true,
                    isPasswordVisible = isConfirmPasswordVisible,
                    onToggleVisibility = { isConfirmPasswordVisible = !isConfirmPasswordVisible },
                    isValid = doPasswordsMatch && isPasswordStrong,
                    isError = confirmPassword.isNotEmpty() && !doPasswordsMatch,
                    errorText = stringResource(R.string.signup_password_error)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { viewModel.register(email, password) },
                enabled = !isLoading && isEmailFormatValid && isPasswordStrong && doPasswordsMatch,
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
                val canSubmit = isEmailFormatValid && isPasswordStrong && doPasswordsMatch
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = if (isLoading || !canSubmit)
                                Brush.verticalGradient(listOf(Color(0xFFE0E0E0), Color(0xFFBDBDBD)))
                            else
                                Brush.verticalGradient(listOf(GreenLight, Green800)),
                            shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium))
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isLoading) CircularProgressIndicator(
                        color = White,
                        modifier = Modifier.size(dimensionResource(R.dimen.icon_size_small))
                    )
                    else Text(
                        text = stringResource(R.string.signUp_screen),
                        color = White,
                        fontSize = dimensionResource(R.dimen.auth_subtitle_size).value.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Row(
                modifier = Modifier.padding(
                    top = dimensionResource(R.dimen.signup_bottom_padding),
                    bottom = dimensionResource(R.dimen.spacing_medium_24)
                )
            ) {
                Text(
                    text = stringResource(R.string.signup_have_account),
                    color = Black,
                    fontSize = dimensionResource(R.dimen.auth_link_size).value.sp
                )
                Text(
                    text = stringResource(R.string.signup_login_link),
                    color = Green800,
                    fontWeight = FontWeight.Bold,
                    fontSize = dimensionResource(R.dimen.auth_link_size).value.sp,
                    modifier = Modifier.clickable { onNavigateToLogin() }
                )
            }
        }
    }
}

@Composable
fun AppDesignField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isValid: Boolean,
    isError: Boolean,
    errorText: String,
    isPassword: Boolean = false,
    isPasswordVisible: Boolean = false,
    onToggleVisibility: (() -> Unit)? = null
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = if (isError) Color.Red else Color(0xFFBCBCBC),
            fontSize = dimensionResource(R.dimen.auth_label_size).value.sp
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = isError,
            visualTransformation = if (isPassword && !isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            textStyle = TextStyle(color = Black, fontSize = dimensionResource(R.dimen.auth_link_size).value.sp),
            placeholder = { Text(placeholder, color = Color(0xFFD1D1D1)) },
            trailingIcon = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_extra_small))
                ) {
                    if (isValid) Icon(Icons.Default.Check, null, tint = Green800)
                    if (isPassword) {
                        IconButton(onClick = onToggleVisibility!!) {
                            Icon(
                                if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                null,
                                tint = Green800
                            )
                        }
                    }
                }
            },
            shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium)),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF9F9F9),
                unfocusedContainerColor = Color(0xFFF9F9F9),
                focusedBorderColor = if (isError) Color.Red else Green800,
                unfocusedBorderColor = if (isError) Color.Red else Color(0xFFE8E8E8),
                errorBorderColor = Color.Red
            )
        )

        if (isError) {
            Text(
                text = errorText,
                color = Color.Red,
                fontSize = dimensionResource(R.dimen.auth_error_size).value.sp,
                modifier = Modifier.padding(
                    start = dimensionResource(R.dimen.padding_extra_small),
                    top = dimensionResource(R.dimen.signup_error_top_padding)
                )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(onRegistrationSuccess = {}, onNavigateToLogin = {})
}
