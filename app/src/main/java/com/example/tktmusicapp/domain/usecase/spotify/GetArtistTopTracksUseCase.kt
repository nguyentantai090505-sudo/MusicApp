package com.example.tktmusicapp.domain.usecase.spotify

import com.example.tktmusicapp.data.model.spotify.TracksResponse
import com.example.tktmusicapp.repository.MusicRepository
import com.example.tktmusicapp.utils.Result
import javax.inject.Inject

class GetArtistTopTracksUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    suspend operator fun invoke(artistId: String): Result<TracksResponse> {
        return musicRepository.getArtistTopTracks(artistId)
    }
}