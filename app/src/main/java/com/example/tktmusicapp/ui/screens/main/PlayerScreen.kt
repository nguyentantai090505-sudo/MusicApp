package com.example.tktmusicapp.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.tktmusicapp.ui.theme.GradientEnd
import com.example.tktmusicapp.ui.theme.GradientMiddle
import com.example.tktmusicapp.ui.theme.GradientStart
import com.example.tktmusicapp.viewmodel.PlayerViewModel
import java.util.concurrent.TimeUnit

@Composable
fun PlayerScreen(
    playerViewModel: PlayerViewModel,
    onNavigateBack: () -> Unit
) {
    val currentSong by playerViewModel.currentSong
    val isPlaying by playerViewModel.isPlaying
    val currentPosition by playerViewModel.currentPosition
    val totalDuration by playerViewModel.totalDuration
    var sliderPosition by remember { mutableStateOf(0f) }

    val gradient = Brush.verticalGradient(
        colors = listOf(GradientStart, GradientMiddle, GradientEnd)
    )

    fun formatDuration(millis: Long): String {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🔙 Back button
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 🖼️ Ảnh bài hát
        AsyncImage(
            model = currentSong?.albumArtUrl,
            contentDescription = "Album Art",
            modifier = Modifier
                .size(260.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 🎵 Tên bài hát
        Text(currentSong?.title ?: "No Song Selected", color = Color.White, fontSize = 22.sp)
        Text(currentSong?.artist ?: "Unknown Artist", color = Color.LightGray, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(32.dp))

        // ⏱️ Thanh tiến trình
        Column {
            Slider(
                value = if (totalDuration > 0) currentPosition.toFloat() / totalDuration else 0f,
                onValueChange = {
                    sliderPosition = it
                    playerViewModel.onSeek(it * totalDuration.toFloat())
                },
                onValueChangeFinished = {
                    playerViewModel.onSeekFinished((sliderPosition * totalDuration).toLong())
                },
                valueRange = 0f..1f,
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.White,
                    inactiveTrackColor = Color.Gray
                )
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = formatDuration(currentPosition), color = Color.White)
                Text(text = formatDuration(totalDuration), color = Color.White)
            }
        }


        Spacer(modifier = Modifier.height(32.dp))

        //  Nút điều khiển
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { playerViewModel.playPrevious() }) {
                Icon(Icons.Default.SkipPrevious, contentDescription = "Previous", tint = Color.White, modifier = Modifier.size(36.dp))
            }
            IconButton(onClick = { if (isPlaying) playerViewModel.pause() else playerViewModel.resume() }) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = "Play/Pause",
                    tint = Color.White,
                    modifier = Modifier.size(60.dp)
                )
            }
            IconButton(onClick = { playerViewModel.playNext() }) {
                Icon(Icons.Default.SkipNext, contentDescription = "Next", tint = Color.White, modifier = Modifier.size(36.dp))
            }
        }
    }
}
