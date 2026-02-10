package com.example.growbox.screen.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.growbox.R
import com.example.growbox.screen.onboarding.Page
import com.example.growbox.screen.onboarding.pages
import com.example.growbox.ui.theme.Black


@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    page: Page
){
    var imageSize = when (page.image){
        R.drawable.ic_onboarding_plant_1 -> dimensionResource(R.dimen.onboarding_icon_size_small)
        R.drawable.ic_onboarding_plant_2 -> dimensionResource(R.dimen.onboarding_icon_size)
        R.drawable.ic_onboarding_plant_3 -> dimensionResource(R.dimen.onboarding_icon_size)
        else -> dimensionResource(R.dimen.onboarding_icon_size_small)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(R.dimen.padding_large)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_huge)))

        Text(
            text = stringResource(page.title),
            fontSize = dimensionResource(R.dimen.font_size_title).value.sp,
            fontWeight = FontWeight.Bold,
            color = Black,
            textAlign = TextAlign.Center

        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_huge)))

        BlurCard(
            modifier = Modifier.size(dimensionResource(R.dimen.content_max_width_small)),
            imageRes = page.image,
            imageSize = imageSize
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))

        Text(
            text = stringResource(page.description),
            fontSize = dimensionResource(R.dimen.font_size_medium).value.sp,
            color = Black,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.font_size_huge))
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_huge)))
    }
}

@Preview(showBackground = true)
@Composable
fun OnBoardingPagePreview() {
    OnBoardingPage(
        page = pages[0])
}