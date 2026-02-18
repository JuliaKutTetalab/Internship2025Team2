package com.example.growbox.screen.settings.components




import androidx.annotation.DrawableRes
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import com.example.growbox.ui.theme.GreenLight
import com.example.growbox.ui.theme.White

@Composable
fun SettingsCards(
    @DrawableRes iconRes: Int,
    title: String,
    isEnabled: Boolean? = null,
    onToggle: ((Boolean) -> Unit)? = null,
    sliderValue: Float,
    onSliderChange: (Float) -> Unit,
    onSliderChangeFinished: (() -> Unit)? = null,
    sliderRange: ClosedFloatingPointRange<Float>,
    minLabel: String,
    maxLabel: String,
    currentLabel: String,
    frequency: Int? = null,
    onFrequencyChange: ((Int) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
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
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        ) {
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
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                }

                if (isEnabled != null && onToggle != null) {
                    Switch(
                        checked = isEnabled,
                        onCheckedChange = onToggle,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = White,
                            checkedTrackColor = GreenLight
                        )
                    )
                }

                if (frequency != null && onFrequencyChange != null) {
                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.height_frequency_dropdown)))
                    FrequencyDropdown(
                        selectedIndex = frequency,
                        onIndexSelected = onFrequencyChange
                    )
                }
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))

            SettingsSlider(
                value = sliderValue,
                onValueChange = onSliderChange,
                onValueChangeFinished = onSliderChangeFinished,
                valueRange = sliderRange,
                label = currentLabel,
                minLabel = minLabel,
                maxLabel = maxLabel
            )
        }
    }
}

