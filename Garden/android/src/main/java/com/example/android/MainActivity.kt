package com.example.android

import android.graphics.RuntimeShader
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.ShaderBrush
import com.example.common.MyGarden
import com.example.common.greenGradient

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MyGarden { size, animationState ->
                    val shader = RuntimeShader(greenGradient)
                    shader.setFloatUniform("iTime", animationState.value)
                    shader.setFloatUniform("iResolution", size.width, size.height)

                    ShaderBrush(shader)
                }
            }
        }
    }
}