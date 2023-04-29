package com.example.common

import androidx.compose.animation.core.*
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
fun AnimatedPetalColorSunflower() {
    Sketch(
        modifier = Modifier.size(100.dp),
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(durationMillis = 3000, easing = LinearEasing))
    ) { hue ->
        drawSunflower(hue = hue)
    }
}

@Composable
fun AnimatedColorSunflower() {
    Sketch(
        modifier = Modifier.size(100.dp),
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(durationMillis = 3000, easing = LinearEasing))
    ) { hue ->
        drawSunflower(color = Color.hsv(hue = hue, saturation = 1f, value = 1f))
    }
}

@Composable
fun AnimatedRotationSunflower() {
    Sketch(
        modifier = Modifier.size(100.dp),
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(durationMillis = 2000, easing = LinearEasing))
    ) { angle ->
        drawSunflower(rotation = angle)
    }
}

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

fun DrawScope.drawSunflower(sizePct: Float = 1f, rotation: Float = 0f, color: Color = Yellow, hue: Float? = null) {
    drawPetals(sizePct, rotation, color, hue)
    drawCenter(sizePct)
}

fun DrawScope.drawCenter(sizePct: Float) {
    val radius = 20f * sizePct

    drawCircle(color = Brown, radius = radius, center = center)
}

fun DrawScope.drawPetals(sizePct: Float, rotation: Float, color: Color, hue: Float?) {
    val numPetals = 8
    var angle = rotation
    val size = Size(width = 30f, height = 75f) * sizePct

    repeat(numPetals) { petal ->
        rotate(angle) {
            drawOval(
                color = hue?.let { Color.hsv(hue = (hue + petal * 10f).mod(360f), saturation = 1f, value = 1f) }
                    ?: color,
                topLeft = Offset(center.x - size.width / 2, center.y),
                size = size
            )
        }
        angle += 360 / numPetals
    }
}

