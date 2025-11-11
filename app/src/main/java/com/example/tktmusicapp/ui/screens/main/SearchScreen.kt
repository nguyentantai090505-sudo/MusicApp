package com.example.tktmusicapp.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.tktmusicapp.domain.model.Song
import com.example.tktmusicapp.ui.theme.BackgroundDark
import com.example.tktmusicapp.viewmodel.PlayerViewModel
import com.example.tktmusicapp.viewmodel.SearchViewModel
import com.example.tktmusicapp.data.model.spotify.TrackItem
import com.example.tktmusicapp.ui.theme.TextPrimary
import com.example.tktmusicapp.viewmodel.SearchResults

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    playerViewModel: PlayerViewModel,
    onNavigateBack: () -> Unit
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val recentSearches by viewModel.recentSearches.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    val gradient = Brush.verticalGradient(
        listOf(
            Color(0xFF6C63FF),
            Color(0xFF352295),
            BackgroundDark
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Back button
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            Text(
                text = "Search",
                color = TextPrimary,
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Search Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(14.dp))
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.width(10.dp))
                BasicTextField(
                    value = searchQuery,
                    onValueChange = viewModel::updateSearchQuery,
                    singleLine = true,
                    textStyle = TextStyle(color = Color.White, fontSize = 17.sp),
                    decorationBox = { innerTextField ->
                        if (searchQuery.isEmpty()) {
                            Text(
                                text = "Search songs, albums, artists...",
                                color = Color.White.copy(alpha = 0.6f),
                                fontSize = 15.sp
                            )
                        }
                        innerTextField()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
            error != null -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = error!!, color = Color.Red, modifier = Modifier.padding(16.dp))
                    Button(onClick = { viewModel.clearError() }) { Text("OK") }
                }
            }
            searchResults != null -> {
                SearchResultsContent(searchResults!!, playerViewModel)
            }
            searchQuery.isEmpty() && recentSearches.isNotEmpty() -> {
                RecentSearchesContent(
                    recentSearches = recentSearches,
                    onSearchClick = viewModel::searchImmediately,
                    onClearRecent = viewModel::clearRecentSearches
                )
            }
            else -> {
                Text(
                    text = if (searchQuery.isEmpty()) "Type something to start searching 🔍" else "No results found for: $searchQuery",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 15.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
private fun SearchResultsContent(results: SearchResults, playerViewModel: PlayerViewModel) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        if (results.tracks.isNotEmpty()) {
            item { Text("Songs", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold) }
            items(results.tracks.take(10)) { track ->
                TrackItem(track = track, playerViewModel = playerViewModel)
            }
        }
        // Sections for Artists and Albums can be added here if needed
    }
}

fun TrackItem.toSong(): Song {
    return Song(
        id = this.id,
        title = this.name,
        artist = this.artists.joinToString { it.name },
        albumArtUrl = this.album.images.firstOrNull()?.url ?: "",
        songUrl = this.preview_url ?: ""
    )
}

@Composable
private fun TrackItem(track: TrackItem, playerViewModel: PlayerViewModel) {
    val hasPreview = track.preview_url != null
    val alpha = if (hasPreview) 1f else 0.5f

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer(alpha = alpha)
            .clickable(enabled = hasPreview) { playerViewModel.play(track.toSong()) }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = track.album.images.firstOrNull()?.url ?: "https://via.placeholder.com/640",
            contentDescription = track.name,
            modifier = Modifier.size(50.dp).clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(track.name, color = Color.White, fontSize = 16.sp)
            Text(track.artists.joinToString { it.name }, color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
        }
    }
}

@Composable
private fun RecentSearchesContent(
    recentSearches: List<String>,
    onSearchClick: (String) -> Unit,
    onClearRecent: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Recent Searches", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text("Clear", color = Color(0xFF6C63FF), modifier = Modifier.clickable { onClearRecent() })
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(recentSearches) { query ->
                Row(
                    modifier = Modifier.fillMaxWidth().clickable { onSearchClick(query) }.padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = Color.White.copy(alpha = 0.7f), modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(query, color = Color.White, fontSize = 16.sp)
                }
            }
        }
    }
}