import java.io.*

/**
 * Created by denak on 13.12.2017.
 */
object ObjReader {

    /*public static void save(Obj obj, String filename) {
        ArrayList<Point3D> points = new ArrayList<>();
        ArrayList<Point3D> normals = new ArrayList<>();
        ArrayList<Point2D> textures = new ArrayList<>();

        for (Triangle triangle : obj.triangles) {
            if (!points.contains(triangle.getA())) {
                points.add(triangle.getA());
            }
            if (!points.contains(triangle.getB())) {
                points.add(triangle.getB());
            }
            if (!points.contains(triangle.getC())) {
                points.add(triangle.getC());
            }
            if (triangle.hasNormalCoords()) {
                if (!normals.contains(triangle.getNormalA())) {
                    normals.add(triangle.getNormalA());
                }
                if (!normals.contains(triangle.getNormalB())) {
                    normals.add(triangle.getNormalB());
                }
                if (!normals.contains(triangle.getNormalC())) {
                    normals.add(triangle.getNormalC());
                }
            }
            if (triangle.hasTextureCoords()) {
                if (!textures.contains(triangle.getTextureA())) {
                    textures.add(triangle.getTextureA());
                }
                if (!textures.contains(triangle.getTextureB())) {
                    textures.add(triangle.getTextureB());
                }
                if (!textures.contains(triangle.getTextureC())) {
                    textures.add(triangle.getTextureC());
                }
            }
        }
        points.sort(Comparator.comparingInt(o -> o.getId()));
        normals.sort(Comparator.comparingInt(o -> o.getId()));
        textures.sort(Comparator.comparingInt(o -> o.getId()));
        StringBuilder string_points = new StringBuilder();
        StringBuilder string_normals = new StringBuilder();
        StringBuilder string_textures = new StringBuilder();
        StringBuilder string_triangles = new StringBuilder();

        for (Point3D point : points) {
            String p = "v " + String.valueOf(point.getX()) + " " +
                    String.valueOf(point.getY()) + " " +
                    String.valueOf(point.getZ()) + "\n";
            string_points.append(p);
        }

        for (Point3D point : normals) {
            String n = "vn " + String.valueOf(point.getX()) + " " +
                    String.valueOf(point.getY()) + " " +
                    String.valueOf(point.getZ()) + "\n";
            string_normals.append(n);
        }

        for (Point2D point : textures) {
            String t = "vt " + String.valueOf(point.getU()) + " " +
                    String.valueOf(point.getV()) + "\n";
            string_textures.append(t);
        }
        for (Triangle triangle : obj.triangles) {
            String tr;
            if (triangle.hasNormalCoords()) {
                if (triangle.hasTextureCoords()) {
                    tr = "f " + triangle.getA().getId() + "/" + triangle.getTextureA().getId() + "/" + triangle.getNormalA().getId() + " " +
                            triangle.getB().getId() + "/" + triangle.getTextureB().getId() + "/" + triangle.getNormalB().getId() + " " +
                            triangle.getC().getId() + "/" + triangle.getTextureC().getId() + "/" + triangle.getNormalC().getId() + "\n";
                } else {
                    tr = "f " + triangle.getA().getId() + "//" + triangle.getNormalA().getId() + " " +
                            triangle.getB().getId() + "//" + triangle.getNormalB().getId() + " " +
                            triangle.getC().getId() + "//" + triangle.getNormalC().getId() + "\n";
                }
            } else {
                if (triangle.hasTextureCoords()) {
                    tr = "f " + triangle.getA().getId() + "/" + triangle.getTextureA().getId() + " " +
                            triangle.getB().getId() + "/" + triangle.getTextureB().getId() + " " +
                            triangle.getC().getId() + "/" + triangle.getTextureC().getId() + "\n";
                } else {
                    tr = "f " + triangle.getA().getId() + " " +
                            triangle.getB().getId() + " " +
                            triangle.getC().getId() + "\n";
                }
            }
            string_triangles.append(tr);
        }

        try (FileWriter writer = new FileWriter(filename)) {
            //writer.write("# points\n");
            writer.write(string_points.toString());
            //writer.write("# textures\n");
            writer.write(string_textures.toString());
            //writer.write("# normals\n");
            writer.write(string_normals.toString());
            //writer.write("# triangles\n");
            writer.write(string_triangles.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    fun load(filename: String): Obj? {
        try {
            BufferedReader(FileReader(filename)).use { reader ->
                val points = ArrayList<Point3D>()
                val normals = ArrayList<Point3D>()
                val textures = ArrayList<Point2D>()
                val triangles = ArrayList<Triangle>()
                var p = 1
                var n = 1
                var t = 1

                while (reader.ready()) {
                    var line = reader.readLine()
                    line = line.replace(" {2}".toRegex(), " ")
                    val elements = line.split(" ".toRegex())
                    when (elements[0]) {
                        "v" -> {
                            val x = elements[1].toFloat()
                            val y = elements[2].toFloat()
                            val z = elements[3].toFloat()

                            points.add(Point3D(x, y, z, p))
                            p++
                        }
                        "vn" -> {
                            val x = elements[1].toFloat()
                            val y = elements[2].toFloat()
                            val z = elements[3].toFloat()

                            normals.add(Point3D(x, y, z, n))
                            n++
                        }
                        "vt" -> {
                            val u = elements[1].toFloat()
                            val v = elements[2].toFloat()

                            textures.add(Point2D(u, v, t))
                            t++
                        }
                        "f" -> {
                            val fPoints = ArrayList<Point3D>()
                            val fNormals = ArrayList<Point3D>()
                            val fTextures = ArrayList<Point2D>()
                            for (i in 0..2) {
                                val coords = elements[i + 1].split("/".toRegex())
                                fPoints += points[coords[0].toInt() - 1]
                                coords.size
                                when (coords.size) {
                                    3 -> {
                                        fNormals += normals[coords[2].toInt() - 1]
                                        if(coords[1].isNotEmpty()) fTextures += textures[coords[1].toInt() - 1]
                                    }
                                    2 -> fTextures += textures[coords[1].toInt() - 1]

                                }
                            }

                            triangles += if (fNormals.size != 0) if (fTextures.size != 0) Triangle(
                                    fPoints[0], fPoints[1], fPoints[2],
                                    fNormals[0], fNormals[1], fNormals[2],
                                    fTextures[0], fTextures[1], fTextures[2])
                            else Triangle(
                                    fPoints[0], fPoints[1], fPoints[2],
                                    fNormals[0], fNormals[1], fNormals[2])
                            else if (fTextures.size != 0) Triangle(
                                    fPoints[0], fPoints[1], fPoints[2],
                                    fTextures[0], fTextures[1], fTextures[2])
                            else Triangle(fPoints[0], fPoints[1], fPoints[2])


                        }
                    }
                }
                return Obj(triangles)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

    }
}
