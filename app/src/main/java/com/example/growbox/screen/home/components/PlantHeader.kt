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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.growbox.data.model.Crop
import com.example.growbox.R
import com.example.growbox.ui.theme.Gray999
import com.example.growbox.ui.theme.GreenLight

@Composable
fun PlantHeader(crop: Crop?) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.ic_nutrion_icon),//поки така іконка
            contentDescription = null,
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = crop?.cropType ?: "No active crop",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        val current = crop?.currentDay ?: 0
        val total = crop?.totalDays ?: 21
        val progress = if (total > 0) current.toFloat() / total else 0f

        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = GreenLight,
            trackColor = Gray999.copy(alpha = 0.3f)
        )
        Text(
            text = "$current/$total days (${total - current} days till harvest)",
            fontSize = 12.sp,
            color = GreenLight
        )
    }
}