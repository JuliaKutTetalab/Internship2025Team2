package com.example.growbox.screen.home.chart.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.growbox.R
import com.example.growbox.screen.home.chart.model.ChartDataPoint
import com.example.growbox.screen.home.chart.model.ChartPeriod
import com.example.growbox.ui.theme.Gray999
import com.example.growbox.ui.theme.Green800
import com.example.growbox.ui.theme.GreenLight
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.chart.DefaultPointConnector
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.ShapeComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import java.time.LocalDate

@Composable
fun ChartGraph(
    modifier: Modifier = Modifier,
    unit: String,
    period: ChartPeriod = ChartPeriod.DAY,
    data: List<ChartDataPoint>
) {
    if (data.isEmpty()) {
        Box(
            modifier = Modifier.height(dimensionResource(R.dimen.height_huge)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.light_chart_no_data),
                color = Gray999
            )
        }
        return
    }

    Column(modifier = modifier) {

        TopValueLabels(
            data = data,
            unit = unit,
            period = period
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_small)))

        VicoLineChart(
            data = data,
            period = period,
            unit = unit
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_small)))

        when (period) {
            ChartPeriod.DAY -> DayAxisLabels(data)
            ChartPeriod.WEEK -> WeekAxisLabels(data)
            ChartPeriod.MONTH -> MonthAxisLabels(data)
        }
    }
}

@Composable
private fun TopValueLabels(
    data: List<ChartDataPoint>,
    unit: String,
    period: ChartPeriod
) {
    val textSize = dimensionResource(R.dimen.font_size_small).value.sp

    when (period) {
        ChartPeriod.DAY -> {
            val positions = listOf(0, 6, 12, 18, 23)
            Row(modifier = Modifier.fillMaxWidth()) {
                TopLabelText(text = topVal(data.getOrNull(positions[0]), unit), textSize)
                Spacer(Modifier.weight(6f))
                TopLabelText(text = topVal(data.getOrNull(positions[1]), unit), textSize)
                Spacer(Modifier.weight(6f))
                TopLabelText(text = topVal(data.getOrNull(positions[2]), unit), textSize)
                Spacer(Modifier.weight(6f))
                TopLabelText(text = topVal(data.getOrNull(positions[3]), unit), textSize)
                Spacer(Modifier.weight(5f))
                TopLabelText(text = topVal(data.getOrNull(positions[4]), unit), textSize)
            }
        }

        ChartPeriod.WEEK -> {
            Row(modifier = Modifier.fillMaxWidth()) {
                data.take(7).forEach { p ->
                    Text(
                        text = if (!p.isMissing) "${p.value.toInt()}$unit" else "",
                        fontSize = textSize,
                        color = Green800,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
            }
        }

        ChartPeriod.MONTH -> {
            val pos = listOf(0, 4, 9, 14, 19, 24, 29)
            Row(modifier = Modifier.fillMaxWidth()) {
                MonthTopLabel(data, pos[0], unit, textSize)
                Spacer(Modifier.weight(4f))
                MonthTopLabel(data, pos[1], unit, textSize)
                Spacer(Modifier.weight(5f))
                MonthTopLabel(data, pos[2], unit, textSize)
                Spacer(Modifier.weight(5f))
                MonthTopLabel(data, pos[3], unit, textSize)
                Spacer(Modifier.weight(5f))
                MonthTopLabel(data, pos[4], unit, textSize)
                Spacer(Modifier.weight(5f))
                MonthTopLabel(data, pos[5], unit, textSize)
                Spacer(Modifier.weight(4f))
                MonthTopLabel(data, pos[6], unit, textSize)
            }
        }
    }
}

@Composable
private fun TopLabelText(text: String, size: TextUnit) {
    Text(
        text = text,
        fontSize = size,
        color = Green800,
        maxLines = 1
    )
}

private fun topVal(p: ChartDataPoint?, unit: String): String {
    if (p == null || p.isMissing) return ""
    return "${p.value.toInt()}$unit"
}

@Composable
private fun MonthTopLabel(data: List<ChartDataPoint>, idx: Int, unit: String, size: TextUnit) {
    val p = data.getOrNull(idx)
    val text = if (p != null && !p.isMissing) "${p.value.toInt()}$unit" else ""
    TopLabelText(text = text, size = size)
}

@Composable
private fun VicoLineChart(
    data: List<ChartDataPoint>,
    period: ChartPeriod,
    unit: String,
) {
    val modelProducer = remember { ChartEntryModelProducer() }

    val axisOverrider = remember(unit, data) {
        when {
            unit.trim() == "%" -> AxisValuesOverrider.fixed(minY = 0f, maxY = 100f)
            unit.trim() == "°C" -> AxisValuesOverrider.fixed(minY = 0f, maxY = 50f)
            else -> {
                val maxVal = data.maxOfOrNull { it.value }?.let { (it * 1.2f).coerceAtLeast(10f) } ?: 100f
                AxisValuesOverrider.fixed(minY = 0f, maxY = maxVal)
            }
        }
    }

    val entries = remember(data, period) {
        val list = ArrayList<FloatEntry>(data.size)

        data.forEachIndexed { index, point ->
            val x = index.toFloat()
            val y = if (point.isMissing) 0f else point.value
            list.add(FloatEntry(x = x, y = y))
        }

        list.sortedBy { it.x }
    }

    val lineSpecs = remember {
        listOf(
            LineChart.LineSpec(
                lineColor = GreenLight.toArgb(),
                lineThicknessDp = 2f,
                point = ShapeComponent(
                    color = Color.White.toArgb(),
                    shape = Shapes.pillShape,
                    strokeColor = GreenLight.toArgb(),
                    strokeWidthDp = 3f,
                ),
                pointSizeDp = 10f,
                pointConnector = DefaultPointConnector(cubicStrength = 0f)
            )
        )
    }

    LaunchedEffect(entries, period) {
        modelProducer.setEntries(entries)
    }

    Chart(
        chart = lineChart(
            lines = lineSpecs,
            spacing = 0.dp,
            axisValuesOverrider = axisOverrider
        ),
        chartModelProducer = modelProducer,
        startAxis = null,
        bottomAxis = null,
        isZoomEnabled = true,
        runInitialAnimation = false,
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.height_large))
    )
}

