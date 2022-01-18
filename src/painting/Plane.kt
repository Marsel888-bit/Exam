import java.awt.Dimension
import java.lang.Integer.max

class Plane(
    xMin: Double,
    xMax: Double,
    yMin: Double,
    yMax: Double
) {
    var xMin: Double = 0.0
        private set
    var xMax: Double = 0.0
        private set
    var yMin: Double = 0.0
        private set
    var yMax: Double = 0.0
        private set

    var xSegment: Pair<Double, Double>
        get() = Pair(xMin, xMax)
        set(value) {
            val xk  = if (value.first == value.second) 0.1 else 0.0
            if (value.first <= value.second) {
                this.xMin = value.first - xk
                this.xMax = value.second + xk
            } else{
                this.xMin = value.first
                this.xMax = value.second
            }
        }

    var ySegment: Pair<Double, Double>
        get() = Pair(yMin, yMax)
        set(value) {
            val yk  = if (value.first == value.second) 0.1 else 0.0
            if (value.first <= value.second) {
                this.yMin = value.first - yk
                this.yMax = value.second + yk
            } else{
                this.yMin = value.first
                this.yMax = value.second
            }
        }

    init {
        xSegment = Pair(xMin, xMax)
        ySegment = Pair(yMin, yMax)
    }

    var pixelSize: Dimension = Dimension(1,1)
        set(size){
            field = Dimension(max(1,size.width),max(1,size.height))
        }

    private var xSize: Int
        get() = pixelSize.width
        set(w) {pixelSize.width = w}
    private var ySize: Int
        get() = pixelSize.height
        set(h) {pixelSize.height = h}

    var width: Int
        get() = xSize - 1
        set(value) {
            xSize = max(1, value)
        }
    var height: Int
        get() = ySize - 1
        set(value) {
            ySize = max(1, value)
        }

    val xDen: Double
        get() = width / (xMax - xMin)

    val yDen: Double
        get() = height / (yMax - yMin)

    fun xCrt2Src(x: Double) : Int{
        return ((-xMin + x) * xDen).toInt()
    }

    fun yCrt2Src(y: Double) : Int{
        return (yDen * (yMax - y)).toInt()
    }

    fun xSrc2Crt(x: Int) : Double{
        return xMin + x / xDen
    }

    fun ySrc2Crt(y: Int) : Double{
        return yMax - y / yDen
    }

}