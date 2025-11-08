package com.example.tktmusicapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tktmusicapp.data.model.spotify.ArtistItem
import com.example.tktmusicapp.domain.usecase.spotify.SearchSpotifyUseCase
import com.example.tktmusicapp.domain.usecase.user.SaveFavoriteArtistsUseCase
import com.example.tktmusicapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseArtistViewModel @Inject constructor(
    private val searchSpotifyUseCase: SearchSpotifyUseCase,
    private val saveFavoriteArtistsUseCase: SaveFavoriteArtistsUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _artists = MutableStateFlow<List<ArtistItem>>(emptyList())
    val artists: StateFlow<List<ArtistItem>> = _artists.asStateFlow()

    private val _selectedArtists = MutableStateFlow<List<ArtistItem>>(emptyList())
    val selectedArtists: StateFlow<List<ArtistItem>> = _selectedArtists.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private var searchJob: Job? = null

    init {
        loadVietNamHotArtistsReal()
        viewModelScope.launch {
            _searchQuery.debounce(300).collectLatest { query ->
                if (query.isBlank()) {
                    loadVietNamHotArtistsReal()
                } else {
                    searchArtistsReal(query)
                }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private fun loadVietNamHotArtistsReal() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val vietnamArtists = listOf(
                    "Sơn Tùng M-TP", "Đen Vâu", "AMEE", "Binz", "Karik", "Wren Evans", "Hoàng Thùy Linh",
                    "Mỹ Tâm", "Noo Phước Thịnh", "Erik", "Đức Phúc", "Soobin", "HIEUTHUHAI", "Tăng Duy Tân",
                    "Phương Ly", "Orange", "Grey D", "tlinh", "Andree Right Hand", "RPT MCK", "Low G",
                    "Chillies", "Ngọt", "Cá Hồi Hoang", "Vũ.", "Thịnh Suy", "Trang", "Minh Tốc & Lam",
                    "Quang Hùng MasterD", "Lyly", "Hương Tràm", "Hoàng Duyên", "Coldzy", "Obito", "Gonzo"
                )

                val results = coroutineScope {
                    vietnamArtists.map { name ->
                        async {
                            val result = searchSpotifyUseCase(name)
                            if (result is Result.Success) {
                                result.data?.artists?.items?.firstOrNull()
                            } else null
                        }
                    }.awaitAll().filterNotNull()
                }

                _artists.value = results.distinctBy { it.id }
            } catch (e: Exception) {
                _error.value = "Lỗi mạng: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun searchArtistsReal(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val result = searchSpotifyUseCase(query)
            if (result is Result.Success) {
                _artists.value = result.data?.artists?.items.orEmpty()
            } else if (result is Result.Error) {

                _error.value = result.message ?: result.exception?.message ?: "Không tìm thấy nghệ sĩ"
            } else {
                _error.value = "Không tìm thấy nghệ sĩ"
            }
            _isLoading.value = false
        }
    }

    fun toggleArtist(artist: ArtistItem) {
        val current = _selectedArtists.value.toMutableList()
        if (current.contains(artist)) {
            current.remove(artist)
        } else if (current.size < 5) {
            current.add(artist)
        }
        _selectedArtists.value = current
    }

    fun isSelected(artist: ArtistItem) = _selectedArtists.value.contains(artist)

    fun saveAndContinue(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val artistIds = _selectedArtists.value.map { it.id }
                saveFavoriteArtistsUseCase(artistIds)
                onSuccess()
            } catch (e: Exception) {
                _error.value = "Lưu thất bại: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}