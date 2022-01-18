package ui.painting

import Plane
import java.awt.*

class ImplicitPlotPainter (private val plane: Plane, var t_min: Double, var t_max: Double): Painter {
    var funColor: Color = Color.MAGENTA
    var functionX: (Double) -> Double = Math::sin
    var functionY: (Double) -> Double = Math::sin
    val steps = 10000

    override fun paint(g: Graphics) {
        with(g as Graphics2D) {
            color = funColor
            stroke = BasicStroke(2F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
            val rh = mapOf(
                RenderingHints.KEY_ANTIALIASING to RenderingHints.VALUE_ANTIALIAS_ON,
                RenderingHints.KEY_INTERPOLATION to RenderingHints.VALUE_INTERPOLATION_BICUBIC,
                RenderingHints.KEY_RENDERING to RenderingHints.VALUE_RENDER_QUALITY,
                RenderingHints.KEY_DITHERING to RenderingHints.VALUE_DITHER_ENABLE
            )
            setRenderingHints(rh)
            val step = (t_max - t_min) / steps
            with(plane) {
                for (i in 0 until steps) {
                    val t = t_min + i * step
                    drawLine(
                        xCrt2Src(functionX(t)),
                        yCrt2Src(functionY(t)),
                        xCrt2Src(functionX(t + step)),
                        yCrt2Src(functionY(t + step)),
                    )
                }
            }
        }
    }
}
