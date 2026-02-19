package com.example.growbox.screen.home.nutrition_chart.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.growbox.R
import com.example.growbox.ui.theme.GreenLight
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.growbox.ui.theme.Black


@Composable
fun StatCard(
    value: String,
    label: String,
    valueColor: Color,
    modifier: Modifier = Modifier
){

    Box(
        modifier = modifier
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
        Column (modifier = modifier.padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Text(
                text = value,
                fontSize = dimensionResource(R.dimen.font_size_huge).value.sp,
                fontWeight = FontWeight.Bold,
                color = valueColor
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_extra_small)))

            Text(
                text = label,
                fontSize = dimensionResource(R.dimen.font_size_medium).value.sp,
                color = Black,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun NutritionStatCards(
    currentValue: Int,
    recommendedValue: Int,
    weekConsumption: String,
    totalConsumption: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium))) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium))

        ) {
            StatCard(
                value = stringResource(R.string.value_percentage, currentValue),
                label = stringResource(R.string.current_information),
                valueColor = GreenLight,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                value = stringResource(R.string.value_percentage, recommendedValue),
                label = stringResource(R.string.recommended_information),
                valueColor = GreenLight,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium))
        ) {
            StatCard(
                value = stringResource(R.string.value_milligrams, weekConsumption),
                label = stringResource(R.string.week_information),
                valueColor = GreenLight,
                modifier = Modifier.weight(1f)
            )

            StatCard(
                value = stringResource(R.string.value_milligrams, totalConsumption),
                label = stringResource(R.string.total_information),
                valueColor = GreenLight,
                modifier = Modifier.weight(1f)
            )
        }
    }
}