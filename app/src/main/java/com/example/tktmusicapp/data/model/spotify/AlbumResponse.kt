package com.example.tktmusicapp.data.model.spotify
data class AlbumResponse(val id: String, val name: String, val images: List<Image>, val artists: List<ArtistSimple>, val tracks: Tracks)
data class AlbumItem(val id: String, val name: String, val images: List<Image>, val artists: List<ArtistSimple>)
data class ArtistSimple(val id: String, val name: String)