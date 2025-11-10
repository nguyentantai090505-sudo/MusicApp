package com.example.tktmusicapp.data.remote

import com.example.tktmusicapp.data.model.spotify.*
import retrofit2.http.*

interface SpotifyApiService {
    @GET("search")
    suspend fun search(
        @Header("Authorization") auth: String,
        @Query("q") query: String,
        @Query("type") type: String = "track,artist,album",
        @Query("limit") limit: Int = 20,
        @Query("market") market: String = "VN"
    ): SearchResponse
    @GET("browse/new-releases")
    suspend fun getNewReleases(
        @Header("Authorization") auth: String,
        @Query("limit") limit: Int = 20,
        @Query("country") country: String = "VN"
    ): NewReleasesResponse

    @FormUrlEncoded
    @POST("api/token")
    suspend fun getToken(@Header("Authorization") auth: String, @Field("grant_type") grantType: String): TokenResponse
    @GET("artists/{id}")
    suspend fun getArtist(@Header("Authorization") auth: String, @Path("id") id: String): ArtistResponse

    @GET("albums/{id}")
    suspend fun getAlbum(@Header    ("Authorization") auth: String, @Path("id") id: String): AlbumResponse

    @GET("tracks/{id}")
    suspend fun getTrack(@Header("Authorization") auth: String, @Path("id") id: String): TrackResponse

    @GET("artists/{id}/top-tracks")
    suspend fun getArtistTopTracks(@Header("Authorization") auth: String, @Path("id") id: String, @Query("market") market: String = "VN"): TracksResponse

    @GET("recommendations")
    suspend fun getRecommendations(@Header("Authorization") auth: String, @Query("seed_artists") seedArtists: String, @Query("limit") limit: Int = 30, @Query("market") market: String = "VN"): RecommendationsResponse

    @GET("artists/{id}/albums")
    suspend fun getArtistAlbums(@Header("Authorization") auth: String, @Path("id") id: String, @Query("limit") limit: Int = 20): AlbumsResponse

}