package com.example.droidchat.data.repository.di

import com.example.droidchat.data.repository.AuthRepository
import com.example.droidchat.data.repository.AuthRepositoryImpl
import com.example.droidchat.data.repository.ChatsRepository
import com.example.droidchat.data.repository.ChatsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun bindAuthRepository(repository: AuthRepositoryImpl): AuthRepository

    @Binds
    fun bindChatsRepository(repository: ChatsRepositoryImpl): ChatsRepository
}