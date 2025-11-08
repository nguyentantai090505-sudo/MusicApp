package com.example.tktmusicapp.ui.components.common

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


fun Modifier.shimmer(): Modifier = composed {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_translate"
    )

    val brush = Brush.linearGradient(
        colors = listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.6f),
        ),
        start = androidx.compose.ui.geometry.Offset(translateAnim.value - 300f, 0f),
        end = androidx.compose.ui.geometry.Offset(translateAnim.value + 300f, 0f)
    )

    this.background(brush)
}


@Composable
fun SkeletonLoading() {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .size(120.dp)
            .clip(CircleShape)
            .shimmer()
    )
}