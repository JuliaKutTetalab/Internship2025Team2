package com.example.growbox.screen.profile.historic_data.components



import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.example.growbox.ui.theme.Green800
import com.example.growbox.ui.theme.GreenLight

@Composable
fun ValueCard(
    @DrawableRes iconRes: Int,
    label: String,
    value: String,
    date: String,
    iconTint: Color = GreenLight,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = dimensionResource(R.dimen.card_elevation).value.dp,
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_large))
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_large))
            )
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small))
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = label,
                    tint = Green800,
                    modifier = Modifier.size(dimensionResource(R.dimen.icon_size_small))
                )

                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacing_small)))

                Text(
                    text = label,
                    fontSize = dimensionResource(R.dimen.font_size_medium).value.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }


            Text(
                text = value,
                fontSize = dimensionResource(R.dimen.font_size_huge).value.sp,
                fontWeight = FontWeight.Bold,
                color = iconTint
            )


            Text(
                text = date,
                fontSize = dimensionResource(R.dimen.font_size_medium).value.sp,
                color = Color.Black
            )
        }
    }
}