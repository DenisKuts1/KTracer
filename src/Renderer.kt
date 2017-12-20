/**
 * Created by denis on 16.12.2017.
 */
class Renderer {
    val scene: Scene
    val tree: KDTree

    constructor(scene: Scene) {
        this.scene = scene
        tree = KDTree(scene)
    }

    fun render(): ArrayList<ArrayList<Int>> {
        val result = ArrayList<ArrayList<Int>>()
        for (i in 0..(scene.camera.resolutionWight - 1)) {
            result += ArrayList<Int>()
            iter@ for (j in 0..(scene.camera.resolutionHeight - 1)) {
                println(i * scene.camera.resolutionHeight + j)
                val ray = Ray(scene.camera.base.base, scene.camera[i,j])
                val boxes = tree.getBoxes(ray)
                val triangles = ArrayList<Triangle>()
                for(box in boxes)
                    for (triangle in box.triangles)
                        if(triangle !in triangles)
                            triangles += triangle
                var minDistance = Float.POSITIVE_INFINITY
                var nearestTriangle: Triangle? = null
                for (triangle in triangles){
                    val distance = triangle.intersect(ray)
                    if(distance == 0f) continue
                    if(distance < minDistance){
                        minDistance = distance
                        nearestTriangle = triangle
                    }
                }
                if(nearestTriangle == null){
                    result[i].add(0)
                    continue@iter
                }
                val intersectionPoint = ray.base + (ray.direction * minDistance)
                val direction = scene.lightSources[0].base - intersectionPoint
                direction.normalize()
                val lightRay = Ray(intersectionPoint, direction)
                val lightBoxes = tree.getBoxes(lightRay)
                if(lightBoxes.size > 0) {
                    val lightTriangles = ArrayList<Triangle>()
                    for (box in lightBoxes)
                        for(triangle in box.triangles)
                            if(triangle !in lightTriangles) lightTriangles += triangle
                    for (triangle in lightTriangles){
                        val distance = triangle.intersect(ray)
                        if(distance != 0f){
                            result[i].add(0)
                            continue@iter
                        }
                    }
                }
                result[i].add(16711680)
            }
        }
        return result
    }

}
