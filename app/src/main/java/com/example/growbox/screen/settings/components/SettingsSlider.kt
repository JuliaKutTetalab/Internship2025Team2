package com.example.growbox.screen.settings.components



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp
import com.example.growbox.R
import com.example.growbox.ui.theme.Green200
import com.example.growbox.ui.theme.Green300
import com.example.growbox.ui.theme.Green800
import com.example.growbox.ui.theme.White

@Composable
fun SettingsSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: (() -> Unit)? = null,
    valueRange: ClosedFloatingPointRange<Float>,
    label: String,
    minLabel: String,
    maxLabel: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Slider(
            value = value,
            onValueChange = onValueChange,
            onValueChangeFinished = { onValueChangeFinished?.invoke() },
            valueRange = valueRange,
            colors = SliderDefaults.colors(
                thumbColor = White,
                activeTrackColor = Green800,
                inactiveTrackColor = Green200,
                activeTickColor = Color.Transparent,
                inactiveTickColor = Color.Transparent
            ),
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = minLabel, fontSize = dimensionResource(R.dimen.font_size_small).value.sp, color = Green300)
            Text(text = label,    fontSize = dimensionResource(R.dimen.font_size_small).value.sp, color = Green300)
            Text(text = maxLabel, fontSize = dimensionResource(R.dimen.font_size_small).value.sp, color = Green300)
        }
    }
}


