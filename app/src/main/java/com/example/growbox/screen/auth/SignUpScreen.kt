package com.example.growbox.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.growbox.R
import kotlinx.coroutines.delay
import androidx.compose.ui.res.painterResource
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import com.example.growbox.ui.theme.Black
import com.example.growbox.ui.theme.Gray999
import com.example.growbox.ui.theme.Green800
import com.example.growbox.ui.theme.White
import com.example.growbox.ui.theme.GreenLight
import androidx.compose.runtime.remember
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun SignUpScreen(
    onNavigateToLogin: () -> Unit = {},
    onSignUpClick: (email: String, password: String, confirmPassword: String) -> Unit = {_,_,_->}
){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = dimensionResource(R.dimen.padding_large)),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_giant)))

        Icon(
            painter = painterResource(id = R.drawable.ic_plant),
            contentDescription = "Plant icon",
            tint = Color.Unspecified,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.icon_size_huge))
        )

        Spacer( modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))

        Text(
            text = "Create an account",
            fontSize = dimensionResource(R.dimen.font_size_title).value.sp,
            fontWeight = FontWeight.Bold,
            color = Black
        )

        Spacer( modifier = Modifier.height(dimensionResource(R.dimen.spacing_small)))

        Text(
            text = "Create an account to start growing plants",
            fontSize = dimensionResource(R.dimen.font_size_large).value.sp,
            color = Gray999
        )

        Spacer( modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))

        Column(modifier = Modifier.fillMaxWidth()){
            Text(
                text = "Email",
                fontSize = dimensionResource(R.dimen.font_size_small).value.sp,
                color = Gray999,
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.spacing_small))
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = {
                    Text("nick.name@mail.com", color = Black)
                },
                singleLine = true,
                trailingIcon = {
                    if (email.contains("@") && email.contains(".")){
                        Icon(
                            Icons.Default.Check,
                            contentDescription = "Valid email",
                            tint = Green800
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium)),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Green800,
                    unfocusedBorderColor = White
                )
            )
        }

        Spacer( modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))

        Column(modifier = Modifier.fillMaxWidth()){
            Text(
                text = "Password",
                fontSize = dimensionResource(R.dimen.font_size_small).value.sp,
                color = Gray999,
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.spacing_small))
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = {
                    Text ("********", color = Black )
                },
                singleLine = true,
                visualTransformation = if (isPasswordVisible)
                    VisualTransformation.None
                else PasswordVisualTransformation(),
                trailingIcon = {
                    Row {
                        if (password.length >= 8){
                            Icon(
                                Icons.Default.Check,
                                contentDescription = "Valid password",
                                tint = Green800,
                                modifier = Modifier.padding(end = dimensionResource(R.dimen.spacing_small))
                            )
                        }

                        IconButton(onClick = {
                            isPasswordVisible = !isPasswordVisible
                        }) {
                            Icon(
                                imageVector = if (isPasswordVisible)
                                    Icons.Default.Visibility
                                else Icons.Default.VisibilityOff,
                                contentDescription = "Toggle password",
                                tint = Green800
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium)),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Green800,
                    unfocusedBorderColor = White
                )
            )
        }

        Spacer( modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))

        Column(modifier = Modifier.fillMaxWidth()){
            Text(
                text = "Confirm Password",
                fontSize = dimensionResource(R.dimen.font_size_small).value.sp,
                color = Gray999,
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.spacing_small))
            )

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = {
                    Text ("********", color = Black )
                },
                singleLine = true,
                visualTransformation = if (isConfirmPasswordVisible)
                    VisualTransformation.None
                    else PasswordVisualTransformation(),
                trailingIcon = {
                    Row {
                        if (confirmPassword.isNotEmpty() && confirmPassword == password) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = "Password match",
                                tint = Green800,
                                modifier = Modifier.padding(end = dimensionResource(R.dimen.spacing_small))
                            )
                        }

                        IconButton(onClick = {
                            isConfirmPasswordVisible = !isConfirmPasswordVisible
                        }) {
                            Icon(
                                imageVector = if (isConfirmPasswordVisible)
                                    Icons.Default.Visibility
                                    else Icons.Default.VisibilityOff,
                                contentDescription = "Toggle password",
                                tint = Green800
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium)),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Green800,
                    unfocusedBorderColor = White
                )
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))

        Button(
            onClick = {
                onSignUpClick(email, password,confirmPassword)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.button_height_large))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(GreenLight, Green800)
                    ),
                    shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium))
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium))
        ) {
            Text(
                text = "Sign up",
                fontSize = dimensionResource(R.dimen.font_size_large).value.sp,
                fontWeight = FontWeight.SemiBold,
                color = White
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(R.dimen.padding_extra_large))
        ){
            Text(
                text = "Already have an account? ",
                fontSize = dimensionResource(R.dimen.font_size_large).value.sp,
                color = Black,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Log in",
                fontSize = dimensionResource(R.dimen.font_size_large).value.sp,
                fontWeight = FontWeight.SemiBold,
                color = Green800,
                modifier = Modifier.clickable{ onNavigateToLogin() }
            )
        }

    }



}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen()
}