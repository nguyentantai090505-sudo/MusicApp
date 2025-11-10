package com.example.tktmusicapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tktmusicapp.data.model.spotify.AlbumItem
import com.example.tktmusicapp.data.model.spotify.TrackItem
import com.example.tktmusicapp.domain.usecase.spotify.GetNewReleasesUseCase
import com.example.tktmusicapp.domain.usecase.spotify.GetArtistTopTracksUseCase
import com.example.tktmusicapp.domain.usecase.user.GetFavoriteArtistsUseCase
import com.example.tktmusicapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNewReleasesUseCase: GetNewReleasesUseCase,
    private val getArtistTopTracksUseCase: GetArtistTopTracksUseCase,
    private val getFavoriteArtistsUseCase: GetFavoriteArtistsUseCase
) : ViewModel() {

    private val _newReleases = MutableStateFlow<List<AlbumItem>>(emptyList())
    val newReleases: StateFlow<List<AlbumItem>> = _newReleases.asStateFlow()

    private val _artistTopTracks = MutableStateFlow<List<TrackItem>>(emptyList())
    val artistTopTracks: StateFlow<List<TrackItem>> = _artistTopTracks.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadHomeData()
    }

    fun loadHomeData() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                // 1. Load New Releases - Luôn ổn định
                when (val result = getNewReleasesUseCase()) {
                    is Result.Success -> {
                        _newReleases.value = result.data?.albums?.items ?: emptyList()
                    }
                    is Result.Error -> {
                        // Không block UI, chỉ log lỗi
                        println("New releases error: ${result.message}")
                    }
                }

                // 2. Load Top Tracks từ Favorite Artists
                val favoriteArtists = getFavoriteArtistsUseCase()
                if (favoriteArtists.isNotEmpty()) {
                    loadArtistTopTracks(favoriteArtists.take(3))
                } else {
                    // Nếu chưa có favorite artists, hiển thị popular tracks VN
                    loadPopularVietnamTracks()
                }

            } catch (e: Exception) {
                _error.value = "Lỗi tải dữ liệu: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun loadArtistTopTracks(artistIds: List<String>) {
        val allTracks = mutableListOf<TrackItem>()

        artistIds.forEach { artistId ->
            when (val result = getArtistTopTracksUseCase(artistId)) {
                is Result.Success -> {
                    allTracks.addAll(result.data?.tracks ?: emptyList())
                }
                is Result.Error -> {
                    // Bỏ qua lỗi, tiếp tục với artist khác
                    println("Artist $artistId top tracks error: ${result.message}")
                }
            }
        }

        _artistTopTracks.value = allTracks.shuffled().take(15)
    }

    private suspend fun loadPopularVietnamTracks() {
        // Tạm thời dùng search để lấy popular Vietnam tracks
        // Có thể hardcode một vài track popular của VN
        _artistTopTracks.value = emptyList()
    }

    fun refreshData() {
        loadHomeData()
    }

    fun clearError() {
        _error.value = null
    }
}