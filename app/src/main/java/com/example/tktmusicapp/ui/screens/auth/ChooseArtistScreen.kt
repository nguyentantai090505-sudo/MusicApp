package com.example.tktmusicapp.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.tktmusicapp.data.model.spotify.ArtistItem
import com.example.tktmusicapp.viewmodel.ChooseArtistViewModel
import com.example.tktmusicapp.ui.components.common.SkeletonLoading
import com.example.tktmusicapp.ui.components.common.shimmer
import com.example.tktmusicapp.ui.theme.CircleShape

@Composable
fun ChooseArtistScreen(
    viewModel: ChooseArtistViewModel = hiltViewModel(),
    onContinue: () -> Unit
) {
    val artists by viewModel.artists.collectAsState()
    val selected by viewModel.selectedArtists.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val query by viewModel.searchQuery.collectAsState()

    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            Text("Chọn ít nhất 5 nghệ sĩ bạn thích", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(16.dp))

            OutlinedTextField(
                value = query,
                onValueChange = viewModel::updateSearchQuery,
                placeholder = { Text("Tìm: Taylor Swift, BTS, Sơn Tùng, Blackpink...") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            )

            Spacer(Modifier.height(8.dp))
            Text("Đã chọn: ${selected.size}/5", modifier = Modifier.padding(horizontal = 16.dp))

            if (isLoading) {
                LazyVerticalGrid(GridCells.Fixed(3)) {
                    items(15) { SkeletonLoading() }
                }
            } else if (error != null) {
                Text(error!!, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(16.dp))
            } else if (artists.isEmpty()) {
                Text("Không tìm thấy! Thử: Ed Sheeran, Adele, Drake...", modifier = Modifier.padding(16.dp))
            } else {
                LazyVerticalGrid(GridCells.Fixed(3), modifier = Modifier.weight(1f)) {
                    items(artists) { artist ->
                        ArtistCard(
                            artist = artist,
                            isSelected = viewModel.isSelected(artist),
                            onClick = { viewModel.toggleArtist(artist) }
                        )
                    }
                }
            }

            Button(
                onClick = { viewModel.saveAndContinue(onContinue) },
                enabled = selected.size >= 5 && !isLoading,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), color = MaterialTheme.colorScheme.onPrimary)
                }
                Text(" TIẾP TỤC & LƯU (${selected.size}/5)")
            }
        }
    }
}

@Composable
fun ArtistCard(
    artist: ArtistItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.padding(8.dp).size(120.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = artist.images.firstOrNull()?.url ?: "https://via.placeholder.com/640",
                contentDescription = null,
                modifier = Modifier.size(80.dp).clip(CircleShape)
            )
            Spacer(Modifier.height(4.dp))
            Text(artist.name, maxLines = 1, style = MaterialTheme.typography.labelSmall)
        }
    }
}

// Nếu chưa có SkeletonLoading, tạo tạm file ui/components/common/SkeletonLoading.kt
@Composable
fun SkeletonLoading() {
    Box(modifier = Modifier.padding(8.dp).size(120.dp).shimmer())
}