package com.example.tktmusicapp.repository.impl

import com.example.tktmusicapp.data.remote.FirebaseAuthService
import com.example.tktmusicapp.data.remote.FirebaseFirestoreService
import com.example.tktmusicapp.repository.AuthRepository
import com.example.tktmusicapp.utils.Result
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: FirebaseAuthService,
    private val firestoreService: FirebaseFirestoreService
) : AuthRepository {

    override suspend fun loginWithEmail(email: String, password: String): Result<Unit> =
        authService.loginWithEmail(email, password)

    override suspend fun loginWithGoogle(idToken: String): Result<Unit> =
        authService.loginWithGoogle(idToken)

    override suspend fun registerWithEmail(email: String, password: String): Result<Unit> =
        authService.register(email, password)

    override suspend fun createUserProfile(email: String, nickname: String): Result<Unit> =
        firestoreService.createUserProfile(email, nickname)

    override fun getCurrentUserId(): String? = authService.getCurrentUser()?.uid

    override fun signOut() = authService.signOut()
}