package com.example.growbox.screen.settings.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.example.growbox.ui.theme.GreenLight
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.growbox.R

@Composable
fun FrequencyDropdown(
    selectedFrequency: String,
    onFrequencySelected: (String) -> Unit,
    modifier: Modifier = Modifier
){
    var expanded by remember {mutableStateOf(false)}
    val frequencies = listOf("Every day", "Every week", "Every month")

    Box(modifier = modifier){
        TextButton(
            onClick = {expanded = true}
        ) {
            Text(
                text = selectedFrequency,
                color = GreenLight,
                fontSize = dimensionResource(R.dimen.font_size_medium).value.sp
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = stringResource(R.string.dropdown_description),
                tint = GreenLight
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded = false}
        ) {
            frequencies.forEach { frequency ->
                DropdownMenuItem(
                    text = {Text(frequency)},
                    onClick = {
                        onFrequencySelected(frequency)
                        expanded = false
                    }
                )
            }
        }
    }
}