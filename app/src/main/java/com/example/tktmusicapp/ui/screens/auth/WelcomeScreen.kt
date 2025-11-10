// ui/screens/auth/WelcomeScreen.kt
package com.example.tktmusicapp.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tktmusicapp.ui.components.auth.OutlinedAuthButton
import com.example.tktmusicapp.ui.components.auth.PrimaryAuthButton
import com.example.tktmusicapp.ui.theme.*

@Composable
fun WelcomeScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    0f to GradientStart,
                    0.3f to GradientMiddle,  // VÙNG TÍM GIỮA
                    0.6f to GradientEnd
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // === PHẦN TRÊN: LOGO + "Bắt đầu" ===
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 100.dp)
            ) {
                Spacer(modifier = Modifier.height(80.dp)) // Chỗ logo

                Text(
                    text = "Bắt đầu",
                    style = MaterialTheme.typography.headlineLarge.copy(color = Color.White),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Nghe nhạc theo sở thích của bạn.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }


            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 100.dp)
            ) {
                PrimaryAuthButton(
                    text = "Đăng ký miễn phí",
                    onClick = onNavigateToRegister
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedAuthButton(
                    text = "Đăng nhập",
                    onClick = onNavigateToLogin
                )
            }
        }
    }
}