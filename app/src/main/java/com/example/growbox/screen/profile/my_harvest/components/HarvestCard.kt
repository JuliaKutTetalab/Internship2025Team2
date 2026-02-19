package com.example.growbox.screen.profile.my_harvest.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.growbox.R
import com.example.growbox.ui.theme.GreenLight

@Composable
fun HarvestCard(
    cropType: String,
    harvests: Int,
    totalDays: Int
) {
    val cardBackground = Color(0xFFF1F4F3)
    val numberColor = Color(0xFF2E7D32)
    val textColor = Color.Black

    Card(
        shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_huge)),
        colors = CardDefaults.cardColors(containerColor = cardBackground),
        elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.harvest_card_elevation)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.harvest_card_padding)),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .size(dimensionResource(R.dimen.harvest_icon_box_size))
                    .clip(CircleShape)
                    .background(Color(0xFFE5ECEA)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Eco,
                    contentDescription = null,
                    tint = GreenLight,
                    modifier = Modifier.size(dimensionResource(R.dimen.harvest_icon_size))
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))

            Text(
                text = cropType,
                fontSize = dimensionResource(R.dimen.harvest_crop_name_size).value.sp,
                fontWeight = FontWeight.Bold,
                color = GreenLight
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_small_12)))

            Row {
                Text(
                    text = "Harvests: ",
                    fontSize = dimensionResource(R.dimen.auth_subtitle_size).value.sp,
                    color = textColor
                )
                Text(
                    text = harvests.toString(),
                    fontSize = dimensionResource(R.dimen.auth_subtitle_size).value.sp,
                    fontWeight = FontWeight.Bold,
                    color = numberColor
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.harvest_row_spacing)))

            Row {
                Text(
                    text = "Total Days: ",
                    fontSize = dimensionResource(R.dimen.auth_subtitle_size).value.sp,
                    color = textColor
                )
                Text(
                    text = totalDays.toString(),
                    fontSize = dimensionResource(R.dimen.auth_subtitle_size).value.sp,
                    fontWeight = FontWeight.Bold,
                    color = numberColor
                )
            }
        }
    }
}
