package com.example.common

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp

private val Brown = Color(0xFF742C0D)
private val Yellow = Color(0xFFF8D314)

@Composable
fun AnimatedSizeSunflower() {
    Sketch(
        modifier = Modifier.size(100.dp),
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, delayMillis = 50, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    ) { sizePct ->
        drawSunflower(sizePct = sizePct)
    }
}

@Composable
fun Sunflower() {
    Canvas(Modifier.size(100.dp)) {
        drawSunflower(sizePct = 1f)
    }
}

fun DrawScope.drawSunflower(sizePct: Float) {
    drawPetals(sizePct)
    drawCenter(sizePct)
}

fun DrawScope.drawCenter(sizePct: Float) {
    val radius = 20f * sizePct

    drawCircle(color = Brown, radius = radius, center = center)
}

fun DrawScope.drawPetals(sizePct: Float) {
    val numPetals = 8
    var angle = 0f
    val size = Size(width = 30f, height = 75f) * sizePct

    repeat(numPetals) {
        rotate(angle) {
            drawOval(
                color = Yellow,
                topLeft = Offset(center.x - size.width / 2, center.y),
                size = size
            )
        }
        angle += 360 / numPetals
    }
}

