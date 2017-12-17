/**
 * Created by denis on 16.12.2017.
 */
class Camera {

    var base: Ray = Ray(Point3D(0f, 0f, 0f), Point3D(0f, 0f, 1f))
    var focal_distance: Float = 0.toFloat()
    var width: Float = 0.toFloat()
    var height: Float = 0.toFloat()
    var resolution_wight: Int = 0
    var resolution_height: Int = 0
    /*private val top_left_corner: Point3D
    private val top_right_corner: Point3D
    private val bottom_left_corner: Point3D
    private val bottom_right_corner: Point3D*/
    private var delta_x: Point3D? = null
    private var delta_y: Point3D? = null

    /*fun rotate(x_degree: Float, y_degree: Float) {
        top_left_corner.subtractPoint(base.base)
        top_right_corner.subtractPoint(base.base)
        bottom_left_corner.subtractPoint(base.base)
        bottom_right_corner.subtractPoint(base.base)
        if (x_degree != 0f) {
            base.direction.rotateX(x_degree)
            top_left_corner.rotateX(x_degree)
            top_right_corner.rotateX(x_degree)
            bottom_left_corner.rotateX(x_degree)
            bottom_right_corner.rotateX(x_degree)
        }
        if (y_degree != 0f) {
            base.direction.rotateY(y_degree)
            top_left_corner.rotateY(y_degree)
            top_right_corner.rotateY(y_degree)
            bottom_left_corner.rotateY(y_degree)
            bottom_right_corner.rotateY(y_degree)
        }
        top_left_corner.addPoint(base.base)
        top_right_corner.addPoint(base.base)
        bottom_left_corner.addPoint(base.base)
        bottom_right_corner.addPoint(base.base)
        delta_x = top_right_corner.subtractAndCreatePoint(top_left_corner)
        delta_y = top_left_corner.subtractAndCreatePoint(bottom_left_corner)
        delta_x!!.divide(resolution_wight.toFloat())
        delta_y!!.divide(resolution_height.toFloat())
    }*/

    /*fun getPixel(i: Int, j: Int): Point3D {
        val point = Point3D(
                delta_x!!.x * i - delta_y!!.x * j,
                delta_x!!.y * i - delta_y!!.y * j,
                delta_x!!.z * i - delta_y!!.z * j
        )
        point.addPoint(top_left_corner)
        return point
    }*/

    /*init {
        focal_distance = 1.0f
        width = 1.0f
        height = 0.5625f
        resolution_wight = 1280
        resolution_height = 720
        top_left_corner = Point3D(
                -width / 2,
                height / 2,
                focal_distance
        )
        top_right_corner = Point3D(
                width / 2,
                height / 2,
                focal_distance
        )
        bottom_left_corner = Point3D(
                -width / 2,
                -height / 2,
                focal_distance
        )
        bottom_right_corner = Point3D(
                width / 2,
                -height / 2,
                focal_distance
        )
        delta_x = top_right_corner.subtractAndCreatePoint(top_left_corner)
        delta_y = top_left_corner.subtractAndCreatePoint(bottom_left_corner)
        delta_x!!.divide(resolution_wight.toFloat())
        delta_y!!.divide(resolution_height.toFloat())
    }*/


}
