package com.example.tktmusicapp.di

import com.example.tktmusicapp.data.remote.FirebaseAuthService
import com.example.tktmusicapp.data.remote.FirebaseFirestoreService
import com.example.tktmusicapp.data.remote.SpotifyApiService
import com.example.tktmusicapp.repository.MusicRepository
import com.example.tktmusicapp.repository.impl.MusicRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // ====== FIREBASE CŨ – GIỮ NGUYÊN ======
    @Provides @Singleton fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides @Singleton fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides @Singleton
    fun provideFirebaseAuthService(auth: FirebaseAuth) = FirebaseAuthService(auth)

    @Provides @Singleton
    fun provideFirebaseFirestoreService(
        firestore: FirebaseFirestore,
        auth: FirebaseAuth
    ) = FirebaseFirestoreService(firestore, auth)

    // ====== SPOTIFY MỚI – ĐÃ ĐƯA VÀO TRONG OBJECT ======
    @Provides @Singleton
    fun provideSpotifyOkHttp(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides @Singleton
    fun provideSpotifyApi(client: OkHttpClient): SpotifyApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.spotify.com/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpotifyApiService::class.java)
    }

    @Provides @Singleton
    fun provideMusicRepositoryImpl(api: SpotifyApiService): MusicRepositoryImpl = MusicRepositoryImpl(api)
}


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds @Singleton
    abstract fun bindMusicRepository(impl: MusicRepositoryImpl): MusicRepository
}