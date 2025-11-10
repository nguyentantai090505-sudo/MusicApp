package com.example.tktmusicapp.domain.usecase.spotify

import com.example.tktmusicapp.data.model.spotify.RecommendationsResponse
import com.example.tktmusicapp.repository.MusicRepository
import com.example.tktmusicapp.utils.Result
import javax.inject.Inject

class GetRecommendationsUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    suspend operator fun invoke(seedArtists: List<String>): Result<RecommendationsResponse> {
        return musicRepository.getRecommendations(seedArtists)
    }
}