package com.example.tktmusicapp.repository.impl

import com.example.tktmusicapp.data.remote.SpotifyApiService
import com.example.tktmusicapp.repository.MusicRepository
import com.example.tktmusicapp.utils.Result
import com.example.tktmusicapp.utils.SpotifyTokenManager
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(private val api: SpotifyApiService) : MusicRepository {
    override suspend fun search(query: String) = safeCall { api.search(SpotifyTokenManager.getToken(), query) }
    override suspend fun getArtist(id: String) = safeCall { api.getArtist(SpotifyTokenManager.getToken(), id) }
    override suspend fun getAlbum(id: String) = safeCall { api.getAlbum(SpotifyTokenManager.getToken(), id) }
    override suspend fun getTrack(id: String) = safeCall { api.getTrack(SpotifyTokenManager.getToken(), id) }
    override suspend fun getRecommendations(seedArtists: List<String>) = safeCall { api.getRecommendations(SpotifyTokenManager.getToken(), seedArtists.joinToString(",")) }

    private inline fun <T> safeCall(call: () -> T): Result<T> = try { Result.Success(call()) } catch (e: Exception) { Result.Error(e.message ?: "Lỗi") }
}