/**
 * Created by denak on 15.12.2017.
 */
object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val objReafer = ObjReader
        val obj = objReafer.load("Fidget_Spinner.obj")
        if (obj != null)
            for(triangle in obj)
                println(triangle["a", "x"])
    }
}
