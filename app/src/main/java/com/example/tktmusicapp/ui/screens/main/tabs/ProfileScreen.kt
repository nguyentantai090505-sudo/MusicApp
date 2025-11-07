package com.example.tktmusicapp.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToPlayer: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Your Profile", color = Color.White, style = MaterialTheme.typography.titleLarge)

        Spacer(Modifier.height(20.dp))

        Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(100.dp))
        Text("TKT User", color = Color.White, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onNavigateToHome,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6C63FF))
        ) { Text("Back to Home", color = Color.White) }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onNavigateToSearch,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) { Text("Search Music", color = Color.White) }
    }
}
