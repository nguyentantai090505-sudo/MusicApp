package com.example.tktmusicapp.ui.screens.auth

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tktmusicapp.ui.theme.*
import com.example.tktmusicapp.viewmodel.AuthState
import com.example.tktmusicapp.viewmodel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

private enum class RegisterStep { STEP1, STEP2, STEP3, STEP4 }

@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToChooseArtist: () -> Unit
) {
    val viewModel: AuthViewModel = hiltViewModel()
    val authState by viewModel.authState.collectAsState()
    val context = LocalContext.current

    var currentStep by remember { mutableStateOf(RegisterStep.STEP1) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }

    // Google Sign-In Launcher
    val googleLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            account.idToken?.let { token ->
                viewModel.loginWithGoogle(token) // Google đăng ký = login luôn
            }
        } catch (e: ApiException) {
            // Không làm gì hoặc toast lỗi
        }
    }

    // Xử lý kết quả Auth
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Success -> {
                onNavigateToChooseArtist()
                viewModel.resetState()
            }
            is AuthState.Error -> {
                // Error sẽ hiển thị ở từng bước nếu cần
            }
            else -> Unit
        }
    }

    val isLoading = authState is AuthState.Loading
    val errorMessage = (authState as? AuthState.Error)?.message

    val gradient = Brush.verticalGradient(
        colors = listOf(GradientStart, GradientMiddle, GradientEnd)
    )
    val darkBackground = Color(0xFF121212)
    val backgroundModifier = if (currentStep == RegisterStep.STEP1) {
        Modifier.fillMaxSize().background(gradient)
    } else {
        Modifier.fillMaxSize().background(darkBackground)
    }

    Box(modifier = backgroundModifier) {
        when (currentStep) {
            RegisterStep.STEP1 -> RegisterStep1Content(
                onContinueWithGoogle = {
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken("294351049895-0epsmb2go6o3jn3gl04aj5cndnf1aqhv.apps.googleusercontent.com") // ← THAY BẰNG WEB CLIENT ID THẬT
                        .requestEmail()
                        .build()
                    val client = GoogleSignIn.getClient(context, gso)
                    googleLauncher.launch(client.signInIntent)
                },
                onContinueWithEmail = { currentStep = RegisterStep.STEP2 },
                onNavigateToLogin = onNavigateToLogin,
                isLoading = isLoading
            )
            RegisterStep.STEP2 -> RegisterStep2Content(
                email = email,
                onEmailChange = { email = it },
                onNext = {
                    if (email.isNotBlank() && isValidEmail(email)) {
                        currentStep = RegisterStep.STEP3
                    }
                },
                onBack = { currentStep = RegisterStep.STEP1 },
                isLoading = isLoading
            )
            RegisterStep.STEP3 -> RegisterStep3Content(
                password = password,
                onPasswordChange = { password = it },
                onNext = {
                    if (password.length >= 6) {
                        currentStep = RegisterStep.STEP4
                    }
                },
                onBack = { currentStep = RegisterStep.STEP2 },
                isLoading = isLoading
            )
            RegisterStep.STEP4 -> RegisterStep4Content(
                nickname = nickname,
                onNicknameChange = { nickname = it },
                onComplete = {
                    if (nickname.isNotBlank()) {
                        viewModel.registerWithEmail(email, password, nickname)
                    }
                },
                onBack = { currentStep = RegisterStep.STEP3 },
                isLoading = isLoading,
                errorMessage = errorMessage
            )
        }

        // Loading overlay toàn màn hình
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = PrimaryButton)
            }
        }
    }
}

private fun isValidEmail(email: String): Boolean {
    return email.contains("@") && email.contains(".") && email.length > 5
}

@Composable
private fun RegisterStep1Content(
    onContinueWithGoogle: () -> Unit,
    onContinueWithEmail: () -> Unit,
    onNavigateToLogin: () -> Unit,
    isLoading: Boolean
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
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

        Button(
            onClick = onContinueWithGoogle,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryButton),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
            } else {
                Text("Tiếp tục bằng Google")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onContinueWithEmail,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryButton),
            enabled = !isLoading
        ) {
            Text("Tiếp tục với email")
        }

        Spacer(modifier = Modifier.height(32.dp))

        TextButton(onClick = onNavigateToLogin, enabled = !isLoading) {
            Text("Đã có tài khoản? Đăng nhập", color = TextSecondary)
        }
    }
}

@Composable
private fun RegisterStep2Content(
    email: String,
    onEmailChange: (String) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit,
    isLoading: Boolean
) {
    Column(modifier = Modifier.fillMaxSize().padding(32.dp)) {
        IconButton(onClick = onBack) {
            Icon(Icons.Default.ArrowBack, "Back", tint = Color.White)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Nhập email của bạn để bắt đầu",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
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
            ),
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6C63FF)),
            enabled = email.isNotBlank() && isValidEmail(email) && !isLoading
        ) {
            Text("Tiếp tục", color = Color.White)
        }
    }
}

@Composable
private fun RegisterStep3Content(
    password: String,
    onPasswordChange: (String) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit,
    isLoading: Boolean
) {
    Column(modifier = Modifier.fillMaxSize().padding(32.dp)) {
        IconButton(onClick = onBack) {
            Icon(Icons.Default.ArrowBack, "Back", tint = Color.White)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Tạo mật khẩu",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
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
            ),
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text("Mật khẩu phải có ít nhất 6 ký tự", style = MaterialTheme.typography.bodySmall, color = Color(0xFFB3B3B3))

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6C63FF)),
            enabled = password.length >= 6 && !isLoading
        ) {
            Text("Tiếp tục", color = Color.White)
        }
    }
}

@Composable
private fun RegisterStep4Content(
    nickname: String,
    onNicknameChange: (String) -> Unit,
    onComplete: () -> Unit,
    onBack: () -> Unit,
    isLoading: Boolean,
    errorMessage: String?
) {
    Column(modifier = Modifier.fillMaxSize().padding(32.dp)) {
        IconButton(onClick = onBack) {
            Icon(Icons.Default.ArrowBack, "Back", tint = Color.White)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Chọn tên tài khoản",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
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
            ),
            enabled = !isLoading
        )

        if (!errorMessage.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onComplete,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6C63FF)),
            enabled = nickname.isNotBlank() && !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
            } else {
                Text("Tiếp tục", color = Color.White)
            }
        }
    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    TKTMusicAppTheme {
        RegisterScreen(
            onNavigateToLogin = {},
            onNavigateToChooseArtist = {}
        )
    }
}