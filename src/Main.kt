/**
 * Created by denak on 15.12.2017.
 */
object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val objReafer = ObjReader
        val obj = objReafer.load("Fidget_Spinner1.obj")

        if (obj != null){
            val camera = Camera()
            val scene = Scene(camera)
            scene += obj
            val tree = KDTree(scene)
            val boxes = tree.getBoxes(Ray(Point3D(0f,0f,0f), Point3D(0f,0f,-1f)))
            println(boxes.size)
            val triangles = ArrayList<Triangle>()
            for(box in boxes)
                for(triangle in box.triangles)
                    if(triangle !in triangles)
                        triangles.add(triangle)
            println(triangles.size)
        }

    }
}
