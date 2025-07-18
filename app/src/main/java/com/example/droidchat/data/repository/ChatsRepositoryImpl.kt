package com.example.droidchat.data.repository

import com.example.droidchat.data.di.IoDispatcher
import com.example.droidchat.data.manager.selfuser.SelfUserManager
import com.example.droidchat.data.manager.token.TokenManager
import com.example.droidchat.data.mapper.asDomainModel
import com.example.droidchat.data.network.NetworkDataSource
import com.example.droidchat.data.network.model.PaginationParams
import com.example.droidchat.model.Chat
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class ChatsRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val selfUserManager: SelfUserManager,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ChatsRepository {
    override suspend fun getChats(
        offset: Int,
        limit: Int,
    ): Result<List<Chat>> {
        return withContext(ioDispatcher) {
            runCatching {
                val paginatedChatResponse = networkDataSource.getChats(
                    paginationParams = PaginationParams(
                        offset = offset.toString(),
                        limit = limit.toString()
                    )
                )

                val selfUser = selfUserManager.selfUser.firstOrNull()
                paginatedChatResponse.asDomainModel(selfUserId = selfUser?.id)
            }
        }
    }
}

