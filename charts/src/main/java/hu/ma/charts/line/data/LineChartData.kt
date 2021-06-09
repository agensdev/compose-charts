package hu.ma.charts.line.data

import android.graphics.Typeface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class LineChartData(
  val lines: List<LineData>,
  val xLabels: List<String> = listOf(),
  val yLabels: List<AxisLabel> = listOf(),
  val chartColors: ChartColors = ChartColors.defaultColors(),
  val horizontalLines: Boolean = false,
  val drawAxis: DrawAxis = DrawAxis.Both,
  val axisWidth: Float = 2f,
  val axisTextSize: TextUnit = 10.sp,
  val axisTypeface: Typeface = Typeface.DEFAULT,
  val axisLabelPadding: Dp = 10.dp,
) {


  data class LineData(
    val title: String,
    val points: List<LinePoint>,
    val color: Color,
    val gradientFill: Boolean = false,
  ) {
    data class LinePoint(val x: Int, val value: Float, val label: String? = null)
  }
}
