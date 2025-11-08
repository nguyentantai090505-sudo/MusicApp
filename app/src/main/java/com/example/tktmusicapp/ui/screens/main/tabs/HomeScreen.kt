package com.example.tktmusicapp.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import com.example.tktmusicapp.ui.theme.TextHint
import com.example.tktmusicapp.ui.theme.TextPrimary
import com.example.tktmusicapp.R // ⚠️ kiểm tra lại package R của bạn
import com.example.tktmusicapp.ui.theme.GradientMiddle
import androidx.compose.ui.graphics.Brush
import com.example.tktmusicapp.ui.theme.GradientEnd
import com.example.tktmusicapp.ui.theme.GradientStart

@Composable
fun HomeScreen(
    onNavigateToSearch: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToLibrary: () -> Unit,
    onNavigateToPlayer: () -> Unit
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    // 🔹 Dữ liệu giả “Vibes” (sau này có thể load từ API Spotify)
    val vibes = listOf(
        Pair("Chill Vibes", R.drawable.ic_launcher_foreground),
        Pair("Workout Energy", R.drawable.ic_launcher_foreground),
        Pair("Focus Mode", R.drawable.ic_launcher_foreground),
        Pair("Party Time", R.drawable.ic_launcher_foreground),
        Pair("Night Drive", R.drawable.ic_launcher_foreground)
    )

    val GradientBottom = null
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
    )
    {
        // 🔹 Header + Search Box
        Column {
            Text(
                text = "Hello 🎵",
                color = TextPrimary,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Ô tìm kiếm
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1E1E1E), shape = RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    BasicTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        singleLine = true,
                        textStyle = TextStyle(color = TextPrimary, fontSize = 16.sp),
                        decorationBox = { innerTextField ->
                            if (searchQuery.text.isEmpty()) {
                                Text("Search for songs, artists...", color = TextHint)
                            }
                            innerTextField()
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 🔹 Vibes section
            Text(
                text = "Your Vibes",
                color = TextPrimary,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(vibes) { (title, imageRes) ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .width(120.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF1E1E1E))
                            .clickable { onNavigateToPlayer() }
                            .padding(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = title,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = title,
                            color = TextPrimary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        // 🔹 Khoảng trống cho nội dung thêm sau
        Spacer(modifier = Modifier.weight(1f))

        // 🔹 Bottom Navigation (Home / Search / Library / Profile)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* stay on home */ }) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = Color.White
                )
            }
            IconButton(onClick = onNavigateToSearch) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White
                )
            }
            IconButton(onClick = onNavigateToLibrary) {
                Icon(
                    imageVector = Icons.Default.LibraryMusic,
                    contentDescription = "Library",
                    tint = Color.White
                )
            }
            IconButton(onClick = onNavigateToProfile) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = Color.White
                )
            }
        }
    }
}
