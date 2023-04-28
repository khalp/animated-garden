import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.common.MyGarden

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MyGarden()
    }
}
