import javafx.fxml.FXML
import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color

/**
 * Created by denak on 20.12.2017.
 */
class Controller {

    @FXML
    lateinit var canvas: Canvas

    fun initialize() {
        val camera = Camera()
        val scene = Scene(camera)
        val obj = ObjReader.load("Fidget_Spinner1.obj") ?: return
        val light = LightSource(Point3D(1f, 5f, -1f))
        scene += obj
        scene += light
        val renderer = Renderer(scene)
        val res = renderer.render()
        val gr = canvas.graphicsContext2D
        for (i in 0..1279)
            for (j in 0..719) {
                println(i * scene.camera.resolutionHeight + j)
                val r = (res[i][j] / 65536) / 256.0
                val g = ((res[i][j] / 256) % 256) / 256.0
                val b = (res[i][j] % 256) / 256.0
                gr.stroke = Color(r, g, b, 1.0)
                gr.fill = Color(r, g, b, 1.0)
                gr.fillRect(i.toDouble(), j.toDouble(), 0.0, 0.0)
            }
    }

}