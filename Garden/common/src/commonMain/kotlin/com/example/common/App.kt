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
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.isActive

@Composable
fun MyGarden() {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Adaptive(100.dp)
    ) {
        items(8) {
            Sunflower()
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
