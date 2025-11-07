package com.example.tktmusicapp.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tktmusicapp.ui.theme.*
import kotlinx.coroutines.delay

// Demo Artist data - sẽ thay bằng Spotify API sau
data class DemoArtist(
    val id: String,
    val name: String,
    val genre: String,
    val followers: Int,
    val imageUrl: String = "" // Placeholder for future Spotify images
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseArtistScreen(
    onComplete: () -> Unit
) {
    var selectedArtists by remember { mutableStateOf(emptySet<String>()) }
    var isLoading by remember { mutableStateOf(true) }
    var searchQuery by remember { mutableStateOf("") }

    val gradient = Brush.verticalGradient(
        colors = listOf(GradientStart, GradientMiddle, GradientEnd)
    )

    // Simulate API loading - sẽ thay bằng real API call sau
    LaunchedEffect(Unit) {
        delay(1500)
        isLoading = false
    }

    // Filter artists based on search query
    val filteredArtists = if (searchQuery.isBlank()) {
        getDemoArtists()
    } else {
        getDemoArtists().filter { artist ->
            artist.name.contains(searchQuery, ignoreCase = true) ||
                    artist.genre.contains(searchQuery, ignoreCase = true)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // Header
            Text(
                text = "Chọn nghệ sĩ yêu thích của bạn",
                style = MaterialTheme.typography.headlineMedium,
                color = TextPrimary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Chọn ít nhất 3 nghệ sĩ để cá nhân hóa trải nghiệm",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Search Bar
            OutlinedTextField(

                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                placeholder = {
                    Text(
                        "Tìm kiếm nghệ sĩ...",
                        color = TextHint
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = TextSecondary
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = PrimaryButton,
                    unfocusedBorderColor = TextHint.copy(alpha = 0.5f),
                    cursorColor = PrimaryButton,
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Selected Count
            Text(
                text = "Đã chọn: ${selectedArtists.size}/3",
                style = MaterialTheme.typography.bodyLarge,
                color = if (selectedArtists.size >= 3) PrimaryButton else TextSecondary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Artists List
            if (isLoading) {
                // Skeleton Loading for Artists
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(6) {
                        ArtistItemSkeleton()
                    }
                }
            } else if (filteredArtists.isEmpty()) {
                // Empty State
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Không tìm thấy nghệ sĩ",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextSecondary
                    )
                    Text(
                        text = "Thử tìm kiếm với từ khóa khác",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextHint
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredArtists) { artist ->
                        ArtistItem(
                            artist = artist,
                            isSelected = selectedArtists.contains(artist.id),
                            onToggle = { artistId ->
                                selectedArtists = if (selectedArtists.contains(artistId)) {
                                    selectedArtists - artistId
                                } else {
                                    // Giới hạn tối đa 10 nghệ sĩ
                                    if (selectedArtists.size < 10) {
                                        selectedArtists + artistId
                                    } else {
                                        selectedArtists
                                    }
                                }
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Complete Button
            Button(
                onClick = onComplete,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = selectedArtists.size >= 3 && !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryButton,
                    disabledContainerColor = PrimaryButton.copy(alpha = 0.3f)
                )
            ) {
                Text(
                    text = if (selectedArtists.size < 3) {
                        "Chọn thêm ${3 - selectedArtists.size} nghệ sĩ"
                    } else {
                        "Hoàn thành"
                    },
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
private fun ArtistItem(
    artist: DemoArtist,
    isSelected: Boolean,
    onToggle: (String) -> Unit
) {
    Card(
        onClick = { onToggle(artist.id) },
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                PrimaryButton.copy(alpha = 0.15f)
            else
                TextHint.copy(alpha = 0.08f)
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 4.dp else 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Artist Avatar với chữ cái đầu
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(
                        if (isSelected) PrimaryButton.copy(alpha = 0.3f)
                        else TextHint.copy(alpha = 0.3f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = artist.name.take(2).uppercase(),
                    style = MaterialTheme.typography.labelLarge,
                    color = if (isSelected) PrimaryButton else TextSecondary
                )
            }

            // Artist Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = artist.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary
                )
                Text(
                    text = "${artist.genre} • ${formatFollowers(artist.followers)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }

            // Selection Indicator
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(
                        if (isSelected) PrimaryButton
                        else TextHint.copy(alpha = 0.3f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = TextPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ArtistItemSkeleton() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = TextHint.copy(alpha = 0.08f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Skeleton Avatar
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(TextHint.copy(alpha = 0.3f))
            )

            // Skeleton Text
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(20.dp)
                        .background(TextHint.copy(alpha = 0.3f))
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .height(16.dp)
                        .background(TextHint.copy(alpha = 0.3f))
                )
            }

            // Skeleton Checkbox
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(TextHint.copy(alpha = 0.3f))
            )
        }
    }
}

// Helper function to format followers count
private fun formatFollowers(count: Int): String {
    return when {
        count >= 1_000_000 -> "${count / 1_000_000}M followers"
        count >= 1_000 -> "${count / 1_000}K followers"
        else -> "$count followers"
    }
}

// Demo data - sẽ thay bằng Spotify API call sau
private fun getDemoArtists(): List<DemoArtist> = listOf(
    DemoArtist("1", "Sơn Tùng M-TP", "Pop", 5_200_000),
    DemoArtist("2", "Đen Vâu", "Rap", 3_800_000),
    DemoArtist("3", "Jack", "Pop", 4_500_000),
    DemoArtist("4", "AMEE", "R&B", 2_100_000),
    DemoArtist("5", "Bích Phương", "Pop", 2_800_000),
    DemoArtist("6", "JustaTee", "Rap", 1_900_000),
    DemoArtist("7", "Hiền Hồ", "Pop", 1_500_000),
    DemoArtist("8", "ERIK", "Ballad", 1_200_000),
    DemoArtist("9", "MIN", "R&B", 1_800_000),
    DemoArtist("10", "Soobin Hoàng Sơn", "Pop", 2_300_000),
    DemoArtist("11", "Đức Phúc", "Ballad", 1_600_000),
    DemoArtist("12", "Mỹ Tâm", "Pop", 3_200_000),
    DemoArtist("13", "Hòa Minzy", "Pop", 2_700_000),
    DemoArtist("14", "Đông Nhi", "Pop", 1_900_000),
    DemoArtist("15", "Sơn Tùng M-TP", "V-Pop", 5_500_000),
    DemoArtist("16", "Binz", "Rap", 1_400_000),
    DemoArtist("17", "Phương Ly", "Pop", 1_100_000),
    DemoArtist("18", "Karik", "Rap", 1_800_000),
    DemoArtist("19", "Orange", "Rap", 900_000),
    DemoArtist("20", "Thịnh Suy", "Rap", 800_000)
)

@Preview
@Composable
fun ChooseArtistScreenPreview() {
    TKTMusicAppTheme {
        ChooseArtistScreen(
            onComplete = {}
        )
    }
}