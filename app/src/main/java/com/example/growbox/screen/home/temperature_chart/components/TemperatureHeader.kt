package com.example.growbox.screen.home.temperature_chart.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.growbox.R
import com.example.growbox.ui.theme.Green800
import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.growbox.ui.theme.GreenLight
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.growbox.ui.theme.Gray999


@Composable
fun TemperatureHeader(
    iconRes: Int,
    description: String,
    currentValue: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.padding_medium), vertical = dimensionResource(R.dimen.padding_medium)),
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(
            modifier = Modifier
                .size(dimensionResource(R.dimen.icon_size_extra_large)),
            contentAlignment = Alignment.Center
        ){
            Icon(
                painter = painterResource(iconRes),
                contentDescription = stringResource(R.string.temperature_title),
                tint = Green800,
                modifier = Modifier.size(dimensionResource(R.dimen.icon_size_large))
            )
        }

        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacing_medium)))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = description,
                fontSize = dimensionResource(R.dimen.font_size_small).value.sp,
                color = Gray999,
                maxLines = 2
            )
        }

        Text(
            text = "$currentValue%",
            fontSize = dimensionResource(R.dimen.font_size_huge).value.sp,
            fontWeight = FontWeight.Bold,
            color = GreenLight
        )
    }
}