package com.practicecoding.dentalai.general

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.practicecoding.dentalai.ui.theme.blue
import com.practicecoding.dentalai.ui.theme.lightBlue

object Gradient {
    val gradientMain = Brush.linearGradient(
        0.0f to Color.White,
        500.0f to blue,
        start = Offset.Zero,
        end = Offset.Infinite
    )
    val gradientCard = Brush.linearGradient(
        0.0f to Color.White,
        500.0f to lightBlue,
        start = Offset.Zero,
        end = Offset.Infinite
    )
}
//    Box(modifier = Modifier.background(gradient))

