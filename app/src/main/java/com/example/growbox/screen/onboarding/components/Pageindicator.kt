package com.example.growbox.screen.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.CircleShape
import com.example.growbox.R
import androidx.compose.ui.res.dimensionResource
import com.example.growbox.ui.theme.Gray999
import com.example.growbox.ui.theme.GreenLight


@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    pageSize: Int,
    selectedPage: Int,
    selectedColor: Color = GreenLight,
    unselectedColor: Color = Gray999
    ){
        Row(modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small))
            )
        {
            repeat(pageSize) { page ->
                Box(modifier = Modifier
                    .size(
                        if (page == selectedPage)
                            dimensionResource(R.dimen.page_indicator_size_selected)
                        else
                            dimensionResource(R.dimen.page_indicator_size_unselected)
                    )
                    .clip(CircleShape)
                    .background(color = if (page == selectedPage) selectedColor else unselectedColor)
                )
            }
        }

}