package com.example.tktmusicapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tktmusicapp.domain.usecase.*
import com.example.tktmusicapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginWithEmailUseCase: LoginWithEmailUseCase,
    private val loginWithGoogleUseCase: LoginWithGoogleUseCase,
    private val registerWithEmailUseCase: RegisterWithEmailUseCase,
    private val createUserProfileUseCase: CreateUserProfileUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun loginWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = loginWithEmailUseCase(email, password)
            _authState.value = when (result) {
                is Result.Success -> AuthState.Success
                is Result.Error -> AuthState.Error(result.message)
            }
        }
    }

    fun loginWithGoogle(idToken: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = loginWithGoogleUseCase(idToken)
            _authState.value = when (result) {
                is Result.Success -> AuthState.Success
                is Result.Error -> AuthState.Error(result.message)
            }
        }
    }

    fun registerWithEmail(email: String, password: String, nickname: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val registerResult = registerWithEmailUseCase(email, password)
            when (registerResult) {
                is Result.Success -> {
                    val profileResult = createUserProfileUseCase(email, nickname)
                    _authState.value = if (profileResult is Result.Success) {
                        AuthState.Success
                    } else {
                        AuthState.Error((profileResult as Result.Error).message)
                    }
                }
                is Result.Error -> _authState.value = AuthState.Error(registerResult.message)
            }
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}