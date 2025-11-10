package com.example.tktmusicapp.domain.usecase.user

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SaveTrackUseCase @Inject constructor() {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    suspend operator fun invoke(trackId: String) {
        val userId = auth.currentUser?.uid ?: throw Exception("Chưa đăng nhập")
        val currentTracks = GetSavedTracksUseCase().invoke().toMutableList()

        if (!currentTracks.contains(trackId)) {
            currentTracks.add(trackId)
            firestore.collection("users").document(userId)
                .set(mapOf("savedTracks" to currentTracks), SetOptions.merge())
                .await()
        }
    }
}