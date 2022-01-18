package ui.painting

import java.awt.*
import java.awt.Graphics
import Plane

class FunctionPainter(private val plane: Plane
) : Painter {

        var function: (Double) -> Double = Math::sin
        var funColor: Color = Color.MAGENTA

        override fun paint(g: Graphics){
            with (g as Graphics2D){
                color = funColor
                stroke = BasicStroke(4F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
                val rh = mapOf(
                    RenderingHints.KEY_ANTIALIASING to RenderingHints.VALUE_ANTIALIAS_ON,
                    RenderingHints.KEY_INTERPOLATION to RenderingHints.VALUE_INTERPOLATION_BICUBIC,
                    RenderingHints.KEY_RENDERING to RenderingHints.VALUE_RENDER_QUALITY,
                    RenderingHints.KEY_DITHERING to RenderingHints.VALUE_DITHER_ENABLE
                )
                setRenderingHints(rh)
                with (plane) {
                    for (x in 0 until width){
                        drawLine(
                            x,
                            yCrt2Src(function(xSrc2Crt(x))),
                            x+1,
                            yCrt2Src(function(xSrc2Crt(x+1)))
                        )
                    }
                }
            }
        }
}