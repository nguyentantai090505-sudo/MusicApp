package com.example.tktmusicapp.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tktmusicapp.ui.theme.*

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val gradient = Brush.verticalGradient(
        colors = listOf(GradientStart, GradientMiddle, GradientEnd)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Đăng nhập",
                style = MaterialTheme.typography.headlineLarge,
                color = TextPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Google Login - thẳng đến success
            Button(
                onClick = onLoginSuccess,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryButton)
            ) {
                Text("Tiếp tục với Google")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Email Login - thẳng đến success (tạm thời)
            Button(
                onClick = onLoginSuccess,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryButton)
            ) {
                Text("Tiếp tục với Email")
            }

            Spacer(modifier = Modifier.height(32.dp))

            TextButton(onClick = onNavigateToRegister) {
                Text(
                    text = "Chưa có tài khoản? Đăng ký",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    TKTMusicAppTheme {
        LoginScreen(
            onNavigateToRegister = {},
            onLoginSuccess = {}
        )
    }
}