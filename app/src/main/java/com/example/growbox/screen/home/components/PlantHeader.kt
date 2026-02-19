package com.example.growbox.screen.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.growbox.data.model.Crop
import com.example.growbox.R
import com.example.growbox.ui.theme.Gray999
import com.example.growbox.ui.theme.GreenLight

@Composable
fun PlantHeader(crop: Crop?) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.ic_nutrition_icon),
            contentDescription = null,
            modifier = Modifier
                .size(dimensionResource(R.dimen.plant_header_image_size))
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_small)))
        Text(
            text = crop?.cropType ?: "No active crop",
            fontSize = dimensionResource(R.dimen.plant_header_title_size).value.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        val current = crop?.currentDay ?: 0
        val total = crop?.totalDays ?: 21
        val progress = if (total > 0) current.toFloat() / total else 0f

        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(dimensionResource(R.dimen.spacing_small))
                .clip(RoundedCornerShape(dimensionResource(R.dimen.corner_radius_small))),
            color = GreenLight,
            trackColor = Gray999.copy(alpha = 0.3f)
        )
        Text(
            text = "$current/$total days (${total - current} days till harvest)",
            fontSize = dimensionResource(R.dimen.font_size_small).value.sp,
            color = GreenLight
        )
    }
}
