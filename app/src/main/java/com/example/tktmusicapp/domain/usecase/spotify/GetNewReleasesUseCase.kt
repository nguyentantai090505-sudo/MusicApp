package com.example.tktmusicapp.domain.usecase.spotify

import com.example.tktmusicapp.data.model.spotify.NewReleasesResponse
import com.example.tktmusicapp.repository.MusicRepository
import com.example.tktmusicapp.utils.Result
import javax.inject.Inject

class GetNewReleasesUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    suspend operator fun invoke(): Result<NewReleasesResponse> {
        return musicRepository.getNewReleases()
    }
}