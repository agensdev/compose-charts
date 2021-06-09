package hu.ma.charts.line

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import hu.ma.charts.line.data.DrawAxis
import hu.ma.charts.line.data.LineChartData

@Composable
fun LineChart(modifier: Modifier = Modifier, data: LineChartData) {
  Canvas(modifier = modifier.background(data.chartColors.background), onDraw = {
    val maxXValues = data.lines.maxOf { it.points.maxOf { point -> point.x } }
    val maxYValue = data.lines.maxOf { it.points.maxOf { point -> point.value } }

    val heightOfXAxisLabels = 40f
    val chartAreaBottom =
      if (data.xLabels.isNotEmpty()) this.size.height - heightOfXAxisLabels else this.size.height

    val xaxisLabelOrigin = this.size.height
    val xinterval = this.size.width / maxXValues
    val ynormalization = maxYValue / chartAreaBottom

    val nativeCanvas = drawContext.canvas.nativeCanvas

    data.lines.forEach {

      data.yLabels.forEach { ylabel ->
        val y = chartAreaBottom - ylabel.atValue / ynormalization
        if (data.horizontalLines) {
          drawLine(
            data.chartColors.horizontalLines,
            strokeWidth = 1f,
            start = Offset(0f, y),
            end = Offset(
              maxXValues * xinterval, y
            ),
          )
        }
      }

      if (it.gradientFill) {
        val gradientPathBuffer = Path()
        val gradient = Brush.verticalGradient(
          colors = listOf(it.color, Color.Transparent)
        )

        val firstPoint = it.points.first()

        gradientPathBuffer.moveTo(
          firstPoint.x * xinterval,
          chartAreaBottom
        )
        gradientPathBuffer.lineTo(
          firstPoint.x * xinterval,
          chartAreaBottom - firstPoint.value / ynormalization
        )

        it.points.subList(1, it.points.size).forEach { point ->
          gradientPathBuffer.lineTo(
            point.x * xinterval,
            chartAreaBottom - point.value / ynormalization
          )
        }

        gradientPathBuffer.lineTo(it.points.last().x * xinterval, chartAreaBottom)
        gradientPathBuffer.lineTo(firstPoint.x * xinterval, chartAreaBottom)
        drawPath(gradientPathBuffer, gradient)

      }

      it.points.forEachIndexed { index, point ->
        if (index > 0) {

          val previous = it.points[index - 1]


          drawLine(
            color = it.color, start = Offset(
              xinterval * previous.x,
              chartAreaBottom - previous.value / ynormalization
            ),
            end = Offset(
              xinterval * point.x,
              chartAreaBottom - point.value / ynormalization
            ), strokeWidth = 4f
          )

        }
      }

      if (data.drawAxis == DrawAxis.Y || data.drawAxis == DrawAxis.Both) {
        drawLine(
          data.chartColors.axis,
          strokeWidth = data.axisWidth,
          start = Offset(0f, chartAreaBottom),
          end = Offset(0f, 0f)
        )
      }

      if(data.drawAxis == DrawAxis.X || data.drawAxis == DrawAxis.Both) {
        drawLine(
          data.chartColors.axis,
          strokeWidth = data.axisWidth,
          start = Offset(0f, chartAreaBottom),
          end = Offset(maxXValues * xinterval, chartAreaBottom)
        )
      }

      val axisLabelPaint = Paint().apply {
        textSize = data.axisTextSize.toPx()
        color = data.chartColors.label.toArgb()
        typeface = data.axisTypeface
        isAntiAlias = true
      }


      data.xLabels.forEachIndexed { index, xlabel ->
        val textWidth = axisLabelPaint.measureText(xlabel)
        val x = when (index) {
          0 -> 0f
          data.xLabels.size - 1 -> maxXValues * xinterval - textWidth
          else -> index * xinterval - textWidth / 2f
        }

        nativeCanvas.drawText(
          xlabel,
          x,
          xaxisLabelOrigin,
          axisLabelPaint
        )
      }


      data.yLabels.forEach { ylabel ->
        val y = chartAreaBottom - ylabel.atValue / ynormalization
        nativeCanvas.drawText(
          ylabel.label,
          0f,
          y - data.axisLabelPadding.value,
          axisLabelPaint
        )
      }
    }
  })
}



