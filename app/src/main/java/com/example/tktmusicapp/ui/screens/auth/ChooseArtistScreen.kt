package com.example.tktmusicapp.ui.screens.auth

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
                placeholder = { Text("Tìm kiếm nghệ sĩ") },
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
                Text(" TIẾP TỤC(${selected.size}/5)")
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
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .width(120.dp)
            .clickable { onClick() }
    ) {

        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(
                    width = if (isSelected) 4.dp else 0.dp,
                    color = if (isSelected) Color(0xFF6C63FF) else Color.Transparent,
                    shape = CircleShape
                )
                .padding(4.dp)
        ) {
            AsyncImage(
                model = artist.images.firstOrNull()?.url ?: "https://via.placeholder.com/640",
                contentDescription = artist.name,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(Modifier.height(8.dp))


        Text(
            text = artist.name,
            style = MaterialTheme.typography.labelMedium,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
fun SkeletonLoading() {
    Box(modifier = Modifier.padding(8.dp).size(120.dp).shimmer())
}