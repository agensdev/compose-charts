package hu.ma.charts.sample.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.ma.charts.line.LineChart
import hu.ma.charts.line.data.AxisLabel
import hu.ma.charts.line.data.DrawAxis
import hu.ma.charts.line.data.LineChartData

@Preview
@Composable
fun LineChartPreview() {
  LineChart(
    modifier = Modifier
      .size(400.dp, 300.dp)
      .background(Color(255, 255, 255))
      .padding(16.dp),
    data = LineChartData(
      lines = listOf(
        LineChartData.LineData(
          "Line 1",
          color = Color(0xFF66C194), gradientFill = true,
          points = listOf(
            LineChartData.LineData.LinePoint(0, 0f),
            LineChartData.LineData.LinePoint(1, 100f),
            LineChartData.LineData.LinePoint(2, 150f),
            LineChartData.LineData.LinePoint(3, 75f)
          )
        ),
        LineChartData.LineData(
          "Line 2", color = Color(0xFF427C9C), gradientFill = true, points = listOf(
            LineChartData.LineData.LinePoint(1, 50f),
            LineChartData.LineData.LinePoint(2, 20f),
            LineChartData.LineData.LinePoint(3, 80f),
            LineChartData.LineData.LinePoint(4, 120f),
            LineChartData.LineData.LinePoint(6, 90f),
          )
        )
      ),
      xLabels = listOf("2015", "2016", "2017", "2018", "2019", "2020", "2021"),
      yLabels = listOf(
        AxisLabel(0f, "0"),
        AxisLabel(50f, "50"),
        AxisLabel(100f, "100"),
        AxisLabel(150f, "150")
      ),
      horizontalLines = true,
      drawAxis = DrawAxis.None
    )
  )
}
