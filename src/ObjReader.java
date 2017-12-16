import java.io.*;
import java.util.ArrayList;

/**
 * Created by denak on 13.12.2017.
 */
public class ObjReader {

    public static void save(Obj obj, String filename) {
        StringBuilder points = new StringBuilder();
        StringBuilder normals = new StringBuilder();
        StringBuilder textures = new StringBuilder();
        StringBuilder triangles = new StringBuilder();

        for (int i = 0, t = 1, n = 1; i < obj.triangles.size(); i++) {
            Triangle triangle = obj.triangles.get(i);
            String a = "v " + String.valueOf(triangle.a.x) + " " + String.valueOf(triangle.a.y) + " " + String.valueOf(triangle.a.z) + "\n";
            String b = "v " + String.valueOf(triangle.b.x) + " " + String.valueOf(triangle.b.y) + " " + String.valueOf(triangle.b.z) + "\n";
            String c = "v " + String.valueOf(triangle.c.x) + " " + String.valueOf(triangle.c.y) + " " + String.valueOf(triangle.c.z) + "\n";
            String na = "", nb = "", nc = "";
            String ta = "", tb = "", tc = "";
            boolean is_textures = false;
            boolean is_normals = false;
            if (triangle.normal_a != null) {
                is_normals = true;
                na = "vn " + String.valueOf(triangle.normal_a.x) + " " + String.valueOf(triangle.normal_a.y) + " " + String.valueOf(triangle.normal_a.z) + "\n";
                n++;
            }
            if (triangle.normal_b != null) {
                nb = "vn " + String.valueOf(triangle.normal_b.x) + " " + String.valueOf(triangle.normal_b.y) + " " + String.valueOf(triangle.normal_b.z) + "\n";
                n++;
            }
            if (triangle.normal_c != null) {
                nc = "vn " + String.valueOf(triangle.normal_c.x) + " " + String.valueOf(triangle.normal_c.y) + " " + String.valueOf(triangle.normal_c.z) + "\n";
                n++;
            }
            if (triangle.texture_a != null) {
                is_textures = true;
                ta = "vt " + String.valueOf(triangle.texture_a.u) + " " + String.valueOf(triangle.texture_a.v) + "\n";
                t++;
            }
            if (triangle.texture_b != null) {
                tb = "vt " + String.valueOf(triangle.texture_b.u) + " " + String.valueOf(triangle.texture_b.v) + "\n";
                t++;
            }
            if (triangle.texture_c != null) {
                tc = "vt " + String.valueOf(triangle.texture_c.u) + " " + String.valueOf(triangle.texture_c.v) + "\n";
                t++;
            }
            String tr;
            if (is_normals) {
                if (is_textures) {
                    tr = "f " + (i * 3 + 1) + "/" + (t - 2) + "/" + (n - 2) + " " + (i * 3 + 2) + "/" + (t - 1) + "/" + (n - 1) + " " + (i * 3 + 3) + "/" + t + "/" + n + "\n";
                } else {
                    tr = "f " + (i * 3 + 1) + "//" + (n - 2) + " " + (i * 3 + 2) + "//" + (n - 1) + " " + (i * 3 + 3) + "//" + n + "\n";
                }
            } else {
                if (is_textures) {
                    tr = "f " + (i * 3 + 1) + "/" + (t - 2) + " " + (i * 3 + 2) + "/" + (t - 1) + " " + (i * 3 + 3) + "/" + t + "\n";
                } else {
                    tr = "f " + (i * 3 + 1) + " " + (i * 3 + 2) + " " + (i * 3 + 3) + "\n";
                }
            }
            points.append(a).append(b).append(c);
            textures.append(ta).append(tb).append(tc);
            normals.append(na).append(nb).append(nc);
            triangles.append(tr);
        }
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(points.toString());
            writer.write(textures.toString());
            writer.write(normals.toString());
            writer.write(triangles.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Obj load(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            ArrayList<Point3D> points = new ArrayList<>();
            ArrayList<Point3D> normals = new ArrayList<>();
            ArrayList<Point2D> textures = new ArrayList<>();
            ArrayList<Triangle> triangles = new ArrayList<>();

            while (reader.ready()) {
                String line = reader.readLine();
                String elements[] = line.split(" ");
                if (elements.length == 4) {
                    elements[1] = elements[2];
                    elements[2] = elements[3];
                }
                if (elements.length == 5) {
                    elements[1] = elements[3];
                    elements[2] = elements[4];
                }
                switch (elements[0]) {
                    case "v": {
                        float x = Float.parseFloat(elements[1]);
                        float y = Float.parseFloat(elements[2]);
                        float z = Float.parseFloat(elements[3]);

                        points.add(new Point3D(x, y, z));
                        break;
                    }
                    case "vn": {
                        float x = Float.parseFloat(elements[1]);
                        float y = Float.parseFloat(elements[2]);
                        float z = Float.parseFloat(elements[3]);

                        normals.add(new Point3D(x, y, z));
                        break;
                    }
                    case "vt": {
                        float u = Float.parseFloat(elements[1]);
                        float v = Float.parseFloat(elements[2]);

                        textures.add(new Point2D(u, v));
                        break;
                    }
                    case "f": {
                        Point3D fPoints[] = new Point3D[3];
                        Point3D fNormals[] = new Point3D[3];
                        Point2D fTextures[] = new Point2D[3];

                        for (int i = 0; i < 3; i++) {
                            String[] coords = elements[i + 1].split("/");
                            fPoints[i] = points.get(Integer.parseInt(coords[0]) - 1);
                            switch (coords.length) {
                                case 3: {
                                    fNormals[i] = normals.get(Integer.parseInt(coords[2]) - 1);
                                    if (coords[1].equals("")) {
                                        break;
                                    }
                                }
                                case 2: {
                                    fTextures[i] = textures.get(Integer.parseInt(coords[1]) - 1);
                                }
                            }
                        }
                        Triangle triangle = new Triangle(
                                fPoints[0], fPoints[1], fPoints[2],
                                fNormals[0], fNormals[1], fNormals[2],
                                fTextures[0], fTextures[1], fTextures[2]);
                        triangles.add(triangle);
                    }
                }
            }
            return new Obj(triangles);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
