package com.example.tktmusicapp.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun PlayerScreen(onNavigateBack: () -> Unit) {
    var isPlaying by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = onNavigateBack, modifier = Modifier.align(Alignment.Start)) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
        }

        Spacer(Modifier.height(32.dp))

        Image(
            painter = rememberAsyncImagePainter("https://i.scdn.co/image/ab67616d0000b273d74b6bdfc1b60a9d79b6b1f1"),
            contentDescription = null,
            modifier = Modifier.size(300.dp)
        )

        Spacer(Modifier.height(24.dp))

        Text("Song Title", color = Color.White, fontWeight = FontWeight.Bold)
        Text("Artist Name", color = Color.Gray)

        Spacer(Modifier.height(40.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                Icons.Default.SkipPrevious,
                contentDescription = "Previous",
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )
            IconButton(onClick = { isPlaying = !isPlaying }) {
                Icon(
                    if (isPlaying) Icons.Default.PauseCircle else Icons.Default.PlayCircle,
                    contentDescription = "Play/Pause",
                    tint = Color(0xFF6C63FF),
                    modifier = Modifier.size(80.dp)
                )
            }
            Icon(
                Icons.Default.SkipNext,
                contentDescription = "Next",
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}
