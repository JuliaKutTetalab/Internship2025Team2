package com.example.growbox.screen.profile.historic_data.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.growbox.R


@Composable
fun HistoricDataCard(
    @DrawableRes iconRes: Int,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){

    Box(
        modifier = modifier
            .clickable { onClick()}
            .shadow(
                elevation = dimensionResource(R.dimen.card_elevation).value.dp,
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_large)),
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_large))
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        ) {
            // Header^ Icon + Name
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = title,
                        modifier = Modifier.size(dimensionResource(R.dimen.icon_size_small)),
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacing_medium)))

                    Text(
                        text = title,
                        fontSize = dimensionResource(R.dimen.font_size_large).value.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}