package com.muhammadrafinovandi0108.sleepquality.chart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.muhammadrafinovandi0108.sleepquality.model.DataTidur
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.compose.cartesian.data.lineSeries
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart

@Composable
fun ComposeBasicLineChart(
    dataTidur: List<DataTidur>,
    modifier: Modifier = Modifier
) {

    val modelProducer = remember {
        CartesianChartModelProducer()
    }

    LaunchedEffect(dataTidur) {
        val series = dataTidur.map { it.durasi / 60f }
        modelProducer.runTransaction {
            lineSeries {
                series(series.ifEmpty { listOf(0f) })
            }
        }
    }
    val lineLayer = rememberLineCartesianLayer()
    val startAxis = VerticalAxis.rememberStart(
        valueFormatter = { _, value, _ ->
            val number = (value as? Number)?.toFloat() ?: 0f
            "${number.toInt()}h"
        }
    )
    CartesianChartHost(
        chart = rememberCartesianChart(
            lineLayer,
            startAxis = startAxis,
            bottomAxis = HorizontalAxis.rememberBottom()
        ),
        modelProducer = modelProducer,
        modifier = modifier
    )
}