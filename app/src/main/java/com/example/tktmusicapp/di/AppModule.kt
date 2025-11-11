package com.example.tktmusicapp.di

import android.content.Context
import com.example.tktmusicapp.data.remote.FirebaseAuthService
import com.example.tktmusicapp.data.remote.FirebaseFirestoreService
import com.example.tktmusicapp.data.remote.SpotifyApiService
import com.example.tktmusicapp.domain.usecase.spotify.GetArtistTopTracksUseCase
import com.example.tktmusicapp.domain.usecase.spotify.GetNewReleasesUseCase
import com.example.tktmusicapp.domain.usecase.user.GetAvatarUrlUseCase
import com.example.tktmusicapp.domain.usecase.user.GetFavoriteArtistsUseCase
import com.example.tktmusicapp.domain.usecase.user.GetSavedTracksUseCase
import com.example.tktmusicapp.domain.usecase.user.RemoveTrackUseCase
import com.example.tktmusicapp.domain.usecase.user.SaveTrackUseCase
import com.example.tktmusicapp.domain.usecase.user.UploadAvatarUseCase
import com.example.tktmusicapp.repository.MusicRepository
import com.example.tktmusicapp.repository.impl.MusicRepositoryImpl
import com.example.tktmusicapp.viewmodel.PlayerViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // ====== FIREBASE CŨ – GIỮ NGUYÊN ======
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseAuthService(auth: FirebaseAuth) = FirebaseAuthService(auth)

    @Provides
    @Singleton
    fun provideFirebaseFirestoreService(
        firestore: FirebaseFirestore,
        auth: FirebaseAuth
    ) = FirebaseFirestoreService(firestore, auth)


    @Provides
    @Singleton
    fun provideSpotifyOkHttp(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun provideSpotifyApi(client: OkHttpClient): SpotifyApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.spotify.com/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpotifyApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePlayerViewModel(@ApplicationContext context: Context): PlayerViewModel {
        return PlayerViewModel(context)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object UseCaseModule {

        @Provides
        @Singleton
        fun provideGetFavoriteArtistsUseCase(): GetFavoriteArtistsUseCase {
            return GetFavoriteArtistsUseCase()
        }
        @Provides
        @Singleton
        fun provideGetNewReleasesUseCase(repository: MusicRepository): GetNewReleasesUseCase {
            return GetNewReleasesUseCase(repository)
        }

        @Provides
        @Singleton
        fun provideGetArtistTopTracksUseCase(repository: MusicRepository): GetArtistTopTracksUseCase {
            return GetArtistTopTracksUseCase(repository)
        }
        @Provides
        @Singleton
        fun provideGetSavedTracksUseCase(): GetSavedTracksUseCase {
            return GetSavedTracksUseCase()
        }

        @Provides
        @Singleton
        fun provideSaveTrackUseCase(): SaveTrackUseCase {
            return SaveTrackUseCase()
        }

        @Provides
        @Singleton
        fun provideRemoveTrackUseCase(): RemoveTrackUseCase {
            return RemoveTrackUseCase()
        }

        @Provides
        @Singleton
        fun provideMusicRepositoryImpl(api: SpotifyApiService): MusicRepositoryImpl =
            MusicRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideUploadAvatarUseCase(): UploadAvatarUseCase {
        return UploadAvatarUseCase()
    }

    @Provides
    @Singleton
    fun provideGetAvatarUrlUseCase(): GetAvatarUrlUseCase {
        return GetAvatarUrlUseCase()
    }
}
