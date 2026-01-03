package com.example.growbox.screen.onboarding.components


import androidx.annotation.Dimension
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import com.example.growbox.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.dimensionResource
import com.example.growbox.ui.theme.Green800
import com.example.growbox.ui.theme.GreenLight
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
        val gradientColor = listOf(GreenLight, Green800)
        val buttonShape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium))

        Button(
            onClick = onClick,
            modifier = modifier
                .height(dimensionResource(R.dimen.button_height_medium)),
            shape = buttonShape,
            contentPadding = PaddingValues(dimensionResource(R.dimen.padding_zero)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        ){
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(colors = gradientColor),
                            shape = buttonShape
                        )
                        .clip(buttonShape),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(
                        text = text,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = dimensionResource(R.dimen.font_size_large).value.sp
                    )
                }
            }
    }

@Composable
fun FinishOnBoardingTextButton(
    text: String,
    onClick: () -> Unit
){
    TextButton(onClick = onClick){
        Text (
            text = text,
            fontSize = dimensionResource(R.dimen.font_size_large).value.sp,
            color = Green800,
            fontWeight = FontWeight.Medium
        )
    }
}