package com.example.tktmusicapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tktmusicapp.ui.screens.auth.ChooseArtistScreen
import com.example.tktmusicapp.ui.screens.auth.LoginScreen
import com.example.tktmusicapp.ui.screens.auth.RegisterScreen
import com.example.tktmusicapp.ui.screens.auth.WelcomeScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String = Destinations.WELCOME
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
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
                },
                onLoginWithGoogle = {
                    // TODO: Xử lý Firebase Google Auth
                    // Tạm thời mock thành công
                    navController.navigate(Destinations.CHOOSE_ARTIST) {
                        popUpTo(Destinations.WELCOME) { inclusive = true }
                    }
                },
                onLoginWithEmail = { email, password ->
                    // TODO: Xử lý Firebase Email Auth
                    // Tạm thời mock thành công
                    navController.navigate(Destinations.CHOOSE_ARTIST) {
                        popUpTo(Destinations.WELCOME) { inclusive = true }
                    }
                },
                isLoading = false, // TODO: Lấy từ ViewModel
                errorMessage = null // TODO: Lấy từ ViewModel
            )
        }

        composable(Destinations.REGISTER) {
            RegisterScreen(
                onNavigateToLogin = { navController.navigate(Destinations.LOGIN) },
                onNavigateToChooseArtist = { navController.navigate(Destinations.CHOOSE_ARTIST) },
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
                    navController.navigate(Destinations.MAIN) {
                        popUpTo(Destinations.WELCOME) { inclusive = true }
                    }
                }
            )
        }
    }
}