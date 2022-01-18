package ui.painting

import Plane
import java.awt.*
import java.awt.Graphics



class CartesianPainter (private val plane: Plane) : Painter {

    var mainFont: Font = Font("Cambria", Font.BOLD, 16)
    var maxTickColor: Color = Color.RED

    override fun paint(g: Graphics) {
        with(plane) {
            (g as Graphics2D).apply {
                stroke = BasicStroke(3F)
                if ((xMin > 0) or (xMax < 0)) {
                    drawLine(width, 0, width, height)
                    drawLine(0, 0, 0, height)
                } else drawLine(xCrt2Src(0.0), 0, xCrt2Src(0.0), height)
                if ((yMin > 0) or (yMax < 0)) {
                    drawLine(0, height, width, height)
                    drawLine(0, 0, width, 0)
                } else drawLine(0, yCrt2Src(0.0), width, yCrt2Src(0.0))
                drawXLabels(g)
                drawYLabels(g)
            }
        }
    }


    private fun drawXLabels(g: Graphics) {
        with(plane) {
            with(g as Graphics2D) {
                stroke = BasicStroke(1F)
                color = maxTickColor
                font = mainFont
                var step = 10
                if (xMax - xMin < 1){
                    step = 1
                }
                if (xMax - xMin > 20){
                    step = 100
                }
                var tickValue = (xMin * 100).toInt()
                if (step == 100) {
                    tickValue = (xMin.toInt()) * 100
                }
                var t = tickValue.toDouble() / 100
                var size = 8
                while (t <= xMax) {
                    t = tickValue.toDouble() / 100
                    if (tickValue % (10 * step) == 0){
                        color = Color.BLUE
                        stroke = BasicStroke(2F)
                        size = 12
                    }
                    if (yMin > 0.0 || yMax < 0.0){
                        drawLine(xCrt2Src(t),-size, xCrt2Src(t), size)
                        drawLine(xCrt2Src(t), height-size, xCrt2Src(t), height+size)
                    } else drawLine(xCrt2Src(t), yCrt2Src(0.0) - size, xCrt2Src(t), yCrt2Src(0.0) + size)
                    color = maxTickColor
                    stroke = BasicStroke(1F)
                    size = 8
                    val (tW, tH) = with(fontMetrics.getStringBounds(t.toString(), g)) {
                        Pair(width.toInt(), height.toInt())
                    }
                    if (tickValue % (10 * step) == 0){
                        if (yMin > 0.0 || yMax < 0.0){
                            drawString(t.toString(), xCrt2Src(t) - tW / 2, size + tH)
                            drawString(t.toString(), xCrt2Src(t) - tW / 2, height - tH)
                        }
                        else  drawString(t.toString(), xCrt2Src(t) - tW / 2, yCrt2Src(0.0) + size + tH)
                    }
                    tickValue += step
                }
            }
        }
    }

    private fun drawYLabels(g: Graphics) {
        with(plane) {
            with(g as Graphics2D) {
                stroke = BasicStroke(1F)
                color = maxTickColor
                font = mainFont
                var step = 10
                if (yMax - yMin < 1){
                    step = 1
                }
                if (yMax - yMin > 20){
                    step = 100
                }
                var tickValue = (yMin * 10).toInt() * 10
                if (step == 100) tickValue = (yMin.toInt() * 100)
                var t = tickValue.toDouble() / 100
                var size = 8
                while (t <= yMax) {
                    t = tickValue.toDouble() / 100
                    if (tickValue % (10 * step) == 0){
                        color = Color.BLUE
                        stroke = BasicStroke(2F)
                        size = 12
                    }
                    if (xMin > 0.0 || xMax < 0.0) {
                        drawLine(-size,yCrt2Src(t),size,yCrt2Src(t))
                        drawLine(width - size, yCrt2Src(t), width + size, yCrt2Src(t))
                    } else drawLine(xCrt2Src(0.0) - size, yCrt2Src(t), xCrt2Src(0.0) + size, yCrt2Src(t))
                    color = maxTickColor
                    stroke = BasicStroke(1F)
                    size = 8
                    val (tW, tH) = with(fontMetrics.getStringBounds(t.toString(), g)) {
                        Pair(width.toInt(), height.toInt())
                    }
                    if (tickValue % (10 * step) == 0){
                        if (xMin > 0.0 || xMax < 0.0){
                            drawString(t.toString(), 10, yCrt2Src(t) + tH / 4)
                            drawString(t.toString(), width + tW / 2, yCrt2Src(t) + tH / 4)
                        }
                        else if (t != 0.0) {
                          drawString(t.toString(), xCrt2Src(0.0) + tW / 2, yCrt2Src(t) + tH / 4)
                        }
                    }
                    tickValue += step
                }
            }
        }
    }
}

