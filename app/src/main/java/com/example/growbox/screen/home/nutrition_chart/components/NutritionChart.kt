package com.example.growbox.screen.home.nutrition_chart.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.growbox.R
import com.example.growbox.screen.home.nutrition_chart.model.ChartDataPoint
import com.example.growbox.ui.theme.Gray999
import com.example.growbox.ui.theme.Green800
import com.example.growbox.ui.theme.GreenLight
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollState
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.ShapeComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders


@Composable
fun NutritionChart(
    modifier: Modifier = Modifier,
    data: List<ChartDataPoint>
) {
    //Дані відсутні - показує Placeholder
    if (data.isEmpty()){
        Box(
            modifier = Modifier.height(dimensionResource(R.dimen.height_huge)),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = stringResource(R.string.nutrition_chart_no_data),
                color = Gray999
            )
        }
        return
    }

    Column (modifier = modifier){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            data.forEach { point ->
                Text (
                    text = "${point.value.toInt()}mg",
                    fontSize = dimensionResource(R.dimen.font_size_small).value.sp,
                    color = Green800,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_small)))

        VicoLineChart(data = data)

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_small)))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            data.forEach { point ->
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        text = point.dayLabel,
                        fontSize = dimensionResource(R.dimen.font_size_small).value.sp,
                        color = Gray999
                    )
                    Text(
                        text = point.dataLabel,
                        fontSize = dimensionResource(R.dimen.font_size_small).value.sp,
                        color = Gray999
                    )
                }
            }
        }
    }
}

@Composable
private fun VicoLineChart(
    data: List<ChartDataPoint>
){
    val modelProducer = remember { ChartEntryModelProducer() }

    val dataSetForModel = remember(data) {
        mutableStateListOf<FloatEntry>().apply{
            data.forEachIndexed { index, point ->
                add(FloatEntry(x = index.toFloat(), y = point.value))
            }
        }
    }

    val dataSetLineSpec = remember {
        listOf(
            LineChart.LineSpec(
                lineColor = GreenLight.toArgb(),
                lineThicknessDp = 3f, //Товщина ліній
                point = ShapeComponent(
                    shape = Shapes.pillShape,  // Кругла форма
                    color = GreenLight.toArgb()
                ),
                pointSizeDp = 8f , // Розмір точки
                lineBackgroundShader = DynamicShaders.fromBrush(
                    brush = Brush.verticalGradient(
                        listOf(
                            GreenLight.copy(alpha = 0.3f),
                            GreenLight.copy(alpha = 0f)
                        )
                    )
                )
            )
        )
    }
    val chartStateScroll = rememberChartScrollState()

    //Оновлення графіка
    LaunchedEffect(dataSetForModel) {
        if (dataSetForModel.isNotEmpty()){
            modelProducer.setEntries(dataSetForModel)
        }
    }

    val bottomAxisValueElement = AxisValueFormatter<AxisPosition.Horizontal.Bottom>{value, _ ->
        data.getOrNull(value.toInt())?.dayLabel ?: ""
    }

    if (dataSetForModel.isNotEmpty()){
        Chart(
            chart = lineChart(
                lines = dataSetLineSpec),
            chartModelProducer = modelProducer,
            startAxis = null,
            bottomAxis = rememberBottomAxis(
                valueFormatter = bottomAxisValueElement,
                guideline = null, //щоб не було вертикальних ліній
                label = null
            ),
            chartScrollState = chartStateScroll,
            isZoomEnabled = true,
            runInitialAnimation = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.height_large))
        )
    }
}