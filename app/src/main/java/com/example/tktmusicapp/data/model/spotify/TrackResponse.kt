package com.example.tktmusicapp.data.model.spotify
data class TrackResponse(val id: String, val name: String, val duration_ms: Int, val preview_url: String?, val album: AlbumSimple, val artists: List<ArtistSimple>)
data class TrackItem(val id: String, val name: String, val duration_ms: Int, val preview_url: String?, val album: AlbumSimple, val artists: List<ArtistSimple>)
data class AlbumSimple(val id: String, val name: String, val images: List<Image>)
data class TracksResponse(val tracks: List<TrackItem>)