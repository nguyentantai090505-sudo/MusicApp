package com.example.tktmusicapp.domain.usecase

import com.example.tktmusicapp.repository.AuthRepository
import com.example.tktmusicapp.utils.Result
import javax.inject.Inject

class LoginWithGoogleUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(idToken: String): Result<Unit> {
        return authRepository.loginWithGoogle(idToken)
    }
}