package com.example.growbox.screen.auth.auth

import androidx.compose.runtime.Composable
//import com.example.growbox.R
import com.example.growbox.navigation.NavigationDestination
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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



object SignUpDestination: NavigationDestination {
    override val route = "signup_route"
    override val titleRes = R.string.signUp_screen
}
@Composable
fun SignUpScreen(
    onRegistrationSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit = {}
) {

    val viewModel: SignUpViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val signUpState by viewModel.signUpState.collectAsState()
    val isLoading = signUpState is SignUpState.Loading


    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    var passwordError by remember { mutableStateOf<String?>(null) }


    LaunchedEffect(signUpState) {
        when (signUpState) {
            is SignUpState.Success -> {
                onRegistrationSuccess()

                viewModel.resetState()
            }
            is SignUpState.Error -> {
                val message = (signUpState as SignUpState.Error).message
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
        ){
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_giant)))

            // ІКОНКА ТА ЗАГОЛОВКИ
            Icon(
                painter = painterResource(id = R.drawable.ic_plant),
                contentDescription = stringResource(R.string.content_description_plant_icon),
                tint = Color.Unspecified,
                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size_huge))
            )

            Spacer( modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))

            Text(
                text = stringResource(R.string.signup_title),
                fontSize = dimensionResource(R.dimen.font_size_title).value.sp,
                fontWeight = FontWeight.Bold,
                color = Black
            )

            Spacer( modifier = Modifier.height(dimensionResource(R.dimen.spacing_small)))

            Text(
                text = stringResource(R.string.signup_subtitle),
                fontSize = dimensionResource(R.dimen.font_size_large).value.sp,
                color = Gray999
            )

            Spacer( modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))

            // ПОЛЕ EMAIL
            Column(modifier = Modifier.fillMaxWidth()){
                Text(
                    text = stringResource(R.string.signup_email_label),
                    fontSize = dimensionResource(R.dimen.font_size_small).value.sp,
                    color = Gray999,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.spacing_small))
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it; passwordError = null },
                    placeholder = { Text(stringResource(R.string.signup_email_placeholder), color = Black) },
                    singleLine = true,
                    trailingIcon = {
                        // Дуже проста валідація наявності символів @ та .
                        if (email.contains("@") && email.contains(".")){
                            Icon(Icons.Default.Check, contentDescription = stringResource(R.string.content_description_valid_email), tint = Green800)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium)),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Green800, unfocusedBorderColor = White)
                )
            }

            Spacer( modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))

            // ПОЛЕ PASSWORD
            Column(modifier = Modifier.fillMaxWidth()){
                Text(
                    text = stringResource(R.string.signup_password_label),
                    fontSize = dimensionResource(R.dimen.font_size_small).value.sp,
                    color = Gray999,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.spacing_small))
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it; passwordError = null },
                    placeholder = { Text (stringResource(R.string.signup_password_placeholder), color = Black ) },
                    singleLine = true,
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        Row {
                            if (password.length >= 8){
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = stringResource(R.string.content_description_valid_password),
                                    tint = Green800,
                                    modifier = Modifier.padding(end = dimensionResource(R.dimen.spacing_small))
                                )
                            }
                            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                Icon(
                                    imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = stringResource(R.string.content_description_toggle_password),
                                    tint = Green800
                                )
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium)),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Green800, unfocusedBorderColor = White)
                )
            }

            Spacer( modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))

            // ПОЛЕ CONFIRM PASSWORD
            Column(modifier = Modifier.fillMaxWidth()){
                Text(
                    text = stringResource(R.string.signup_confirm_password_label),
                    fontSize = dimensionResource(R.dimen.font_size_small).value.sp,
                    color = Gray999,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.spacing_small))
                )

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it; passwordError = null },
                    placeholder = { Text (stringResource(R.string.signup_confirm_password_placeholder), color = Black ) },
                    singleLine = true,
                    visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    isError = passwordError != null, // Відображення помилки
                    trailingIcon = {
                        Row {
                            if (isPasswordValid(password)){
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = stringResource(R.string.content_description_valid_password),
                                    tint = Green800,
                                    modifier = Modifier.padding(end = dimensionResource( R.dimen.spacing_small))
                                )
                            }
                            IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                                Icon(
                                    imageVector = if (isConfirmPasswordVisible)
                                        Icons.Default.Visibility
                                        else Icons.Default.VisibilityOff,
                                    contentDescription = stringResource(R.string.content_description_toggle_password),
                                    tint = Green800
                                )
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium)),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Green800, unfocusedBorderColor = White)
                )
                if (passwordError != null) {
                    Text(text = passwordError!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                }
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))

            // КНОПКА "SIGN UP"
            Button(
                onClick = {
                    if (!isLoading) {
                        if (password != confirmPassword) {
                            passwordError = R.string.signup_password_error.toString()
                            return@Button
                        }
                        passwordError = null
                        viewModel.register(email, password)
                    }
                    if (!isPasswordValid(password)) {
                        passwordError = R.string.signup_password_valid_error.toString()
                        return@Button
                    }
                    passwordError = null
                    viewModel.register(email, password)
                },
                enabled = !isLoading && email.isNotBlank() && isPasswordValid(password) && password == confirmPassword,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.LightGray.copy(alpha = 0.5f)
                ),
                contentPadding = PaddingValues(dimensionResource(R.dimen.padding_zero)),
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium)),
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
                            text = stringResource(R.string.signup_button),
                            fontSize = dimensionResource(R.dimen.font_size_large).value.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // НАВІГАЦІЯ НА LOGIN
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(R.dimen.padding_extra_large))
            ){
                Text(
                    text = stringResource(R.string.signup_have_account),
                    fontSize = dimensionResource(R.dimen.font_size_large).value.sp,
                    color = Black,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = stringResource(R.string.signup_login_link),
                    fontSize = dimensionResource(R.dimen.font_size_large).value.sp,
                    fontWeight = FontWeight.Bold,
                    color = Green800,
                    modifier = Modifier.clickable{ onNavigateToLogin() }
                )
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(onRegistrationSuccess = {}, onNavigateToLogin = {})
}