import java.util.ArrayList;

/**
 * Created by denak on 13.12.2017.
 */
public class Obj {
    public ArrayList<Triangle> triangles;
    public Point3D center;

    public Obj(ArrayList<Triangle> triangles) {
        this.triangles = triangles;
        calculateCenter();
    }

    private void calculateCenter() {
        float x = 0f, y = 0f, z = 0f;
        int n = triangles.size();
        for (Triangle triangle : triangles) {
            x += triangle.center.x;
            y += triangle.center.y;
            z += triangle.center.z;
        }

        if(center == null){

            center = new Point3D(
                    x / n,
                    y / n,
                    z / n
            );
        } else {
            center.x = x / n;
            center.y = y / n;
            center.z = z / n;
        }
    }

    private void addTriangle(Triangle triangle){
        int n = triangles.size();
        float x = center.x * n + triangle.center.x;
        float y = center.y * n + triangle.center.y;
        float z = center.z * n + triangle.center.z;
        center.x = x / (n + 1);
        center.y = y / (n + 1);
        center.z = z / (n + 1);
        triangles.add(triangle);

    }
}
