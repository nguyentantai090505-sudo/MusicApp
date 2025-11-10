package com.example.tktmusicapp.utils

import com.example.tktmusicapp.data.model.spotify.TokenResponse
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Credentials
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.tktmusicapp.data.remote.SpotifyApiService

object SpotifyTokenManager {
    private var token: String? = null
    private var expiresAt: Long = 0
    private val mutex = Mutex()

    suspend fun getToken(): String = mutex.withLock {
        if (token == null || System.currentTimeMillis() > expiresAt) {
            val retrofit = Retrofit.Builder().baseUrl("https://accounts.spotify.com/").addConverterFactory(GsonConverterFactory.create()).build()
            val service = retrofit.create(SpotifyApiService::class.java)
            val auth = Credentials.basic(SpotifyConfig.CLIENT_ID, SpotifyConfig.CLIENT_SECRET)
            val response = service.getToken(auth, "client_credentials")
            token = "Bearer ${response.access_token}"
            expiresAt = System.currentTimeMillis() + (response.expires_in * 1000L) - 60000L
        }
        token!!
    }
}