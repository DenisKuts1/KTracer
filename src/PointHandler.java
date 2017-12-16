import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Created by denak on 13.12.2017.
 */
public class PointHandler {

    @NotNull
    public static Point2D addAndCreatePoint(Point2D a, Point2D b) {
        return new Point2D(
                a.u + b.u,
                a.v + b.v
        );
    }

    @NotNull
    public static Point3D addAndCreatePoint(Point3D a, Point3D b) {
        return new Point3D(
                a.x + b.x,
                a.y + b.y,
                a.z + b.z
        );
    }

    public static void addPoint(Point2D a, Point2D b) {
        a.u += b.u;
        a.v += b.v;
    }

    public static void addPoint(Point3D a, Point3D b) {
        a.x += b.x;
        a.y += b.y;
        a.z += b.z;
    }

    @NotNull
    public static Point2D subtractAndCreatePoint(Point2D a, Point2D b) {
        return new Point2D(
                a.u - b.u,
                a.v - b.v
        );
    }

    @NotNull
    public static Point3D subtractAndCreatePoint(Point3D a, Point3D b) {
        return new Point3D(
                a.x - b.x,
                a.y - b.y,
                a.z - b.z
        );
    }

    public static void subtractPoint(Point2D a, Point2D b) {
        a.u -= b.u;
        a.v -= b.v;
    }

    public static void subtractPoint(Point3D a, Point3D b) {
        a.x -= b.x;
        a.y -= b.y;
        a.z -= b.z;
    }

    @Contract(pure = true)
    public static float dotProduct(Point2D a, Point2D b) {
        return a.u * b.u + a.v * b.v;
    }

    @Contract(pure = true)
    public static float dotProduct(Point3D a, Point3D b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    @NotNull
    public static Point3D crossProduct(Point3D a, Point3D b) {
        return new Point3D(
                a.y * b.z - a.z * b.y,
                a.z * b.x - a.x * b.z,
                a.x * b.y - a.y * b.x
        );
    }

    public static float distance(Point2D a, Point2D b) {
        return (float) Math.sqrt(squareDistance(a, b));
    }

    public static float distance(Point3D a, Point3D b) {
        return (float) Math.sqrt(squareDistance(a, b));
    }

    @Contract(pure = true)
    public static float squareDistance(Point2D a, Point2D b) {
        float u = a.u - b.u;
        float v = a.v - b.v;
        return u * u + v * v;
    }

    @Contract(pure = true)
    public static float squareDistance(Point3D a, Point3D b) {
        float x = a.x - b.x;
        float y = a.y - b.y;
        float z = a.z - b.z;
        return x * x + y * y + z * z;
    }
}
