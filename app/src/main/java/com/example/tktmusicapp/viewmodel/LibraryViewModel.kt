package com.example.tktmusicapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tktmusicapp.data.model.spotify.ArtistItem
import com.example.tktmusicapp.data.model.spotify.TrackItem
import com.example.tktmusicapp.domain.usecase.spotify.GetArtistUseCase
import com.example.tktmusicapp.domain.usecase.spotify.GetTrackUseCase
import com.example.tktmusicapp.domain.usecase.user.GetFavoriteArtistsUseCase
import com.example.tktmusicapp.domain.usecase.user.GetSavedTracksUseCase
import com.example.tktmusicapp.domain.usecase.user.SaveTrackUseCase
import com.example.tktmusicapp.domain.usecase.user.RemoveTrackUseCase
import com.example.tktmusicapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val getFavoriteArtistsUseCase: GetFavoriteArtistsUseCase,
    private val getSavedTracksUseCase: GetSavedTracksUseCase,
    private val getArtistUseCase: GetArtistUseCase,
    private val getTrackUseCase: GetTrackUseCase,
    private val saveTrackUseCase: SaveTrackUseCase,
    private val removeTrackUseCase: RemoveTrackUseCase
) : ViewModel() {

    private val _favoriteArtists = MutableStateFlow<List<ArtistItem>>(emptyList())
    val favoriteArtists: StateFlow<List<ArtistItem>> = _favoriteArtists.asStateFlow()

    private val _savedTracks = MutableStateFlow<List<TrackItem>>(emptyList())
    val savedTracks: StateFlow<List<TrackItem>> = _savedTracks.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadLibraryData()
    }

    fun loadLibraryData() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                // Load favorite artists
                loadFavoriteArtists()

                // Load saved tracks
                loadSavedTracks()

            } catch (e: Exception) {
                _error.value = "Lỗi tải thư viện: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun loadFavoriteArtists() {
        val favoriteArtistIds = getFavoriteArtistsUseCase()

        if (favoriteArtistIds.isNotEmpty()) {
            val artists = favoriteArtistIds.map { artistId ->
                viewModelScope.async {
                    when (val result = getArtistUseCase(artistId)) {
                        is Result.Success -> result.data
                        is Result.Error -> null
                    }
                }}.awaitAll().filterNotNull()

            _favoriteArtists.value = artists.map { artist ->
                ArtistItem(
                    id = artist.id,
                    name = artist.name,
                    images = artist.images
                )
            }
        }
    }

    private suspend fun loadSavedTracks() {
        val savedTrackIds = getSavedTracksUseCase()

        if (savedTrackIds.isNotEmpty()) {
            val tracks = savedTrackIds.map { trackId ->
                viewModelScope.async {
                    when (val result = getTrackUseCase(trackId)) {
                        is Result.Success -> result.data
                        is Result.Error -> null
                    }
                }
            }.awaitAll().filterNotNull()

            _savedTracks.value = tracks.map { track ->
                TrackItem(
                    id = track.id,
                    name = track.name,
                    duration_ms = track.duration_ms,
                    preview_url = track.preview_url,
                    album = track.album,
                    artists = track.artists
                )
            }
        }
    }

    fun saveTrack(trackId: String) {
        viewModelScope.launch {
            try {
                saveTrackUseCase(trackId)
                loadSavedTracks() // Reload saved tracks
            } catch (e: Exception) {
                _error.value = "Lỗi lưu bài hát: ${e.message}"
            }
        }
    }

    fun removeTrack(trackId: String) {
        viewModelScope.launch {
            try {
                removeTrackUseCase(trackId)
                loadSavedTracks() // Reload saved tracks
            } catch (e: Exception) {
                _error.value = "Lỗi xóa bài hát: ${e.message}"
            }
        }
    }

    fun refreshData() {
        loadLibraryData()
    }

    fun clearError() {
        _error.value = null
    }
}
