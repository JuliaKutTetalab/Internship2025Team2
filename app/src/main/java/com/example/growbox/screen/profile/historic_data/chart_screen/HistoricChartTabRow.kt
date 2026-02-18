package com.example.growbox.screen.profile.historic_data.chart_screen


import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import com.example.growbox.R
import com.example.growbox.screen.home.chart.model.ChartPeriod

import com.example.growbox.ui.theme.Gray999
import com.example.growbox.ui.theme.GreenLight

@Composable
fun HistoricChartTabRow(
    selectedPeriod: ChartPeriod,
    onPeriodSelected: (ChartPeriod) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = listOf(
        ChartPeriod.DAY to "Day",
        ChartPeriod.WEEK to "Week",
        ChartPeriod.MONTH to "Month"
    )

    TabRow(
        selectedTabIndex = tabs.indexOfFirst { it.first == selectedPeriod },
        containerColor = Color.White,
        contentColor = GreenLight,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                color = GreenLight,
                height = dimensionResource(R.dimen.height_tab_row_small),
                modifier = Modifier.tabIndicatorOffset(
                    tabPositions[tabs.indexOfFirst { it.first == selectedPeriod }]
                )
            )
        },
        modifier = modifier
    ) {
        tabs.forEach { (period, title) ->
            Tab(
                selected = selectedPeriod == period,
                onClick = { onPeriodSelected(period) },
                text = {
                    Text(
                        text = title,
                        fontWeight = if (selectedPeriod == period) FontWeight.SemiBold else FontWeight.Normal,
                        color = if (selectedPeriod == period) GreenLight else Gray999
                    )
                }
            )
        }
    }
}