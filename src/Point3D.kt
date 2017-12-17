import org.jetbrains.annotations.Contract

/**
 * Created by denak on 13.12.2017.
 */
class Point3D : Iterator<Float> {

    override fun hasNext(): Boolean{
        if (current != 3) return true
        current = 0
        return false
    }

    override fun next(): Float {
        current++
        return this[current - 1]
    }

    private var id = 0
    private var x: Float
    private var y: Float
    private var z: Float
    private var current: Int

    operator fun Point3D.unaryMinus() = Point3D(-x, -y, -z)

    operator fun Point3D.plus(point3D: Point3D) = Point3D(x + point3D.x, y + point3D.y, z + point3D.z)

    operator fun Point3D.plusAssign(point3D: Point3D) {
        x += point3D.x
        y += point3D.y
        z += point3D.z
    }

    operator fun Point3D.minus(point3D: Point3D) = Point3D(x - point3D.x, y - point3D.y, z - point3D.z)

    operator fun Point3D.minusAssign(point3D: Point3D) {
        x -= point3D.x
        y -= point3D.y
        z -= point3D.z
    }

    operator fun div(float: Float) = Point3D(x / float, y / float, z / float)

    operator fun divAssign(float: Float) {
        x /= float
        y /= float
        z /= float
    }

    operator fun times(float: Float) = Point3D(x * float, y * float, z * float)

    operator fun times(point3D: Point3D) = x * point3D.x + y * point3D.y + z * point3D.z

    operator fun timesAssign(float: Float) {
        x *= float
        y *= float
        z *= float
    }

    operator fun not() = Point3D(1 / x, 1 / y, 1 / z)

    operator fun rem(point3D: Point3D) = Point3D(
            y * point3D.z - z * point3D.y,
            z * point3D.x - x * point3D.z,
            x * point3D.y - y * point3D.x)

    operator fun remAssign(point3D: Point3D) {
        val x = x
        val y = y
        val z = z
        this.x = y * point3D.z - z * point3D.y
        this.y = z * point3D.x - x * point3D.z
        this.z = x * point3D.y - y * point3D.x
    }

    override fun equals(other: Any?) = when (other) {
        null -> false
        !is Point3D -> false
        else -> x == other.x && y == other.y && z == other.z
    }

    operator fun get(int: Int) = when (int) {
        0 -> x
        1 -> y
        2 -> z
        else -> 0f
    }

    operator fun get(string: String) = when (string) {
        "x" -> x
        "y" -> y
        "z" -> z
        else -> 0f
    }

    operator fun set(int: Int, float: Float) {
        when (int) {
            0 -> x = float
            1 -> y = float
            2 -> z = float
        }
    }

    operator fun set(string: String, float: Float) {
        when (string) {
            "x" -> x = float
            "y" -> y = float
            "z" -> z = float
        }
    }

    constructor(x: Float, y: Float, z: Float) {
        this.x = x
        this.y = y
        this.z = z
        current = 0
    }

    constructor(x: Float, y: Float, z: Float, id: Int) : this(x, y, z) {
        this.id = id
    }

    fun squareDistance(b: Point3D): Float {
        val x = x - b.x
        val y = y - b.y
        val z = z - b.z
        return x * x + y * y + z * z
    }

    fun normalize() {
        val length = length()
        x /= length
        y /= length
        z /= length
    }


    fun rotateX(degree: Float) {
        val d = (degree * Math.PI / 180)
        val sinDegree = Math.sin(d).toFloat()
        val cosDegree = Math.cos(d).toFloat()
        val y = y
        val z = z
        this.y = y * cosDegree - z * sinDegree
        this.z = z * cosDegree + y * sinDegree
    }

    fun rotateY(degree: Float) {
        val d = (degree * Math.PI / 180)
        val sinDegree = Math.sin(d).toFloat()
        val cosDegree = Math.cos(d).toFloat()
        val x = x
        val z = z
        this.x = x * cosDegree - z * sinDegree
        this.z = z * cosDegree + x * sinDegree
    }


    fun length() = Math.sqrt((x * x + y * y + z * z).toDouble()).toFloat()
    fun distance(b: Point3D) = Math.sqrt(squareDistance(b).toDouble()).toFloat()
    fun print(a: Point3D) = a.x.toString() + " " + a.y + " " + a.z
}
