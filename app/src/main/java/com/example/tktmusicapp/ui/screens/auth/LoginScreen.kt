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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tktmusicapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit,
    onLoginWithGoogle: () -> Unit,
    onLoginWithEmail: (email: String, password: String) -> Unit,
    isLoading: Boolean = false,
    errorMessage: String? = null
) {
    var showEmailLogin by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(showEmailLogin) {
        email = ""
        password = ""
    }

    val gradient = Brush.verticalGradient(
        colors = listOf(GradientStart, GradientMiddle, GradientEnd)
    )

    // Màu nền #121212 cho email login
    val darkBackground = Color(0xFF121212)

    // Chọn background dựa trên mode
    val backgroundModifier = if (showEmailLogin) {
        Modifier.fillMaxSize().background(darkBackground)
    } else {
        Modifier.fillMaxSize().background(gradient)
    }

    Box(modifier = backgroundModifier) {
        if (showEmailLogin) {
            // EMAIL LOGIN LAYOUT - Nâng cao các phần tử lên
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
            ) {
                // Back button - nằm cao hơn
                IconButton(
                    onClick = {
                        showEmailLogin = false
                        email = ""
                        password = ""
                    },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Quay lại",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(80.dp)) // Tăng khoảng cách từ top

                // Bỏ dòng text "Đăng nhập với email"

                // Form nhập liệu - nâng cao lên
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email hoặc tên người dùng", color = Color(0xFFB3B3B3)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = PrimaryButton,
                        unfocusedBorderColor = Color(0xFF535353),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    enabled = !isLoading
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Mật khẩu", color = Color(0xFFB3B3B3)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = PrimaryButton,
                        unfocusedBorderColor = Color(0xFF535353),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    enabled = !isLoading
                )

                // Error message từ Firebase
                if (!errorMessage.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(40.dp)) // Tăng khoảng cách trước nút

                // Login Button - nâng cao lên để không bị bàn phím che
                Button(
                    onClick = {
                        if (email.isNotEmpty() && password.isNotEmpty()) {
                            onLoginWithEmail(email, password)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryButton),
                    enabled = !isLoading && email.isNotEmpty() && password.isNotEmpty()
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = TextPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Text("Đăng nhập", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        } else {
            // DEFAULT LOGIN OPTIONS - Giữ nguyên layout cũ với gradient
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

                // Google Login Button
                Button(
                    onClick = onLoginWithGoogle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryButton),
                    enabled = !isLoading
                ) {
                    Text("Tiếp tục với Google", style = MaterialTheme.typography.bodyLarge)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Email Login Button - Show email form
                Button(
                    onClick = { showEmailLogin = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryButton),
                    enabled = !isLoading
                ) {
                    Text("Tiếp tục với Email", style = MaterialTheme.typography.bodyLarge)
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Register section
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Bạn chưa có tài khoản? ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                    TextButton(
                        onClick = onNavigateToRegister,
                        contentPadding = PaddingValues(0.dp),
                        enabled = !isLoading
                    ) {
                        Text(
                            text = "Đăng ký",
                            style = MaterialTheme.typography.bodyMedium,
                            color = PrimaryButton
                        )
                    }
                }
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
            onLoginSuccess = {},
            onLoginWithGoogle = {},
            onLoginWithEmail = { _, _ -> }
        )
    }
}