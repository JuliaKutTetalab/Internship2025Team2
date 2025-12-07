package com.example.growbox.screen.auth

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import com.example.growbox.R
import com.example.growbox.ui.theme.Black
import com.example.growbox.ui.theme.Gray999
import com.example.growbox.ui.theme.Green800
import com.example.growbox.ui.theme.GreenLight
import com.example.growbox.ui.theme.White


@Composable
fun LoginScreen(
    onNavigateToSingUp: () -> Unit = {},
    onLoginClick: (email: String, password: String) -> Unit = {_,_->}
) {
    //Тимчасово, переробити коли буде ViewModel
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

Column(
    modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(horizontal = 24.dp),
    horizontalAlignment = Alignment.CenterHorizontally
){
    Spacer(modifier = Modifier.height(170.dp))

    Icon(
        painter = painterResource(id = R.drawable.ic_plant),
        contentDescription = "Plant icon",
        tint = Color.Unspecified,
        modifier = Modifier.size(110.dp)
    )
    Spacer(modifier = Modifier.height(32.dp))

    Text(
        text = "Welcome back",
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold,
        color = Black
    )
    Spacer(modifier = Modifier.height(10.dp))

    Text(
        text = "Sing in to grow your plants",
        fontSize = 20.sp,
        color = Gray999
    )
    Spacer(modifier = Modifier.height(60.dp))

    Column(modifier = Modifier.fillMaxWidth()) {

        Text(
            text = "Email",
            fontSize = 16.sp,
            color = Gray999,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = {
                Text(
                    text = "nick.name@gmail.com",
                    color = Black
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Green800,
                unfocusedBorderColor = White
            ),
        )
    }
    Spacer(Modifier.height(20.dp))

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Password",
            fontSize = 16.sp,
            color = Gray999,
            modifier = Modifier.padding(bottom = 10.dp)


        )

        OutlinedTextField(
            value = password,
            onValueChange = {password = it },
            placeholder = {
                Text("********", color = Black)
            },
            singleLine = true,
            visualTransformation = if (isPasswordVisible)
                VisualTransformation.None
                else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = {
                    isPasswordVisible = !isPasswordVisible
                }) {
                    Icon(
                        imageVector = if (isPasswordVisible)
                            Icons.Default.Visibility
                        else Icons.Default.VisibilityOff,
                        contentDescription = if (isPasswordVisible)
                            "Hide password"
                        else
                            "Show password",
                        tint = Color(0xFF2F7302)
                    )
                }

            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Green800,
                unfocusedBorderColor = White

            )
        )
    }

    Spacer(modifier = Modifier.height(32.dp))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                brush = Brush.verticalGradient(  // Вертикальний
                    colors = listOf(GreenLight, Green800)
                ),
                shape = RoundedCornerShape(14.dp)
            )
            .clickable {
                onLoginClick(email, password)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Sign in",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = White
        )
    }

    Spacer(modifier = Modifier.weight(1f))

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp)
    ) {
        Text(
            text = "Don`t have an account? ",
            fontSize = 18.sp,
            color = Black
        )
        Text(
            text = "Sign up",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Green800,
            modifier = Modifier.clickable{ onNavigateToSingUp() }
        )

    }

}
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}
    
