package com.example.common

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.isActive


// Source: https://github.com/drinkthestars/composable-sheep-sketches/blob/main/sheepSketches/src/main/java/trnt/sheepsketches/screens/GradientShaderScreen.kt#L82
val greenGradient = """
        uniform float2 iResolution;
        uniform float iTime;
        
        vec4 main(in float2 fragCoord) {
            // Normalized pixel coordinates (from 0 to 1)
            vec2 uv = iTime/iResolution.xy;
    
            // Time varying pixel color
            float r = 0.3 + 0.5*sin(iTime*2.0+uv.x*3.0);
            float g = 0.9 + 0.1*cos(iTime*2.0+uv.x*3.0);
            float b = 0.3 + 0.4*cos(iTime*2.0+uv.x*3.0);
    
            // Output to screen
            return vec4(r, g, b, 1.0);
        }
    """

// Source: https://twitter.com/notargs/status/1250468645030858753
val complexGreen = """
        uniform float2 iResolution;
        uniform float iTime;
        
        float f(vec3 p) {
            p.z -= iTime * 10.;
            float a = p.z * .1;
            p.xy *= mat2(cos(a), sin(a), -sin(a), cos(a));
            return .1 - length(cos(p.xy) + sin(p.yz));
        }
        
        half4 main(vec2 fragcoord) { 
            vec3 d = .5 - fragcoord.xy1 / iResolution.y;
            vec3 p=vec3(0);
            for (int i = 0; i < 32; i++) {
              p += f(p) * d;
            }
            return ((sin(p) + vec3(0.95, 3.725, 2.05)) / length(p)).xyz1;
        }
        """

@Composable
fun MyGarden(backgroundBrush: ((Size, AnimationState<Float, AnimationVector1D>) -> ShaderBrush)? = null) {
    val backgroundBrushTime = remember { AnimationState(0f) }

    LaunchedEffect(true) {
        while (isActive) {
            backgroundBrushTime.animateTo(
                targetValue = 2 * Math.PI.toFloat(),
                animationSpec = infiniteRepeatable(tween(durationMillis = 7000, easing = LinearEasing)),
                sequentialAnimation = true
            )
        }
    }

    val backgroundShaderModifier = backgroundBrush?.let {
        Modifier.drawBehind { drawRect(brush = backgroundBrush(size, backgroundBrushTime), size = size) }
    } ?: Modifier

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize().then(backgroundShaderModifier),
        columns = GridCells.Adaptive(100.dp)
    ) {
        repeat(8) {
            item { Sunflower() }
            item { AnimatedSizeSunflower() }
            item { AnimatedRotationSunflower() }
            item { AnimatedColorSunflower() }
            item { AnimatedPetalColorSunflower() }
            item { AnimatedPetalColorAndRotationSunflower() }
            item { AnimatedEverythingSunflower() }
        }
    }
}

// Simplified version of: https://github.com/drinkthestars/composable-sheep-sketches/blob/main/canvasSketch/src/main/java/com/canvas/sketch/Sketch.kt#L157
@Composable
fun Sketch(
    modifier: Modifier = Modifier,
    targetValue: Float,
    animationSpec: AnimationSpec<Float>,
    onDraw: DrawScope.(Float) -> Unit
) {
    val animationState = remember { AnimationState(0f) }

    LaunchedEffect(targetValue) {
        while (isActive) {
            animationState.animateTo(
                targetValue = targetValue,
                animationSpec = animationSpec,
                sequentialAnimation = true
            )
        }
    }

    Canvas(modifier = modifier) {
        onDraw(animationState.value)
    }
}
