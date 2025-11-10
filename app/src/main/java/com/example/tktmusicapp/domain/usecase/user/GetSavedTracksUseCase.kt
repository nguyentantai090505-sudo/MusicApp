package com.example.tktmusicapp.domain.usecase.user

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetSavedTracksUseCase @Inject constructor() {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    suspend operator fun invoke(): List<String> {
        val userId = auth.currentUser?.uid ?: return emptyList()
        val doc = firestore.collection("users").document(userId).get().await()
        return doc.get("savedTracks") as? List<String> ?: emptyList()
    }
}