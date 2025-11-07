package com.example.tktmusicapp.domain.usecase

import com.example.tktmusicapp.repository.AuthRepository
import com.example.tktmusicapp.utils.Result
import javax.inject.Inject

class CreateUserProfileUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, nickname: String): Result<Unit> {
        return authRepository.createUserProfile(email, nickname)
    }
}