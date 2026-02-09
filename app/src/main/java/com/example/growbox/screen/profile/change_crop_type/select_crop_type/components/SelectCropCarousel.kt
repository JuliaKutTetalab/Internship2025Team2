package com.example.growbox.screen.profile.change_crop_type.select_crop_type.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.growbox.R
import com.example.growbox.ui.theme.Green200
import com.example.growbox.ui.theme.Green800
import com.example.growbox.ui.theme.GreenLight
import kotlin.math.absoluteValue


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectCropCarousel(
    cropTypeResIds: List<Int>,
    onCropTypeSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    val pagerState = rememberPagerState (pageCount = { cropTypeResIds.size })

    // Передасть вибраний індекс назовні
    LaunchedEffect(pagerState.currentPage) {
        onCropTypeSelected(pagerState.currentPage)
    }

    VerticalPager(
        state = pagerState,
        modifier = modifier.height(dimensionResource(R.dimen.crop_type_height_huge)),
        contentPadding = PaddingValues(vertical = dimensionResource(R.dimen.padding_extra_huge))
    ) { page ->
        val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction

        val cropTypeName = stringResource(cropTypeResIds[page])

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                .graphicsLayer {
                    val scale = 1f - (pageOffset.absoluteValue * 0.2f).coerceIn(0f, 0.5f)
                    scaleX = scale
                    scaleY = scale
                    alpha = 1f - (pageOffset.absoluteValue * 0.7f).coerceIn(0f, 0.7f)
                }
                .background(
                    color = if (pageOffset.absoluteValue < 0.5f) Green200 else Color.Transparent,
                    shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_huge))
                )
                .padding(vertical = dimensionResource(R.dimen.padding_medium), horizontal = dimensionResource(R.dimen.padding_small)),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = cropTypeName,
                textAlign = TextAlign.Center,
                fontSize = dimensionResource(R.dimen.font_size_large).value.sp,
                fontWeight = FontWeight.Bold,
                color = if (pageOffset.absoluteValue < 0.5f) Green800 else GreenLight
            )
        }
    }
}