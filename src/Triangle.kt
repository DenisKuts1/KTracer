/**
 * Created by denak on 13.12.2017.
 */
class Triangle {
    private val a: Point3D
    private val b: Point3D
    private val c: Point3D
    private var normalA: Point3D? = null
    private var normalB: Point3D? = null
    private var normalC: Point3D? = null
    private var textureA: Point2D? = null
    private var textureB: Point2D? = null
    private var textureC: Point2D? = null
    private val center: Point3D

    fun hasTextureCoords() = textureA != null && textureB != null && textureC != null

    fun hasNormalCoords() = normalA != null && normalB != null && normalC != null

    constructor(a: Point3D, b: Point3D, c: Point3D) {
        this.a = a
        this.b = b
        this.c = c
        center = Point3D(
                (a[0] + b[0] + c[0]) / 3,
                (a[1] + b[1] + c[1]) / 3,
                (a[2] + b[2] + c[2]) / 3)
    }

    constructor(a: Point3D, b: Point3D, c: Point3D, normal_a: Point3D, normal_b: Point3D, normal_c: Point3D) : this(a, b, c) {
        this.normalA = normal_a
        this.normalB = normal_b
        this.normalC = normal_c
    }

    constructor(a: Point3D, b: Point3D, c: Point3D, texture_a: Point2D, texture_b: Point2D, texture_c: Point2D) : this(a, b, c) {
        this.textureA = texture_a
        this.textureB = texture_b
        this.textureC = texture_c
    }

    constructor(a: Point3D, b: Point3D, c: Point3D, normal_a: Point3D, normal_b: Point3D, normal_c: Point3D,
                texture_a: Point2D, texture_b: Point2D, texture_c: Point2D) : this(a, b, c, normal_a, normal_b, normal_c) {
        this.textureA = texture_a
        this.textureB = texture_b
        this.textureC = texture_c
    }

    operator fun contains(point3D: Point3D) = when (point3D) {
        a -> true
        b -> true
        c -> true
        else -> false
    }

    operator fun get(int: Int) = when(int){
        0 -> a
        1 -> b
        2 -> c
        else -> center
    }

    operator fun get(string: String) = when(string){
        "a" -> a
        "b" -> b
        "c" -> c
        else -> center
    }

    operator fun get(string: String, coordinates: String) = when(string){
        "a" -> a[coordinates]
        "b" -> b[coordinates]
        "c" -> c[coordinates]
        else -> center[coordinates]
    }

    fun intersect(ray: Ray): Float{
        val e1 = b - a
        val e2 = c - a
        // Вычисление вектора нормали к плоскости
        val pvec = ray.direction % e2
        val det = e1 * pvec

        // Луч параллелен плоскости
        if (det < 1e-8 && det > -1e-8) {
            return 0f
        }

        val inv_det = 1 / det
        val tvec = ray.base - a
        val u = (tvec * pvec) * inv_det
        if (u < 0 || u > 1) {
            return 0f
        }

        val qvec = tvec % e1
        val v = (ray.direction * qvec) * inv_det;
        if (v < 0 || u + v > 1) {
            return 0f
        }
        return (e2 * qvec) * inv_det
    }
}
