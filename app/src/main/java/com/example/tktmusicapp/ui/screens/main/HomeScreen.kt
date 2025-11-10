package com.example.tktmusicapp.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.tktmusicapp.ui.theme.TextHint
import com.example.tktmusicapp.ui.theme.TextPrimary
import com.example.tktmusicapp.R
import androidx.compose.ui.graphics.Brush
import com.example.tktmusicapp.ui.theme.GradientMiddle
import com.example.tktmusicapp.ui.theme.GradientEnd
import com.example.tktmusicapp.ui.theme.GradientStart
import com.example.tktmusicapp.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToSearch: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToLibrary: () -> Unit,
    onNavigateToPlayer: () -> Unit
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val newReleases by viewModel.newReleases.collectAsState()
    val artistTopTracks by viewModel.artistTopTracks.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadHomeData()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        GradientStart,
                        GradientMiddle,
                        GradientEnd
                    )
                )
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Header + Search Box
        Column {
            Text(
                text = "Search...",
                color = TextPrimary,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Search Box - Click để navigate đến SearchScreen
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

            // New Releases Section - TĂNG CHIỀU CAO LazyRow
            if (newReleases.isNotEmpty()) {
                Text(
                    text = "New Releases",
                    color = TextPrimary,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(
                    modifier = Modifier.height(180.dp), // TĂNG CHIỀU CAO
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

            // Top Tracks from Favorite Artists Section - TĂNG CHIỀU CAO LazyRow
            Text(
                text = "From Your Favorite Artists",
                color = TextPrimary,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(10.dp))

            if (isLoading) {
                // Loading skeleton - TĂNG CHIỀU CAO
                LazyRow(
                    modifier = Modifier.height(180.dp), // TĂNG CHIỀU CAO
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(5) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .width(120.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF1E1E1E))
                                .padding(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFF2A2A2A))
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(16.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color(0xFF2A2A2A))
                            )
                        }
                    }
                }
            } else if (error != null) {
                // Error state
                Column {
                    Text(
                        text = error!!,
                        color = Color.Red,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    Button(onClick = { viewModel.refreshData() }) {
                        Text("Thử lại")
                    }
                }
            } else if (artistTopTracks.isNotEmpty()) {
                // Top tracks from favorite artists - TĂNG CHIỀU CAO
                LazyRow(
                    modifier = Modifier.height(180.dp), // TĂNG CHIỀU CAO
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(artistTopTracks.take(10)) { track ->
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
                                model = track.album.images.firstOrNull()?.url ?: "https://via.placeholder.com/640",
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
            } else {
                // Empty state
                Text(
                    text = "Chọn nghệ sĩ yêu thích để xem nhạc của họ",
                    color = TextHint,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
        }

        // Bottom Navigation - GIỮ NGUYÊN
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* stay on home */ }) {
                Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.White)
            }
            IconButton(onClick = onNavigateToSearch) {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
            }
            IconButton(onClick = onNavigateToLibrary) {
                Icon(Icons.Default.LibraryMusic, contentDescription = "Library", tint = Color.White)
            }
            IconButton(onClick = onNavigateToProfile) {
                Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.White)
            }
        }
    }
}