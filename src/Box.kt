/**
 * Created by denis on 16.12.2017.
 */

class Box {

    private var min: Point3D = Point3D(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE)
    private var max: Point3D = Point3D(Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE)
    private val triangles = ArrayList<Triangle>()

    operator fun contains(triangle: Triangle) = triangle in triangles

    operator fun plusAssign(triangle: Triangle){
        triangles += triangle
        for (t in triangle)
            for (i in (0..2)) {
                min[i] = if (t[i] < min[i]) t[i] else min[i]
                max[i] = if (t[i] > max[i]) t[i] else max[i]
            }
    }

    fun intersect(ray: Ray, t0: Float, t1: Float): Boolean {
        val invertDirection = !ray.direction
        var tMin: Float
        var tMax: Float
        val tYMin: Float
        val tYMax: Float
        val tZMin: Float
        val tZMax: Float

        if (invertDirection[0] < 0) {
            tMin = (max[0] - ray.base[0]) * invertDirection[0]
            tMax = (min[0] - ray.base[0]) * invertDirection[0]
        } else {
            tMin = (min[0] - ray.base[0]) * invertDirection[0]
            tMax = (max[0] - ray.base[0]) * invertDirection[0]
        }
        if (invertDirection[1] < 0) {
            tYMin = (max[1] - ray.base[1]) * invertDirection[1]
            tYMax = (min[1] - ray.base[1]) * invertDirection[1]
        } else {
            tYMin = (min[1] - ray.base[1]) * invertDirection[1]
            tYMax = (max[1] - ray.base[1]) * invertDirection[1]
        }

        if (tMin > tYMax || tYMin > tMax) return false
        tMin = if (tYMin > tMin) tYMin else tMin
        tMax = if (tYMax < tMax) tYMax else tMax

        if (invertDirection[2] < 0) {
            tZMin = (max[2] - ray.base[2]) * invertDirection[2]
            tZMax = (min[2] - ray.base[2]) * invertDirection[2]
        } else {
            tZMin = (min[2] - ray.base[2]) * invertDirection[2]
            tZMax = (max[2] - ray.base[2]) * invertDirection[2]
        }

        if (tMin > tZMax || tZMin > tMax) return false
        tMin = if (tZMin > tMin) tZMin else tMin
        tMax = if (tZMax < tMax) tZMax else tMax

        return (tMin < t1) && (tMax > t0)
    }
}
