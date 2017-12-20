/**
 * Created by denak on 13.12.2017.
 */
class Obj {

    val triangles: ArrayList<Triangle>
    private var center: Point3D

    constructor(triangles: ArrayList<Triangle>) {
        this.triangles = triangles
        center = initCenter()

    }

    constructor(point3D: Point3D) {
        triangles = ArrayList()
        center = point3D
    }

    private fun initCenter(): Point3D {
        var x = 0f
        var y = 0f
        var z = 0f
        val n = triangles.size
        for (triangle in triangles) {
            x += triangle["center", "x"]
            y += triangle["center", "x"]
            z += triangle["center", "x"]
        }
        return Point3D(x / n,
                y / n,
                z / n)
    }

    operator fun plusAssign(triangle: Triangle) {
        val n = triangles.size
        val x = center["x"] * n + triangle["center", "x"]
        val y = center["y"] * n + triangle["center", "y"]
        val z = center["z"] * n + triangle["center", "z"]
        center["x"] = x / (n + 1)
        center["y"] = y / (n + 1)
        center["z"] = z / (n + 1)
        triangles += triangle
    }

    operator fun contains(triangle: Triangle) = triangle in triangles

    operator fun get(int: Int) = triangles[int]
    operator fun get(int: Int, point: String) = triangles[int][point]
    operator fun get(int: Int, point: String, coordinate: String) = triangles[int][point, coordinate]
}
