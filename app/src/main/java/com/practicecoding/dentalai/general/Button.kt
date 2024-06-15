package com.practicecoding.dentalai.general

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicecoding.dentalai.ui.theme.Crimson

@Composable
fun GeneralButton(
    text: String,
    width: Int,
    height: Int,
    roundnessPercent: Int = 30,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(width.dp)
            .height(height.dp)
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .background(color = Crimson, shape = RoundedCornerShape(percent = roundnessPercent)),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = text, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
    }
}

