package com.example.tktmusicapp.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tktmusicapp.ui.navigation.Destinations
import com.example.tktmusicapp.ui.screens.main.tabs.LibraryScreen

@Composable
fun MainScreen(navController: NavHostController) {
    var selectedItem by remember { mutableStateOf(Destinations.HOME) }

    Scaffold(
        containerColor = Color.Black,
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF121212),
                tonalElevation = 4.dp
            ) {
                NavigationBarItem(
                    selected = selectedItem == Destinations.HOME,
                    onClick = {
                        selectedItem = Destinations.HOME
                        navController.navigate(Destinations.HOME)
                    },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Trang Chủ") },
                    label = { Text("Trang Chủ") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.Gray,
                        selectedTextColor = Color.White,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    selected = selectedItem == Destinations.SEARCH,
                    onClick = {
                        selectedItem = Destinations.SEARCH
                        navController.navigate(Destinations.SEARCH)
                    },
                    icon = { Icon(Icons.Default.Search, contentDescription = "Tìm kiếm") },
                    label = { Text("Tìm kiếm") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.Gray,
                        selectedTextColor = Color.White,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    selected = selectedItem == Destinations.LIBRARY,
                    onClick = {
                        selectedItem = Destinations.LIBRARY
                        navController.navigate(Destinations.LIBRARY)
                    },
                    icon = { Icon(Icons.Default.LibraryMusic, contentDescription = "Thư viện") },
                    label = { Text("Thư viện") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.Gray,
                        selectedTextColor = Color.White,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Destinations.HOME,
            modifier = androidx.compose.ui.Modifier.padding(paddingValues)
        ) {
            composable(Destinations.HOME) {
                HomeScreen(
                    onNavigateToSearch = { navController.navigate(Destinations.SEARCH) },
                    onNavigateToProfile = { /* không cần */ },
                    onNavigateToPlayer = { navController.navigate(Destinations.PLAYER) }
                )
            }
            composable(Destinations.SEARCH) {
                SearchScreen(
                    onNavigateToHome = { navController.navigate(Destinations.HOME) },
                    onNavigateToProfile = { /* không cần */ },
                    onNavigateToPlayer = { navController.navigate(Destinations.PLAYER) }
                )
            }
            composable(Destinations.LIBRARY) {
                LibraryScreen()
            }
            composable(Destinations.PLAYER) {
                PlayerScreen(onNavigateBack = { navController.popBackStack() })
            }
        }
    }
}
