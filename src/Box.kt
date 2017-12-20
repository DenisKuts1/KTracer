import com.sun.org.apache.xml.internal.dtm.ref.CoroutineManager

/**
 * Created by denis on 16.12.2017.
 */

class Box {

    val min: Point3D
    val max: Point3D
    val triangles = ArrayList<Triangle>()
    val center: Point3D = Point3D(0f,0f,0f)

    fun size() = triangles.size

    operator fun get(int: Int) = triangles[int]

    constructor() {
        min = Point3D(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
        max = Point3D(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY)
    }

    constructor(min: Point3D, max: Point3D){
        this.min = min
        this.max = max
        center[0] = (min[0] + max[0]) / 2
        center[1] = (min[1] + max[1]) / 2
        center[2] = (min[2] + max[2]) / 2
    }

    fun volume() = (max[0] - min[0]) * (max[1] - min[1]) * (max[2] - min[2])

    operator fun contains(triangle: Triangle): Boolean
    {
        for(i in 0..2) {
            if (max["x"] > triangle[i][0] && triangle[i][0] > min["x"])
                if (max["y"] > triangle[i][1] && triangle[i][1] > min["y"])
                    if (max["z"] > triangle[i][2] && triangle[i][2] > min["z"])
                        return true
        }
        return false
    }

    //operator fun contains(triangle: Triangle) = triangle in triangles

    operator fun plusAssign(triangle: Triangle){
        /*val x = center[0] * triangles.size
        val y = center[1] * triangles.size
        val z = center[2] * triangles.size*/

        triangles += triangle

        /*center[0] = (x + triangle.center[0]) / triangles.size
        center[1] = (y + triangle.center[1]) / triangles.size
        center[2] = (z + triangle.center[2]) / triangles.size*/

        for (t in 0..2)
            for (i in 0..2) {
                min[i] = if (triangle[t][i] < min[i]) triangle[t][i] else min[i]
                max[i] = if (triangle[t][i] > max[i]) triangle[t][i] else max[i]
            }
        center[0] = (min[0] + max[0]) / 2
        center[1] = (min[1] + max[1]) / 2
        center[2] = (min[2] + max[2]) / 2

    }

    operator fun plusAssign(triangles: ArrayList<Triangle>){
        /*var x = center[0] * this.triangles.size
        var y = center[1] * this.triangles.size
        var z = center[2] * this.triangles.size*/

        this.triangles += triangles

        /*for(triangle in triangles){
            for (t in 0..2) {
                /*for (i in 0..2) {
                    min[i] = if (triangle[t][i] < min[i]) triangle[t][i] else min[i]
                    max[i] = if (triangle[t][i] > max[i]) triangle[t][i] else max[i]
                }*/
            }
            x += triangle.center[0]
            y += triangle.center[1]
            z += triangle.center[2]

        }
        center[0] = x / this.triangles.size
        center[1] = y / this.triangles.size
        center[2] = z / this.triangles.size*/
        /*center[0] = (min[0] + max[0]) / 2
        center[1] = (min[1] + max[1]) / 2
        center[2] = (min[2] + max[2]) / 2*/
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
