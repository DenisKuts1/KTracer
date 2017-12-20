/**
 * Created by denis on 16.12.2017.
 */
class Scene{
    var camera: Camera
    val objects: ArrayList<Obj> = ArrayList()
    val lightSources: ArrayList<LightSource> = ArrayList()

    constructor(camera: Camera) {
        this.camera = camera
    }

    operator fun plusAssign(obj: Obj) {
        objects += obj
    }

    operator fun plusAssign(lightSource: LightSource) {
        lightSources += lightSource
    }

    operator fun get(int: Int) = objects[int]



}
