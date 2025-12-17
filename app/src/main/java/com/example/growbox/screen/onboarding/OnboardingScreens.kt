package com.example.growbox.screen.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.growbox.R
import com.example.growbox.ui.theme.Black
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.growbox.ui.theme.Black
import com.example.growbox.ui.theme.Gray999
import com.example.growbox.ui.theme.Green800
import com.example.growbox.ui.theme.GreenLight
import com.example.growbox.ui.theme.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle

// SCREEN 1: Choose your plants
@Composable
fun OnboardingScreen1(
    onSkip: () -> Unit = {},
    onNext: () -> Unit = {}
) {
    OnboardingScreenTemplate(
        title = stringResource(R.string.onboarding_screen1_title),
        description = stringResource(R.string.onboarding_screen1_description),
        currentPage = 0,
        totalPages = 3,
        onSkip = onSkip,
        onNext = onNext
    )
}

// SCREEN 2: Connect and control
@Composable
fun OnboardingScreen2(
    onSkip: () -> Unit = {},
    onNext: () -> Unit = {}
) {
    OnboardingScreenTemplate(
        title = stringResource(R.string.onboarding_screen2_title),
        description = stringResource(R.string.onboarding_screen2_description),
        currentPage = 1,
        totalPages = 3,
        onSkip = onSkip,
        onNext = onNext
    )
}

// SCREEN 3: Observe and grow
@Composable
fun OnboardingScreen3(
    onSkip: () -> Unit = {},
    onNext: () -> Unit = {}
) {
    OnboardingScreenTemplate(
        title = stringResource(R.string.onboarding_screen3_title),
        description = stringResource(R.string.onboarding_screen3_description),
        currentPage = 2,
        totalPages = 3,
        onSkip = onSkip,
        onNext = onNext
    )
}

// TEMPLATE (for all 3 screens)
@Composable
private fun OnboardingScreenTemplate(
    title: String,
    description: String,
    currentPage: Int,
    totalPages: Int,
    onSkip: () -> Unit,
    onNext: () -> Unit
){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimensionResource(R.dimen.padding_large)),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_huge)))

                Text(
                    text = title,
                    fontSize = dimensionResource(R.dimen.font_size_title_large).value.sp,
                    fontWeight = FontWeight.Bold,
                    color = Black,
                    textAlign = TextAlign.Center

                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_huge)))
                Box(
                    modifier = Modifier
                        .size(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_onboarding_plant_1),
                        contentDescription = stringResource(R.string.onboarding_plant_description),
                        modifier = Modifier.size(dimensionResource(R.dimen.icon_size_extra_huge))
                    )
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))

                Text(
                    text = description,
                    fontSize = dimensionResource(R.dimen.font_size_huge).value.sp,
                    color = Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.font_size_huge))
                )

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_huge)))

                //Індикатори сторінок (крапки)
                PageIndicator(
                    currentPage = currentPage,
                    totalPages = totalPages
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = dimensionResource(R.dimen.padding_extra_large)),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    //Slip button
                    TextButton(onClick = onSkip) {
                        Text(
                            text = stringResource(R.string.onboarding_skip),
                            fontSize = dimensionResource(R.dimen.font_size_large).value.sp,
                            color = Green800,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    //Next button
                    Button(
                        onClick = onNext,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GreenLight
                        ),
                        shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium)),
                        modifier = Modifier
                            .width(120.dp)
                            .height(dimensionResource(R.dimen.button_height_medium))
                    ) {
                        Text(
                            text = stringResource(R.string.onboarding_next),
                            fontSize = dimensionResource(R.dimen.font_size_large).value.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = White
                        )
                    }
                }
            }
        }
}

//Page indicator (точки внизу)
@Composable
private fun PageIndicator(
    currentPage: Int,
    totalPages: Int
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small)),
        verticalAlignment = Alignment.CenterVertically
    ){
        repeat(totalPages) { index ->
            Box(
                modifier = Modifier
                    .size(if (index == currentPage) 10.dp else 8.dp)
                    .clip(CircleShape)
                    .background(
                        if (index == currentPage)
                            GreenLight
                        else
                            Gray999
                    )
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnboardingScreen1Preview() {
    OnboardingScreen1()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnboardingScreen2Preview() {
    OnboardingScreen2()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnboardingScreen3Preview() {
    OnboardingScreen3()
}