@Composable
private fun MonthAxisLabels(data: List<ChartDataPoint>) {
    val size = dimensionResource(R.dimen.font_size_small).value.sp
    val pos = listOf(0, 4, 9, 14, 19, 24, 29)

    fun labelAt(i: Int): String {
        val iso = data.getOrNull(i)?.dataLabel ?: return ""
        if (iso.isBlank()) return ""
        return runCatching { LocalDate.parse(iso).dayOfMonth.toString() }.getOrElse { "" }
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        Text(labelAt(pos[0]), fontSize = size, color = Gray999)
        Spacer(Modifier.weight(4f))
        Text(labelAt(pos[1]), fontSize = size, color = Gray999)
        Spacer(Modifier.weight(5f))
        Text(labelAt(pos[2]), fontSize = size, color = Gray999)
        Spacer(Modifier.weight(5f))
        Text(labelAt(pos[3]), fontSize = size, color = Gray999)
        Spacer(Modifier.weight(5f))
        Text(labelAt(pos[4]), fontSize = size, color = Gray999)
        Spacer(Modifier.weight(5f))
        Text(labelAt(pos[5]), fontSize = size, color = Gray999)
        Spacer(Modifier.weight(4f))
        Text(labelAt(pos[6]), fontSize = size, color = Gray999)
    }
}

@Composable
private fun WeekAxisLabels(data: List<ChartDataPoint>) {
    val size = dimensionResource(R.dimen.font_size_small).value.sp

    val labels = data.take(7).map { it.dayLabel }.let { list ->
        if (list.all { it.isNotBlank() }) list
        else listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        labels.forEach { d ->
            Text(
                text = d,
                fontSize = size,
                color = Gray999,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun DayAxisLabels(data: List<ChartDataPoint>) {
    val size = dimensionResource(R.dimen.font_size_small).value.sp
    val positions = listOf(0, 6, 12, 18, 23)

    fun labelAt(idx: Int): String {
        val p = data.getOrNull(idx) ?: return ""
        return p.dayLabel
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        Text(labelAt(positions[0]), fontSize = size, color = Gray999)
        Spacer(Modifier.weight(6f))
        Text(labelAt(positions[1]), fontSize = size, color = Gray999)
        Spacer(Modifier.weight(6f))
        Text(labelAt(positions[2]), fontSize = size, color = Gray999)
        Spacer(Modifier.weight(6f))
        Text(labelAt(positions[3]), fontSize = size, color = Gray999)
        Spacer(Modifier.weight(5f))
        Text(labelAt(positions[4]), fontSize = size, color = Gray999)
    }
}
