package com.example.tktmusicapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tktmusicapp.ui.screens.auth.ChooseArtistScreen
import com.example.tktmusicapp.ui.screens.auth.LoginScreen
import com.example.tktmusicapp.ui.screens.auth.RegisterScreen
import com.example.tktmusicapp.ui.screens.auth.WelcomeScreen
import com.example.tktmusicapp.ui.screens.main.HomeScreen
import com.example.tktmusicapp.ui.screens.main.MainScreen
import com.example.tktmusicapp.ui.screens.main.PlayerScreen
import com.example.tktmusicapp.ui.screens.main.SearchScreen
import com.example.tktmusicapp.ui.screens.main.tabs.LibraryScreen
import com.example.tktmusicapp.ui.screens.main.ProfileScreen


@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String = Destinations.WELCOME
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // ======================================
        // 🔹 AUTH FLOW (giữ nguyên của bạn)
        // ======================================
        composable(Destinations.WELCOME) {
            WelcomeScreen(
                onNavigateToLogin = { navController.navigate(Destinations.LOGIN) },
                onNavigateToRegister = { navController.navigate(Destinations.REGISTER) }
            )
        }

        composable(Destinations.LOGIN) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Destinations.REGISTER) },
                onLoginSuccess = {
                    navController.navigate(Destinations.CHOOSE_ARTIST) {
                        popUpTo(Destinations.WELCOME) { inclusive = true }
                    }
                }
            )
        }

        composable(Destinations.REGISTER) {
            RegisterScreen(
                onNavigateToLogin = { navController.navigate(Destinations.LOGIN) },
                onRegisterSuccess = {
                    navController.navigate(Destinations.CHOOSE_ARTIST) {
                        popUpTo(Destinations.WELCOME) { inclusive = true }
                    }
                }
            )
        }

        composable(Destinations.CHOOSE_ARTIST) {
            ChooseArtistScreen(
                onComplete = {
                    navController.navigate(Destinations.HOME) {
                        popUpTo(Destinations.WELCOME) { inclusive = true }
                    }
                }
            )
        }

        composable(Destinations.MAIN) {
            MainScreen(navController = navController)
        }

        // ======================================
        // 🎧 MAIN FLOW (phần mình thêm)
        // ======================================
        composable(Destinations.HOME) {
            HomeScreen(
                onNavigateToSearch = { navController.navigate(Destinations.SEARCH) },
                onNavigateToProfile = { navController.navigate(Destinations.PROFILE) },
                onNavigateToPlayer = { navController.navigate(Destinations.PLAYER) }
            )
        }

        composable(Destinations.SEARCH) {
            SearchScreen(
                onNavigateToHome = { navController.navigate(Destinations.HOME) },
                onNavigateToProfile = { navController.navigate(Destinations.PROFILE) },
                onNavigateToPlayer = { navController.navigate(Destinations.PLAYER) }
            )
        }

        composable(Destinations.PROFILE) {
            ProfileScreen(
                onNavigateToHome = { navController.navigate(Destinations.HOME) },
                onNavigateToSearch = { navController.navigate(Destinations.SEARCH) },
                onNavigateToPlayer = { navController.navigate(Destinations.PLAYER) }
            )
        }

        composable(Destinations.PLAYER) {
            PlayerScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}