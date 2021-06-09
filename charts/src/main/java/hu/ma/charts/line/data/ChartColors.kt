package hu.ma.charts.line.data

import androidx.compose.ui.graphics.Color

data class ChartColors(
  val axis: Color,
  val background: Color,
  val label: Color,
  val horizontalLines: Color,
) {

  companion object {
    fun defaultColors(
      axis: Color = Color.Black,
      background: Color = Color.Transparent,
      label: Color = Color(0xFF909BAA),
      horizontalLines: Color = Color(0xFF233F53)
    ): ChartColors = ChartColors(
      axis = axis,
      background = background,
      label = label,
      horizontalLines = horizontalLines
    )
  }

}
