package com.example.tktmusicapp.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tktmusicapp.ui.theme.*

private enum class RegisterStep { STEP1, STEP2, STEP3 }

@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    var currentStep by remember { mutableStateOf(RegisterStep.STEP1) }

    val gradient = Brush.verticalGradient(
        colors = listOf(GradientStart, GradientMiddle, GradientEnd)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        when (currentStep) {
            RegisterStep.STEP1 -> RegisterStep1Content(
                onContinueWithGoogle = { onRegisterSuccess() }, // Google login thẳng đến success
                onContinueWithEmail = { currentStep = RegisterStep.STEP2 }, // Email chuyển step 2
                onNavigateToLogin = onNavigateToLogin
            )
            RegisterStep.STEP2 -> RegisterStep2Content(
                onNext = { currentStep = RegisterStep.STEP3 },
                onBack = { currentStep = RegisterStep.STEP1 }
            )
            RegisterStep.STEP3 -> RegisterStep3Content(
                onComplete = { onRegisterSuccess() }, // Hoàn thành đăng ký
                onBack = { currentStep = RegisterStep.STEP2 }
            )
        }
    }
}

@Composable
private fun RegisterStep1Content(
    onContinueWithGoogle: () -> Unit,
    onContinueWithEmail: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Đăng ký để bắt đầu nghe",
            style = MaterialTheme.typography.headlineLarge,
            color = TextPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Tiếp tục với Google
        Button(
            onClick = onContinueWithGoogle,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryButton)
        ) {
            Text("Tiếp tục bằng Google")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tiếp tục với Email
        Button(
            onClick = onContinueWithEmail,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryButton)
        ) {
            Text("Tiếp tục với email")
        }

        Spacer(modifier = Modifier.height(32.dp))

        TextButton(onClick = onNavigateToLogin) {
            Text(
                text = "Đã có tài khoản? Đăng nhập",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
        }
    }
}

@Composable
private fun RegisterStep2Content(
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        // Back Button
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = TextPrimary
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Tạo tài khoản",
            style = MaterialTheme.typography.headlineLarge,
            color = TextPrimary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Nhập email của bạn để bắt đầu",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Next Button
        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryButton)
        ) {
            Text("Tiếp tục")
        }
    }
}

@Composable
private fun RegisterStep3Content(
    onComplete: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        // Back Button
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = TextPrimary
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Hoàn tất đăng ký",
            style = MaterialTheme.typography.headlineLarge,
            color = TextPrimary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Chào mừng bạn đến với TKT Music!",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Complete Button
        Button(
            onClick = onComplete,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryButton)
        ) {
            Text("Bắt đầu nghe nhạc")
        }
    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    TKTMusicAppTheme {
        RegisterScreen(
            onNavigateToLogin = {},
            onRegisterSuccess = {}
        )
    }
}