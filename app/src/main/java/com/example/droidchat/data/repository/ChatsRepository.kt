package com.example.droidchat.data.repository

import com.example.droidchat.model.Chat

interface ChatsRepository {
    suspend fun getChats(offset: Int, limit: Int): Result<List<Chat>>
}