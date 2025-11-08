package com.example.tktmusicapp.repository

import com.example.tktmusicapp.data.model.spotify.*
import com.example.tktmusicapp.utils.Result

interface MusicRepository {
    suspend fun search(query: String): Result<SearchResponse>
    suspend fun getArtist(id: String): Result<ArtistResponse>
    suspend fun getAlbum(id: String): Result<AlbumResponse>
    suspend fun getTrack(id: String): Result<TrackResponse>
    suspend fun getRecommendations(seedArtists: List<String>): Result<RecommendationsResponse>
}