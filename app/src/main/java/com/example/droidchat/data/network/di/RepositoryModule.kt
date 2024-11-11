package com.example.droidchat.data.network.di

import com.example.droidchat.data.repository.AuthRepository
import com.example.droidchat.data.repository.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun provideAuthRepository(repository: AuthRepositoryImpl): AuthRepository
}