package com.example.growbox.screen.auth.auth

import androidx.compose.runtime.Composable
//import com.example.growbox.R
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
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.7f))

            Icon(
                painter = painterResource(id = R.drawable.ic_plant),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(stringResource(R.string.signup_title), fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Black)
            Text(stringResource(R.string.signup_subtitle) ,fontSize = 15.sp, color = Gray999)

            Spacer(modifier = Modifier.weight(0.5f))

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                // ПОЛЕ EMAIL
                AppDesignField(
                    label =stringResource(R.string.signup_email_label),
                    value = email,
                    onValueChange = { email = it },
                    placeholder = stringResource(R.string.signup_email_placeholder),
                    isValid = isEmailFormatValid,
                    isError = email.isNotEmpty() && !isEmailFormatValid,
                    errorText = stringResource(R.string.signup_email_valid_error)
                )

                // ПОЛЕ PASSWORD
                AppDesignField(
                    label = stringResource(id= R.string.signup_password_label),
                    value = password,
                    onValueChange = { password = it },
                    placeholder = stringResource(id= R.string.signup_password_placeholder),
                    isPassword = true,
                    isPasswordVisible = isPasswordVisible,
                    onToggleVisibility = { isPasswordVisible = !isPasswordVisible },
                    isValid = isPasswordStrong,
                    isError = password.isNotEmpty() && !isPasswordStrong,
                    errorText = stringResource(id= R.string.signup_password_valid_error)
                )

                // ПОЛЕ CONFIRM PASSWORD
                AppDesignField(
                    label = stringResource(R.string.signup_confirm_password_label),
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    placeholder = stringResource(id= R.string.signup_confirm_password_placeholder),
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
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, disabledContainerColor = Color.Transparent),
                contentPadding = PaddingValues(0.dp)
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
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isLoading) CircularProgressIndicator(color = White, modifier = Modifier.size(24.dp))
                    else Text(stringResource(R.string.signUp_screen), color = White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }

            Row(modifier = Modifier.padding(top = 20.dp, bottom = 24.dp)) {
                Text(stringResource(R.string.signup_have_account), color = Black)
                Text(stringResource(R.string.signup_login_link), color = Green800, fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onNavigateToLogin() })
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
        Text(text = label, color = if (isError) Color.Red else Color(0xFFBCBCBC), fontSize = 13.sp)

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = isError,
            visualTransformation = if (isPassword && !isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            textStyle = TextStyle(color = Black, fontSize = 15.sp),
            placeholder = { Text(placeholder, color = Color(0xFFD1D1D1)) },
            trailingIcon = {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 4.dp)) {
                    if (isValid) Icon(Icons.Default.Check, null, tint = Green800)
                    if (isPassword) {
                        IconButton(onClick = onToggleVisibility!!) {
                            Icon(if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff, null, tint = Green800)
                        }
                    }
                }
            },
            shape = RoundedCornerShape(12.dp),
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
                fontSize = 11.sp,
                modifier = Modifier.padding(start = 4.dp, top = 2.dp)
            )
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(onRegistrationSuccess = {}, onNavigateToLogin = {})
}




