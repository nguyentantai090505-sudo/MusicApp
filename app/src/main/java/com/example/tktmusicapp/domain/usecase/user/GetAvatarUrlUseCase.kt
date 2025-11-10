package com.example.tktmusicapp.domain.usecase.user

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetAvatarUrlUseCase @Inject constructor() {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    suspend operator fun invoke(): String? {
        val userId = auth.currentUser?.uid ?: return null
        val doc = firestore.collection("users").document(userId).get().await()
        return doc.getString("avatarUrl")
    }
}