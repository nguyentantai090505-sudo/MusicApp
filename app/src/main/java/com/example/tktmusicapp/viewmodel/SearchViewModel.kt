package com.example.tktmusicapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tktmusicapp.data.model.spotify.ArtistItem
import com.example.tktmusicapp.data.model.spotify.TrackItem
import com.example.tktmusicapp.data.model.spotify.AlbumItem
import com.example.tktmusicapp.data.model.spotify.SearchResponse
import com.example.tktmusicapp.domain.usecase.spotify.SearchSpotifyUseCase
import com.example.tktmusicapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchSpotifyUseCase: SearchSpotifyUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<SearchResults?>(null)
    val searchResults: StateFlow<SearchResults?> = _searchResults.asStateFlow()

    private val _recentSearches = MutableStateFlow<List<String>>(emptyList())
    val recentSearches: StateFlow<List<String>> = _recentSearches.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private var searchJob: Job? = null

    init {
        // Debounce search - tự động search sau 500ms khi user ngừng gõ
        viewModelScope.launch {
            _searchQuery
                .debounce(500)
                .collect { query ->
                    if (query.isNotBlank()) {
                        performSearch(query)
                    } else {
                        clearSearchResults()
                    }
                }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private fun performSearch(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            when (val result = searchSpotifyUseCase(query)) {
                is Result.Success -> {
                    val data = result.data
                    _searchResults.value = SearchResults(
                        tracks = data?.tracks?.items ?: emptyList(),
                        artists = data?.artists?.items ?: emptyList(),
                        albums = data?.albums?.items ?: emptyList()
                    )

                    // Lưu vào recent searches
                    addToRecentSearches(query)
                }
                is Result.Error -> {
                    _error.value = "Tìm kiếm thất bại: ${result.message}"
                    _searchResults.value = null
                }
            }
            _isLoading.value = false
        }
    }

    fun searchImmediately(query: String) {
        _searchQuery.value = query
        performSearch(query)
    }

    fun clearSearch() {
        _searchQuery.value = ""
        clearSearchResults()
    }

    private fun clearSearchResults() {
        _searchResults.value = null
        _error.value = null
    }

    private fun addToRecentSearches(query: String) {
        val current = _recentSearches.value.toMutableList()
        current.remove(query) // Remove if exists
        current.add(0, query) // Add to beginning
        _recentSearches.value = current.take(10) // Keep only last 10
    }

    fun clearRecentSearches() {
        _recentSearches.value = emptyList()
    }

    fun clearError() {
        _error.value = null
    }
}

data class SearchResults(
    val tracks: List<TrackItem>,
    val artists: List<ArtistItem>,
    val albums: List<AlbumItem>
)