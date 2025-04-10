package com.example.droidchat.data.manager

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.droidchat.data.dataStore.TokensKeys
import com.example.droidchat.data.dataStore.tokenDataStore
import com.example.droidchat.data.di.IoDispatcher
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class TokenManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : TokenManager {

    private val tokenDataStore = context.tokenDataStore

    override val accessToken: Flow<String>
        get() = tokenDataStore.data.map { preferences ->
            preferences[TokensKeys.ACCESS_TOKEN].orEmpty()
        }

    override suspend fun saveAccessToken(accessToken: String) {
        withContext(ioDispatcher) {
            tokenDataStore.edit { preferences ->
                preferences[TokensKeys.ACCESS_TOKEN] = accessToken
            }
        }
    }

    override suspend fun cleanAccessToken() {
        withContext(ioDispatcher) {
            tokenDataStore.edit { preferences ->
                preferences.remove(TokensKeys.ACCESS_TOKEN)
            }
        }
    }
}
