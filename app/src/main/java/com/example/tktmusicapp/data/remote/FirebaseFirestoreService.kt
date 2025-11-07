package com.example.tktmusicapp.data.remote

import com.example.tktmusicapp.utils.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseFirestoreService @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {

    suspend fun createUserProfile(email: String, nickname: String): Result<Unit> {
        return try {
            val uid = firebaseAuth.currentUser?.uid
                ?: return Result.Error("User not authenticated")

            val userMap = hashMapOf(
                "email" to email,
                "nickname" to nickname,
                "createdAt" to System.currentTimeMillis(),
                "uid" to uid
            )

            firestore.collection("users")
                .document(uid)
                .set(userMap)
                .await()

            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "Tạo profile thất bại", e)
        }
    }
}