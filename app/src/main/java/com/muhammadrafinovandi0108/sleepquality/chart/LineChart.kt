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
        val durasiTidur = if (dataTidur.isNotEmpty()) {
            dataTidur.map { item ->
                item.durasi / 60f
            }
        } else {
            listOf(0f)
        }
        modelProducer.runTransaction {
            lineSeries {
                series(durasiTidur)
            }
        }
    }
    val lineLayer = rememberLineCartesianLayer()
    val startAxis = VerticalAxis.rememberStart(
        valueFormatter = { _, value, _ ->
            "${value.toInt()}h"
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