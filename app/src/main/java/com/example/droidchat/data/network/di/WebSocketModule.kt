package com.example.droidchat.data.network.di

import com.example.droidchat.data.network.websocket.ChatWebSocketService
import com.example.droidchat.data.network.websocket.ChatWebSocketServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface WebSocketModule {

    @Binds
    @Singleton
    fun bindWebSocketService(chatWebSocketService: ChatWebSocketServiceImpl): ChatWebSocketService

}