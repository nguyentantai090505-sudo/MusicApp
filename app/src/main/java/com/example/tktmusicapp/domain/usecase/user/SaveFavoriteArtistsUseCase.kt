package com.example.tktmusicapp.domain.usecase.user

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SaveFavoriteArtistsUseCase @Inject constructor() {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    suspend operator fun invoke(artistIds: List<String>) {
        val userId = auth.currentUser?.uid ?: throw Exception("Chưa đăng nhập")
        firestore.collection("users").document(userId)
            .set(mapOf("favoriteArtists" to artistIds), com.google.firebase.firestore.SetOptions.merge())
            .await()
    }
}