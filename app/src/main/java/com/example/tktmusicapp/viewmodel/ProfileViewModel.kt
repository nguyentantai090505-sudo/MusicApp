package com.example.tktmusicapp.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tktmusicapp.data.model.spotify.ArtistItem
import com.example.tktmusicapp.data.model.spotify.TrackItem
import com.example.tktmusicapp.domain.usecase.spotify.GetArtistUseCase
import com.example.tktmusicapp.domain.usecase.spotify.GetTrackUseCase
import com.example.tktmusicapp.domain.usecase.user.*
import com.example.tktmusicapp.utils.Result
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getFavoriteArtistsUseCase: GetFavoriteArtistsUseCase,
    private val getSavedTracksUseCase: GetSavedTracksUseCase,
    private val getArtistUseCase: GetArtistUseCase,
    private val getTrackUseCase: GetTrackUseCase,
    private val uploadAvatarUseCase: UploadAvatarUseCase,
    private val getAvatarUrlUseCase: GetAvatarUrlUseCase
) : ViewModel() {

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile.asStateFlow()

    private val _favoriteArtists = MutableStateFlow<List<ArtistItem>>(emptyList())
    val favoriteArtists: StateFlow<List<ArtistItem>> = _favoriteArtists.asStateFlow()

    private val _savedTracks = MutableStateFlow<List<TrackItem>>(emptyList())
    val savedTracks: StateFlow<List<TrackItem>> = _savedTracks.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _avatarUploadState = MutableStateFlow<AvatarUploadState>(AvatarUploadState.Idle)
    val avatarUploadState: StateFlow<AvatarUploadState> = _avatarUploadState.asStateFlow()

    init {
        loadProfileData()
    }

    fun loadProfileData() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                // Load user profile
                loadUserProfile()

                // Load favorite artists
                loadFavoriteArtists()

                // Load saved tracks
                loadSavedTracks()

            } catch (e: Exception) {
                _error.value = "Lỗi tải profile: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun loadUserProfile() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val avatarUrl = getAvatarUrlUseCase()

        _userProfile.value = UserProfile(
            displayName = currentUser?.displayName ?: "User",
            email = currentUser?.email ?: "",
            avatarUrl = avatarUrl
        )
    }

    private suspend fun loadFavoriteArtists() {
        val favoriteArtistIds = getFavoriteArtistsUseCase()

        if (favoriteArtistIds.isNotEmpty()) {
            val artists = favoriteArtistIds.take(5).map { artistId ->
                viewModelScope.async {
                    try {
                        when (val result = getArtistUseCase(artistId)) {
                            is Result.Success -> result.data
                            is Result.Error -> null
                        }
                    } catch (e: Exception) {
                        null
                    }
                }
            }.awaitAll().filterNotNull()

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
            val tracks = savedTrackIds.take(5).map { trackId ->
                viewModelScope.async {
                    try {
                        when (val result = getTrackUseCase(trackId)) {
                            is Result.Success -> result.data
                            is Result.Error -> null
                        }
                    } catch (e: Exception) {
                        null
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

    fun uploadAvatar(bitmap: Bitmap) {
        viewModelScope.launch {
            _avatarUploadState.value = AvatarUploadState.Loading
            _error.value = null

            when (val result = uploadAvatarUseCase(bitmap)) {
                is Result.Success<*> -> {
                    _avatarUploadState.value = AvatarUploadState.Success(result.data as String)
                    // Reload profile to update avatar
                    loadUserProfile()
                }
                is Result.Error -> {
                    _avatarUploadState.value = AvatarUploadState.Error(result.message ?: "Upload failed")
                    _error.value = result.message
                }
            }

            // Reset upload state after 2 seconds
            kotlinx.coroutines.delay(2000)
            _avatarUploadState.value = AvatarUploadState.Idle
        }
    }

    fun refreshData() {
        loadProfileData()
    }

    fun clearError() {
        _error.value = null
        _avatarUploadState.value = AvatarUploadState.Idle
    }
}

data class UserProfile(
    val displayName: String,
    val email: String,
    val avatarUrl: String?
)

sealed class AvatarUploadState {
    object Idle : AvatarUploadState()
    object Loading : AvatarUploadState()
    data class Success(val imageUrl: String) : AvatarUploadState()
    data class Error(val message: String) : AvatarUploadState()
}
