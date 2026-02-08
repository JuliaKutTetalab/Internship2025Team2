package com.example.growbox.screen.profile.change_crop_type.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.growbox.R
import com.example.growbox.ui.theme.Gray999
import com.example.growbox.ui.theme.GreenLight

@Composable
fun CurrentCropInfo(
    cropType: String,
    currentDay: Int,
    totalDays: Int
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.padding_medium)),
        horizontalAlignment = Alignment.Start
    ){
        Text(
            text = stringResource(R.string.current_crop_type_title),
            fontSize = dimensionResource(R.dimen.font_size_medium).value.sp,
            color = Gray999
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_small)))

        Text(
            text = cropType,
            fontSize = dimensionResource(R.dimen.font_size_large).value.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_small)))

        val progress = if (totalDays > 0) currentDay.toFloat() / totalDays else 0f
        val daysLeft = totalDays - currentDay

        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.spacing_small))
                .clip(RoundedCornerShape(dimensionResource(R.dimen.corner_radius_small))),
            color = GreenLight,
            trackColor = Gray999.copy(alpha = 0.3f)
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_extra_small)))

        Text(
            text = "$currentDay/$totalDays days ($daysLeft days till harvest)",
            fontSize = dimensionResource(R.dimen.font_size_description).value.sp,
            color = GreenLight
        )
    }
}