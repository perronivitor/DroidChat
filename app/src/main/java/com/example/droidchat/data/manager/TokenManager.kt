package com.example.droidchat.data.manager

import kotlinx.coroutines.flow.Flow

interface TokenManager {

    val accessToken: Flow<String>

    suspend fun saveAccessToken(accessToken: String)

    suspend fun cleanAccessToken()
}
