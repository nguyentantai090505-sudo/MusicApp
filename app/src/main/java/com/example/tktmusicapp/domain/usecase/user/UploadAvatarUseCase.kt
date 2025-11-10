package com.example.tktmusicapp.domain.usecase.user

import android.graphics.Bitmap
import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import com.example.tktmusicapp.utils.Result
class UploadAvatarUseCase @Inject constructor() {
    private val storage = FirebaseStorage.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    suspend operator fun invoke(bitmap: Bitmap): Result<String> {
        return try {
            val userId = auth.currentUser?.uid ?: throw Exception("User not logged in")

            // Convert bitmap to byte array
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos)
            val data = baos.toByteArray()

            // Upload to Firebase Storage
            val storageRef: StorageReference = storage.reference
            val avatarRef = storageRef.child("avatars/$userId.jpg")

            val uploadTask = avatarRef.putBytes(data).await()
            val downloadUrl = avatarRef.downloadUrl.await()

            // Save URL to Firestore
            firestore.collection("users").document(userId)
                .update("avatarUrl", downloadUrl.toString())
                .await()

            Result.Success(downloadUrl.toString())
        } catch (e: Exception) {
            Result.Error(e.message ?: "Upload failed")
        }
    }
}