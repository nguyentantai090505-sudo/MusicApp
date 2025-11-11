package com.example.tktmusicapp.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.tktmusicapp.data.model.demoSongs
import com.example.tktmusicapp.data.model.spotify.TrackItem
import com.example.tktmusicapp.domain.model.Song
import com.example.tktmusicapp.ui.theme.*
import com.example.tktmusicapp.viewmodel.HomeViewModel
import com.example.tktmusicapp.viewmodel.PlayerViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    playerViewModel: PlayerViewModel = hiltViewModel(),
    onNavigateToSearch: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToLibrary: () -> Unit,
    onNavigateToPlayer: () -> Unit
) {
    val newReleases by homeViewModel.newReleases.collectAsState()
    val artistTopTracks by homeViewModel.artistTopTracks.collectAsState()
    val isLoading by homeViewModel.isLoading.collectAsState()
    val error by homeViewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.loadHomeData()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(GradientStart, GradientMiddle, GradientEnd)
                )
            )
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Greeting Header
        Text(
            text = "Hello 🎵",
            color = TextPrimary,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Search Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1E1E1E), shape = RoundedCornerShape(12.dp))
                .clickable { onNavigateToSearch() }
                .padding(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color.White.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Search for songs, artists...",
                    color = TextHint,
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 🔥 New Releases Section
        if (newReleases.isNotEmpty()) {
            Text(
                text = "New Releases",
                color = TextPrimary,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(10.dp))

            LazyRow(
                modifier = Modifier.height(180.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(newReleases.take(10)) { album ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .width(120.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF1E1E1E))
                            .clickable { onNavigateToPlayer() }
                            .padding(8.dp)
                    ) {
                        AsyncImage(
                            model = album.images.firstOrNull()?.url ?: "https://via.placeholder.com/640",
                            contentDescription = album.name,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = album.name,
                            color = TextPrimary,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 2,
                            modifier = Modifier.height(40.dp)
                        )
                        Text(
                            text = album.artists.joinToString { it.name },
                            color = TextHint,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // 🎶 Demo Playlist Section
        Text(
            text = "Demo Playlist",
            color = TextPrimary,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(10.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            itemsIndexed(demoSongs) { index, song ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(120.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF1E1E1E))
                        .clickable {
                            playerViewModel.setPlaylist(demoSongs, index)
                            onNavigateToPlayer()
                        }
                        .padding(8.dp)
                ) {
                    AsyncImage(
                        model = song.albumArtUrl,
                        contentDescription = song.title,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = song.title,
                        color = TextPrimary,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        modifier = Modifier.height(40.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 💿 Favorite Artists
        Text(
            text = "From Your Favorite Artists",
            color = TextPrimary,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(10.dp))

        when {
            isLoading -> {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(5) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .width(120.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF2A2A2A))
                                .padding(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFF3A3A3A))
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(16.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color(0xFF3A3A3A))
                            )
                        }
                    }
                }
            }

            error != null -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = error!!,
                        color = Color.Red,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    Button(onClick = { homeViewModel.refreshData() }) {
                        Text("Thử lại")
                    }
                }
            }

            artistTopTracks.isNotEmpty() -> {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(artistTopTracks.take(10)) { track: TrackItem ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .width(120.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF1E1E1E))
                                .clickable {
                                    track.preview_url?.let { url ->
                                        if (url.isNotBlank()) {
                                            val song = Song(
                                                id = track.id,
                                                title = track.name,
                                                artist = track.artists.joinToString { it.name },
                                                albumArtUrl = track.album?.images?.firstOrNull()?.url ?: "",
                                                songUrl = url
                                            )
                                            playerViewModel.play(song)
                                            onNavigateToPlayer()
                                        }
                                    }
                                }
                                .padding(8.dp)
                        ) {
                            AsyncImage(
                                model = track.album?.images?.firstOrNull()?.url
                                    ?: "https://via.placeholder.com/640",
                                contentDescription = track.name,
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = track.name,
                                color = TextPrimary,
                                style = MaterialTheme.typography.bodyMedium,
                                maxLines = 2,
                                modifier = Modifier.height(40.dp)
                            )
                            Text(
                                text = track.artists.joinToString { it.name },
                                color = TextHint,
                                style = MaterialTheme.typography.bodySmall,
                                maxLines = 1
                            )
                        }
                    }
                }
            }

            else -> {
                Text(
                    text = "Chọn nghệ sĩ yêu thích để xem nhạc của họ",
                    color = TextHint,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}
