/**
 * Created by denak on 13.12.2017.
 */
class Point2D {
    var id: Int = 0
    var u: Float = 0f
    var v: Float = 0f

    constructor(u: Float, v: Float) {
        this.u = u
        this.v = v
    }

    constructor(u: Float, v: Float, id: Int) {
        this.u = u
        this.v = v
        this.id = id
    }

    fun addPoint(b: Point2D) {
        u += b.u
        v += b.v
    }

    fun subtractPoint(b: Point2D) {
        u -= b.u
        v -= b.v
    }

    fun squareDistance(b: Point2D): Float {
        val u = u - b.u
        val v = v - b.v
        return u * u + v * v
    }

    fun dotProduct(b: Point2D) = u * b.u + v * b.v
    fun addAndCreatePoint(b: Point2D) = Point2D(u + b.u, v + b.v)
    fun subtractAndCreatePoint(b: Point2D) = Point2D(u - b.u, v - b.v)
    fun distance(b: Point2D) = Math.sqrt(squareDistance(b).toDouble()).toFloat()
    fun length(a: Point2D) = Math.sqrt((a.u * a.u + a.v * a.v).toDouble()).toFloat()
}
