package com.example.growbox.screen.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.growbox.ui.theme.GreenLight
import com.example.growbox.R
import com.example.growbox.data.model.Crop
import com.example.growbox.data.model.User

@Composable
fun ProfileHeaderCard(
    user: User?,
    crop: Crop?,
    email: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.padding_small)),
        shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_huge)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.card_elevation_small))
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_large))
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_plant),
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(R.dimen.profile_avatar_size)),
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_small_12)))

            val farmName = user?.farmName?.takeIf { it.isNotBlank() }
            if (farmName != null) {
                Text(
                    text = farmName,
                    fontSize = dimensionResource(R.dimen.font_size_large).value.sp,
                    fontWeight = FontWeight.Bold,
                    color = GreenLight
                )
            }
            Text(
                text = email,
                fontSize = dimensionResource(R.dimen.font_size_medium).value.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium_24)))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatItem(
                    label = "Total Harvest",
                    value = user?.totalHarvestCount?.toString() ?: "0",
                    valueColor = GreenLight
                )
                VerticalDivider()
                StatItem(
                    label = "Corp Type",
                    value = crop?.cropType ?: "None",
                    valueColor = GreenLight
                )
                VerticalDivider()
                StatItem(
                    label = "Total Days",
                    value = user?.totalDaysGrown?.toString() ?: "0",
                    valueColor = GreenLight
                )
            }
        }
    }
}

@Composable
fun StatItem(label: String, value: String, valueColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            fontSize = dimensionResource(R.dimen.font_size_small).value.sp,
            color = Color.Black
        )
        Text(
            text = value,
            fontSize = dimensionResource(R.dimen.font_size_medium).value.sp,
            fontWeight = FontWeight.Bold,
            color = valueColor
        )
    }
}

@Composable
fun VerticalDivider() {
    Box(
        modifier = Modifier
            .height(dimensionResource(R.dimen.profile_divider_height))
            .width(1.dp)
            .background(Color(0xFFE0E0E0))
    )
}
