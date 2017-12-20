/**
 * Created by denis on 16.12.2017.
 */
class Camera {

    val base = Ray(Point3D(0f, 0f, 0f), Point3D(0f, 0f, -1f))
    var focalDistance = 0f
    var width = 0f
    var height = 0f
    var resolutionWight = 0
    var resolutionHeight = 0
    private val topLeftCorner: Point3D
    private val topRightCorner: Point3D
    private val bottomLeftCorner: Point3D
    private val bottomRightCorner: Point3D
    private val deltaX: Point3D
    private val deltaY: Point3D

    constructor() {
        focalDistance = 1.0f
        width = 1.0f
        height = 0.5625f
        resolutionWight = 1280
        resolutionHeight = 720
        topLeftCorner = Point3D(-width / 2, height / 2, focalDistance)
        topRightCorner = Point3D(width / 2, height / 2, focalDistance)
        bottomLeftCorner = Point3D(-width / 2, -height / 2, focalDistance)
        bottomRightCorner = Point3D(width / 2, -height / 2, focalDistance)
        deltaX = topRightCorner - topLeftCorner
        deltaY = topLeftCorner - bottomLeftCorner
        deltaX /= resolutionWight.toFloat()
        deltaY /= resolutionHeight.toFloat()
    }

    fun rotate(x_degree: Float, y_degree: Float) {
        topLeftCorner -= base.base
        topRightCorner -= base.base
        bottomLeftCorner -= base.base
        bottomRightCorner -= base.base
        if (x_degree != 0f) {
            base.direction.rotateX(x_degree)
            topLeftCorner.rotateX(x_degree)
            topRightCorner.rotateX(x_degree)
            bottomLeftCorner.rotateX(x_degree)
            bottomRightCorner.rotateX(x_degree)
        }
        if (y_degree != 0f) {
            base.direction.rotateY(y_degree)
            topLeftCorner.rotateY(y_degree)
            topRightCorner.rotateY(y_degree)
            bottomLeftCorner.rotateY(y_degree)
            bottomRightCorner.rotateY(y_degree)
        }
        topLeftCorner += base.base
        topRightCorner += base.base
        bottomLeftCorner += base.base
        bottomRightCorner += base.base
        val newDeltaX = topRightCorner - topLeftCorner
        val newDeltaY = topLeftCorner - bottomLeftCorner
        deltaX[0] = newDeltaX[0]
        deltaX[1] = newDeltaX[1]
        deltaX[2] = newDeltaX[2]
        deltaY[0] = newDeltaY[0]
        deltaY[1] = newDeltaY[1]
        deltaY[2] = newDeltaY[2]
        deltaX /= resolutionWight.toFloat()
        deltaY /= resolutionHeight.toFloat()
    }

    operator fun get(i: Int, j: Int): Point3D{
        val point = Point3D(
                deltaX[0] * i - deltaY[0] * j,
                deltaX[1] * i - deltaY[1] * j,
                deltaX[2] * i - deltaY[2] * j
        )
        point += topLeftCorner
        point -= base.base
        point.normalize()
        return point
    }


}
