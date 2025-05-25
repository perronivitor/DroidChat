package com.example.droidchat.data.manager

import android.content.Context
import com.example.droidchat.data.dataStore.TokensKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SecureTokenManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : TokenManager {

    override val accessToken: Flow<String>
        get() = flowOf(CryptoManager.decryptData(context, TokensKeys.ACCESS_TOKEN.name))

    override suspend fun saveAccessToken(accessToken: String) {
        CryptoManager.encryptData(context, TokensKeys.ACCESS_TOKEN.name, accessToken)
    }

    override suspend fun cleanAccessToken() {
        CryptoManager.encryptData(context, TokensKeys.ACCESS_TOKEN.name, "")
    }

}