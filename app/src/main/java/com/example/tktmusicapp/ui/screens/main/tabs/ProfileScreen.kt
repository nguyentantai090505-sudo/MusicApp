package com.example.tktmusicapp.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tktmusicapp.ui.theme.GradientEnd
import com.example.tktmusicapp.ui.theme.GradientMiddle
import com.example.tktmusicapp.ui.theme.GradientStart
import com.example.tktmusicapp.ui.theme.TextHint
import com.example.tktmusicapp.ui.theme.TextPrimary

@Composable
fun ProfileScreen(
    onNavigateBack: () -> Unit
) {
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
    ) {
        IconButton(onClick = onNavigateBack) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        Text(
            text = "👤 Your Profile",
            color = TextPrimary,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "This is your profile page.", color = TextHint)
    }
}