package com.example.growbox.screen.home.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.growbox.R
import com.example.growbox.ui.theme.Black
import com.example.growbox.ui.theme.Green800
import com.example.growbox.ui.theme.GreenLight



@Composable
fun SensorCard(
    iconRes: Int,
    value: String,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    valueColor: Color = GreenLight
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = dimensionResource(R.dimen.card_elevation),
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_large))
            )
            .background(Color.White, RoundedCornerShape(dimensionResource(R.dimen.corner_radius_large)))
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = Green800,
                modifier = Modifier.size(dimensionResource(R.dimen.icon_size_small))
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_small)))
            Text(
                text = value,
                fontSize = dimensionResource(R.dimen.font_size_huge).value.sp,
                fontWeight = FontWeight.Bold,
                color = valueColor
            )
            Text(
                text = label,
                fontSize = dimensionResource(R.dimen.font_size_medium).value.sp,
                color = Black,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}