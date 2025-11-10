package com.example.tktmusicapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds @Singleton
    abstract fun bindMusicRepository(impl: com.example.tktmusicapp.repository.impl.MusicRepositoryImpl): com.example.tktmusicapp.repository.MusicRepository
}