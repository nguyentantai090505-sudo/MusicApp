package com.example.tktmusicapp.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tktmusicapp.ui.theme.*

// Thu gọn enum thành 4 bước
private enum class RegisterStep { STEP1, STEP2, STEP3, STEP4 }

@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToChooseArtist: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    var currentStep by remember { mutableStateOf(RegisterStep.STEP1) }
    // State cho dữ liệu nhập
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }

    // Gradient chỉ cho Step 1
    val gradient = Brush.verticalGradient(
        colors = listOf(GradientStart, GradientMiddle, GradientEnd)
    )

    // Màu nền #121212 cho các step từ 2 trở đi
    val darkBackground = Color(0xFF121212)

    // SỬA: Tách riêng background modifier
    val backgroundModifier = if (currentStep == RegisterStep.STEP1) {
        Modifier.fillMaxSize().background(gradient)
    } else {
        Modifier.fillMaxSize().background(darkBackground)
    }

    Box(modifier = backgroundModifier) {
        when (currentStep) {
            RegisterStep.STEP1 -> RegisterStep1Content(
                onContinueWithGoogle = { onRegisterSuccess() },
                onContinueWithEmail = { currentStep = RegisterStep.STEP2 },
                onNavigateToLogin = onNavigateToLogin
            )
            RegisterStep.STEP2 -> RegisterStep2Content(
                email = email,
                onEmailChange = { email = it },
                onNext = {
                    if (email.isNotBlank() && isValidEmail(email)) {
                        currentStep = RegisterStep.STEP3
                    }
                },
                onBack = { currentStep = RegisterStep.STEP1 }
            )
            RegisterStep.STEP3 -> RegisterStep3Content(
                password = password,
                onPasswordChange = { password = it },
                onNext = {
                    if (password.length >= 6) {
                        currentStep = RegisterStep.STEP4
                    }
                },
                onBack = { currentStep = RegisterStep.STEP2 }
            )
            RegisterStep.STEP4 -> RegisterStep4Content(
                nickname = nickname,
                onNicknameChange = { nickname = it },
                onComplete = {
                    if (nickname.isNotBlank()) {
                        onNavigateToChooseArtist()
                    }
                },
                onBack = { currentStep = RegisterStep.STEP3 }
            )
        }
    }
}

// Hàm validate email đơn giản thay thế android.util.Patterns
private fun isValidEmail(email: String): Boolean {
    return email.contains("@") && email.contains(".") && email.length > 5
}

// Bước 1: GIỮ NGUYÊN GRADIENT (không cần sửa)
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

// Bước 2: Nhập email với màu nền #121212
@Composable
private fun RegisterStep2Content(
    email: String,
    onEmailChange: (String) -> Unit,
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
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Nhập email của bạn để bắt đầu",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Email", color = Color(0xFFB3B3B3)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedLabelColor = Color(0xFFB3B3B3),
                focusedLabelColor = Color(0xFF6C63FF),
                unfocusedIndicatorColor = Color(0xFF535353),
                focusedIndicatorColor = Color(0xFF6C63FF)
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Next Button
        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6C63FF)
            ),
            enabled = email.isNotBlank() && isValidEmail(email)
        ) {
            Text(
                "Tiếp tục",
                color = Color.White
            )
        }
    }
}

// Bước 3: Nhập mật khẩu với màu nền #121212
@Composable
private fun RegisterStep3Content(
    password: String,
    onPasswordChange: (String) -> Unit,
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
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Tạo mật khẩu",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Mật khẩu", color = Color(0xFFB3B3B3)) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedLabelColor = Color(0xFFB3B3B3),
                focusedLabelColor = Color(0xFF6C63FF),
                unfocusedIndicatorColor = Color(0xFF535353),
                focusedIndicatorColor = Color(0xFF6C63FF)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Mật khẩu phải có ít nhất 6 ký tự",
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFFB3B3B3),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Next Button
        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6C63FF)
            ),
            enabled = password.length >= 6
        ) {
            Text(
                "Tiếp tục",
                color = Color.White
            )
        }
    }
}

// Bước 4: Nhập nickname với màu nền #121212
@Composable
private fun RegisterStep4Content(
    nickname: String,
    onNicknameChange: (String) -> Unit,
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
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Chọn tên tài khoản",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = nickname,
            onValueChange = onNicknameChange,
            label = { Text("Nickname", color = Color(0xFFB3B3B3)) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedLabelColor = Color(0xFFB3B3B3),
                focusedLabelColor = Color(0xFF6C63FF),
                unfocusedIndicatorColor = Color(0xFF535353),
                focusedIndicatorColor = Color(0xFF6C63FF)
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onComplete,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6C63FF)
            ),
            enabled = nickname.isNotBlank()
        ) {
            Text(
                "Tiếp tục",
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    TKTMusicAppTheme {
        RegisterScreen(
            onNavigateToLogin = {},
            onNavigateToChooseArtist = {},
            onRegisterSuccess = {}
        )
    }
}