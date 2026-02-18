package com.example.growbox.screen.bluetooth.components



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.growbox.R
import com.example.growbox.ui.theme.Gray999
import com.example.growbox.ui.theme.Green800
import com.example.growbox.ui.theme.GreenLight
import com.example.growbox.ui.theme.White

@Composable
fun GradientButton(
    text: String,
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val gradientColor = listOf(GreenLight, Green800)
    val buttonShape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium))

    Button(
        onClick = onClick,
        enabled = isEnabled,
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.button_height_medium)),
        shape = buttonShape,
        contentPadding = PaddingValues(dimensionResource(R.dimen.padding_zero)),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = White,
            disabledContentColor = White,
            disabledContainerColor = Color.Transparent
        )
    ){
        Row(
            modifier = Modifier
                .fillMaxSize()
                .then(
                    if (isEnabled){
                        Modifier.background(
                            brush = Brush.verticalGradient(colors = gradientColor),
                            shape = buttonShape
                        )
                    } else {
                        Modifier.background(
                            color = Gray999,
                            shape = buttonShape
                        )
                    }
                )
                .clip(buttonShape),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = text,
                fontWeight = FontWeight.SemiBold,
                fontSize = dimensionResource(R.dimen.font_size_large).value.sp
            )
        }
    }
}