package com.example.tktmusicapp.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tktmusicapp.ui.navigation.AppNavGraph
import com.example.tktmusicapp.ui.navigation.Destinations
import com.example.tktmusicapp.ui.theme.BackgroundDark
import com.example.tktmusicapp.viewmodel.PlayerViewModel

@Composable
fun MainScreen(navController: NavHostController, playerViewModel: PlayerViewModel) {

    val currentSong by playerViewModel.currentSong

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomBarDestinations = listOf(
        Destinations.HOME,
        Destinations.SEARCH,
        Destinations.LIBRARY,
        Destinations.PROFILE,
        Destinations.PLAYER
    )

    val shouldShowBottomBar = currentRoute in bottomBarDestinations

    val gradientColors = listOf(
        Color(0xFF6C63FF), // tím
        Color(0xFF352295), // xanh tím đậm
        BackgroundDark     // đen nền
    )
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(gradientColors)),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // 📱 Toàn bộ Navigation Graph
            AppNavGraph(navController = navController, playerViewModel = playerViewModel)

            // 🔹 Thanh điều hướng dưới cùng (chỉ hiển thị trên các màn hình chính)
            if (shouldShowBottomBar) {
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .background(Color.Black) // Đặt nền đen cho toàn bộ thanh dưới cùng
                ) {
                    // MiniPlayer chỉ hiển thị khi có bài hát
                    if (currentSong != null) {
                        MiniPlayer(playerViewModel = playerViewModel, onCLick = { navController.navigate(Destinations.PLAYER) })
                    }

                    // Thanh điều hướng luôn hiển thị
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        IconButton(onClick = { navController.navigate(Destinations.HOME) }) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Home",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = { navController.navigate(Destinations.SEARCH) }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = { navController.navigate(Destinations.LIBRARY) }) {
                            Icon(
                                imageVector = Icons.Default.LibraryMusic,
                                contentDescription = "Library",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = { navController.navigate(Destinations.PROFILE) }) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}
