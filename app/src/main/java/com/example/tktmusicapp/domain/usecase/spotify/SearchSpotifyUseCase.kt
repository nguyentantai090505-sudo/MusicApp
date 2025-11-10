package com.example.tktmusicapp.domain.usecase.spotify

import com.example.tktmusicapp.data.model.spotify.SearchResponse
import com.example.tktmusicapp.repository.MusicRepository
import com.example.tktmusicapp.utils.Result
import javax.inject.Inject

class SearchSpotifyUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    suspend operator fun invoke(query: String): Result<SearchResponse> =
        musicRepository.search(query)
}