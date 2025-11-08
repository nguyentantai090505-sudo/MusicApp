package com.example.tktmusicapp.domain.usecase.spotify

import com.example.tktmusicapp.data.model.spotify.TrackResponse
import com.example.tktmusicapp.repository.MusicRepository
import com.example.tktmusicapp.utils.Result
import javax.inject.Inject

class GetTrackUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    suspend operator fun invoke(trackId: String): Result<TrackResponse> {
        return musicRepository.getTrack(trackId)
    }
}