/**
 * Created by denak on 13.12.2017.
 */
public class Triangle {
    public Point3D a, b, c;
    public Point3D normal_a, normal_b, normal_c;
    public Point2D texture_a, texture_b, texture_c;
    public Point3D center;

    public Triangle(Point3D a, Point3D b, Point3D c) {
        this.a = a;
        this.b = b;
        this.c = c;
        center = new Point3D(
                (a.x + b.x + c.x) / 3,
                (a.y + b.y + c.y) / 3,
                (a.z + b.z + c.z) / 3
        );
    }

    public Triangle(Point3D a, Point3D b, Point3D c, Point3D normal_a, Point3D normal_b, Point3D normal_c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.normal_a = normal_a;
        this.normal_b = normal_b;
        this.normal_c = normal_c;
        center = new Point3D(
                (a.x + b.x + c.x) / 3,
                (a.y + b.y + c.y) / 3,
                (a.z + b.z + c.z) / 3
        );
    }

    public Triangle(Point3D a, Point3D b, Point3D c, Point2D texture_a, Point2D texture_b, Point2D texture_c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.texture_a = texture_a;
        this.texture_b = texture_b;
        this.texture_c = texture_c;
        center = new Point3D(
                (a.x + b.x + c.x) / 3,
                (a.y + b.y + c.y) / 3,
                (a.z + b.z + c.z) / 3
        );

    }

    public Triangle(Point3D a, Point3D b, Point3D c, Point3D normal_a, Point3D normal_b, Point3D normal_c, Point2D texture_a, Point2D texture_b, Point2D texture_c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.normal_a = normal_a;
        this.normal_b = normal_b;
        this.normal_c = normal_c;
        this.texture_a = texture_a;
        this.texture_b = texture_b;
        this.texture_c = texture_c;
        center = new Point3D(
                (a.x + b.x + c.x) / 3,
                (a.y + b.y + c.y) / 3,
                (a.z + b.z + c.z) / 3
        );

    }
}