//@Composable
//fun SignUpScreen(
//    onRegistrationSuccess: () -> Unit,
//    onNavigateToLogin: () -> Unit = {}
//) {
//
//    val viewModel: SignUpViewModel = viewModel(factory = AppViewModelProvider.Factory)
//    val signUpState by viewModel.signUpState.collectAsState()
//    val isLoading = signUpState is SignUpState.Loading
//
//
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    var confirmPassword by remember { mutableStateOf("") }
//    var isPasswordVisible by remember { mutableStateOf(false) }
//    var isConfirmPasswordVisible by remember { mutableStateOf(false) }
//
//    val snackbarHostState = remember { SnackbarHostState() }
//    var passwordError by remember { mutableStateOf<String?>(null) }
//
//
//    LaunchedEffect(signUpState) {
//        when (signUpState) {
//            is SignUpState.Success -> {
//                onRegistrationSuccess()
//
//                viewModel.resetState()
//            }
//            is SignUpState.Error -> {
//                val message = (signUpState as SignUpState.Error).message
//                snackbarHostState.showSnackbar(
//                    message = message,
//                    duration = SnackbarDuration.Long
//                )
//
//                viewModel.resetState()
//            }
//            else -> {}
//        }
//    }
//
//    Scaffold(
//        snackbarHost = { SnackbarHost(snackbarHostState) },
//    ) { paddingValues ->
//
//        Column(
//            modifier = Modifier
//                .padding(paddingValues)
//                .fillMaxSize()
//                .background(Color.White)
//                .padding(horizontal = dimensionResource(R.dimen.padding_large)),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ){
//            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_giant)))
//
//            // ІКОНКА ТА ЗАГОЛОВКИ
//            Icon(
//                painter = painterResource(id = R.drawable.ic_plant),
//                contentDescription = stringResource(R.string.content_description_plant_icon),
//                tint = Color.Unspecified,
//                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size_huge))
//            )
//
//            Spacer( modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))
//
//            Text(
//                text = stringResource(R.string.signup_title),
//                fontSize = dimensionResource(R.dimen.font_size_title).value.sp,
//                fontWeight = FontWeight.Bold,
//                color = Black
//            )
//
//            Spacer( modifier = Modifier.height(dimensionResource(R.dimen.spacing_small)))
//
//            Text(
//                text = stringResource(R.string.signup_subtitle),
//                fontSize = dimensionResource(R.dimen.font_size_large).value.sp,
//                color = Gray999
//            )
//
//            Spacer( modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))
//
//            // ПОЛЕ EMAIL
//            Column(modifier = Modifier.fillMaxWidth()){
//                Text(
//                    text = stringResource(R.string.signup_email_label),
//                    fontSize = dimensionResource(R.dimen.font_size_small).value.sp,
//                    color = Gray999,
//                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.spacing_small))
//                )
//
//                OutlinedTextField(
//                    value = email,
//                    onValueChange = { email = it; passwordError = null },
//                    placeholder = { Text(stringResource(R.string.signup_email_placeholder), color = Black) },
//                    singleLine = true,
//                    trailingIcon = {
//                        // Дуже проста валідація наявності символів @ та .
//                        if (email.contains("@") && email.contains(".")){
//                            Icon(Icons.Default.Check, contentDescription = stringResource(R.string.content_description_valid_email), tint = Green800)
//                        }
//                    },
//                    modifier = Modifier.fillMaxWidth(),
//                    shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium)),
//                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Green800, unfocusedBorderColor = White)
//                )
//            }
//
//            Spacer( modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))
//
//            // ПОЛЕ PASSWORD
//            Column(modifier = Modifier.fillMaxWidth()){
//                Text(
//                    text = stringResource(R.string.signup_password_label),
//                    fontSize = dimensionResource(R.dimen.font_size_small).value.sp,
//                    color = Gray999,
//                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.spacing_small))
//                )
//
//                OutlinedTextField(
//                    value = password,
//                    onValueChange = { password = it; passwordError = null },
//                    placeholder = { Text (stringResource(R.string.signup_password_placeholder), color = Black ) },
//                    singleLine = true,
//                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//                    trailingIcon = {
//                        Row {
//                            if (password.length >= 8){
//                                Icon(
//                                    Icons.Default.Check,
//                                    contentDescription = stringResource(R.string.content_description_valid_password),
//                                    tint = Green800,
//                                    modifier = Modifier.padding(end = dimensionResource(R.dimen.spacing_small))
//                                )
//                            }
//                            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
//                                Icon(
//                                    imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
//                                    contentDescription = stringResource(R.string.content_description_toggle_password),
//                                    tint = Green800
//                                )
//                            }
//                        }
//                    },
//                    modifier = Modifier.fillMaxWidth(),
//                    shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium)),
//                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Green800, unfocusedBorderColor = White)
//                )
//            }
//
//            Spacer( modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))
//
//            // ПОЛЕ CONFIRM PASSWORD
//            Column(modifier = Modifier.fillMaxWidth()){
//                Text(
//                    text = stringResource(R.string.signup_confirm_password_label),
//                    fontSize = dimensionResource(R.dimen.font_size_small).value.sp,
//                    color = Gray999,
//                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.spacing_small))
//                )
//
//                OutlinedTextField(
//                    value = confirmPassword,
//                    onValueChange = { confirmPassword = it; passwordError = null },
//                    placeholder = { Text (stringResource(R.string.signup_confirm_password_placeholder), color = Black ) },
//                    singleLine = true,
//                    visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//                    isError = passwordError != null, // Відображення помилки
//                    trailingIcon = {
//                        Row {
//                            if (isPasswordValid(password)){
//                                Icon(
//                                    Icons.Default.Check,
//                                    contentDescription = stringResource(R.string.content_description_valid_password),
//                                    tint = Green800,
//                                    modifier = Modifier.padding(end = dimensionResource( R.dimen.spacing_small))
//                                )
//                            }
//                            IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
//                                Icon(
//                                    imageVector = if (isConfirmPasswordVisible)
//                                        Icons.Default.Visibility
//                                        else Icons.Default.VisibilityOff,
//                                    contentDescription = stringResource(R.string.content_description_toggle_password),
//                                    tint = Green800
//                                )
//                            }
//                        }
//                    },
//                    modifier = Modifier.fillMaxWidth(),
//                    shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium)),
//                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Green800, unfocusedBorderColor = White)
//                )
//                if (passwordError != null) {
//                    Text(text = passwordError!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
//                }
//            }
//
//            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))
//
//            // КНОПКА "SIGN UP"
//            Button(
//                onClick = {
//                    if (!isLoading) {
//                        if (password != confirmPassword) {
//                            passwordError = R.string.signup_password_error.toString()
//                            return@Button
//                        }
//                        passwordError = null
//                        viewModel.register(email, password)
//                    }
//                    if (!isPasswordValid(password)) {
//                        passwordError = R.string.signup_password_valid_error.toString()
//                        return@Button
//                    }
//                    passwordError = null
//                    viewModel.register(email, password)
//                },
//                enabled = !isLoading && email.isNotBlank() && isPasswordValid(password) && password == confirmPassword,
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color.Transparent,
//                    disabledContainerColor = Color.LightGray.copy(alpha = 0.5f)
//                ),
//                contentPadding = PaddingValues(dimensionResource(R.dimen.padding_zero)),
//                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium)),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(dimensionResource(R.dimen.button_height_large))
//            ) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(
//                            brush = if (isLoading) {
//                                Brush.verticalGradient(listOf(Color.Gray, Color.DarkGray))
//                            } else {
//                                Brush.verticalGradient(colors = listOf(GreenLight, Green800))
//                            },
//                            shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium))
//                        ),
//                    contentAlignment = Alignment.Center
//                ) {
//                    if (isLoading) {
//                        CircularProgressIndicator(color = White, modifier = Modifier.size(30.dp))
//                    } else {
//                        Text(
//                            text = stringResource(R.string.signup_button),
//                            fontSize = dimensionResource(R.dimen.font_size_large).value.sp,
//                            fontWeight = FontWeight.SemiBold,
//                            color = White
//                        )
//                    }
//                }
//            }
//
//            Spacer(modifier = Modifier.weight(1f))
//
//            // НАВІГАЦІЯ НА LOGIN
//            Row(
//                horizontalArrangement = Arrangement.Center,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = dimensionResource(R.dimen.padding_extra_large))
//            ){
//                Text(
//                    text = stringResource(R.string.signup_have_account),
//                    fontSize = dimensionResource(R.dimen.font_size_large).value.sp,
//                    color = Black,
//                    fontWeight = FontWeight.SemiBold
//                )
//                Text(
//                    text = stringResource(R.string.signup_login_link),
//                    fontSize = dimensionResource(R.dimen.font_size_large).value.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Green800,
//                    modifier = Modifier.clickable{ onNavigateToLogin() }
//                )
//            }
//
//        }
//    }
//}

