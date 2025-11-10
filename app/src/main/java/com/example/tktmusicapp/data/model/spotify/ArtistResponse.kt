package com.example.tktmusicapp.data.model.spotify
data class ArtistResponse(val id: String, val name: String, val images: List<Image>, val genres: List<String>)
data class ArtistItem(val id: String, val name: String, val images: List<Image>)