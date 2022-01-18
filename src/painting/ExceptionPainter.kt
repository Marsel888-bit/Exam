package painting

import Plane
import ui.painting.Painter
import java.awt.*

class ExceptionPainter(private val plane: Plane): Painter {


    val exception: String = "Format for t isn't correct!"
    override fun paint(g: Graphics){
        with (g as Graphics2D){
            stroke = BasicStroke(4F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
            val rh = mapOf(
                RenderingHints.KEY_ANTIALIASING to RenderingHints.VALUE_ANTIALIAS_ON,
                RenderingHints.KEY_INTERPOLATION to RenderingHints.VALUE_INTERPOLATION_BICUBIC,
                RenderingHints.KEY_RENDERING to RenderingHints.VALUE_RENDER_QUALITY,
                RenderingHints.KEY_DITHERING to RenderingHints.VALUE_DITHER_ENABLE
            )
            setRenderingHints(rh)
            with (plane) {
                drawString(exception, (plane.width / 2 * 1.5).toFloat(), (plane.height / 2 * 1.5).toFloat())
            }
        }
    }
}