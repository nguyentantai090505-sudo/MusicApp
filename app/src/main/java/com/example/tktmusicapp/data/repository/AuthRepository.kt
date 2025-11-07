package com.example.tktmusicapp.repository

import com.example.tktmusicapp.utils.Result

interface AuthRepository {
    suspend fun loginWithEmail(email: String, password: String): Result<Unit>
    suspend fun loginWithGoogle(idToken: String): Result<Unit>
    suspend fun registerWithEmail(email: String, password: String): Result<Unit>
    suspend fun createUserProfile(email: String, nickname: String): Result<Unit>
    fun getCurrentUserId(): String?
    fun signOut()
}