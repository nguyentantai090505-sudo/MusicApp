package com.example.tktmusicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.tktmusicapp.ui.screens.main.MainScreen
import com.example.tktmusicapp.ui.theme.TKTMusicAppTheme
import com.example.tktmusicapp.viewmodel.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val playerViewModel: PlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TKTMusicAppTheme {
                val navController = rememberNavController()
                MainScreen(navController = navController, playerViewModel = playerViewModel)
            }
        }
    }
}
