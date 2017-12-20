import javafx.application.Application
import javafx.scene.Parent
import javafx.stage.Stage
import javafx.fxml.FXMLLoader.load
import javafx.scene.Scene

/**
 * Created by denak on 15.12.2017.
 */
class Main : Application() {

    val layout = "sample.fxml"

    override fun start(primaryStage: Stage?) {
        primaryStage?.scene = Scene(load<Parent?>(Main::class.java.getResource(layout)))
        primaryStage?.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main::class.java)
        }
    }
}
