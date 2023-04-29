import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.common.MyGarden
import com.example.common.greenGradient
import org.jetbrains.skia.Data
import org.jetbrains.skia.RuntimeEffect
import java.nio.ByteBuffer
import java.nio.ByteOrder

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MyGarden { size, time ->
            // Source: https://www.pushing-pixels.org/2021/09/22/skia-shaders-in-compose-desktop.html
            val dataBuffer = ByteBuffer.allocate(12).order(ByteOrder.LITTLE_ENDIAN)

            // Pass in iResolution
            dataBuffer.putFloat(0, size.width)
            dataBuffer.putFloat(4, size.height)

            // Pass in iTime
            dataBuffer.putFloat(8, time.value)

            val shaderEffect = RuntimeEffect.makeForShader(greenGradient)
            val shader = shaderEffect.makeShader(
                uniforms = Data.makeFromBytes(dataBuffer.array()),
                children = null,
                localMatrix = null
            )

            ShaderBrush(shader)
        }
    }
}
