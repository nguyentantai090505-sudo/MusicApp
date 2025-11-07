package com.example.tktmusicapp.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.tktmusicapp.ui.theme.PrimaryButton

@Composable
fun HomeScreen(
    onNavigateToSearch: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToPlayer: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        // 🔹 Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Good evening 🎵",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )
            IconButton(onClick = onNavigateToProfile) {
                Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.White)
            }
        }

        Spacer(Modifier.height(20.dp))

        // 🔹 Playlist Grid
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            PlaylistCard(
                title = "Daily Mix 1",
                imageUrl = "https://i.scdn.co/image/ab67616d0000b2734f3a59e4f2b4c02b66eb25c3",
                onClick = onNavigateToPlayer
            )
            PlaylistCard(
                title = "Top Hits",
                imageUrl = "https://i.scdn.co/image/ab67706f0000000326a70c252e51c2e6d8b8449b",
                onClick = onNavigateToPlayer
            )
        }

        Spacer(Modifier.height(30.dp))

        // 🔹 Search shortcut
        Button(
            onClick = onNavigateToSearch,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryButton)
        ) {
            Text("Search Music", color = Color.White)
        }
    }
}

@Composable
fun PlaylistCard(title: String, imageUrl: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(160.dp)
            .clickable { onClick() }
    ) {
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = null,
            modifier = Modifier.size(160.dp)
        )
        Text(title, color = Color.White, modifier = Modifier.padding(top = 8.dp))
    }
}
