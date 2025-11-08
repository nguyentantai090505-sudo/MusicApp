package com.example.tktmusicapp.data.model.spotify
data class SearchResponse(val tracks: Tracks?, val artists: Artists?, val albums: Albums?)
data class Tracks(val items: List<TrackItem>)
data class Artists(val items: List<ArtistItem>)
data class Albums(val items: List<AlbumItem>)