package com.example.droidchat.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.droidchat.data.database.DataBaseDataSource
import com.example.droidchat.data.database.DroidChatDatabase
import com.example.droidchat.data.database.entity.MessageEntity
import com.example.droidchat.data.di.IoDispatcher
import com.example.droidchat.data.manager.selfuser.SelfUserManager
import com.example.droidchat.data.mapper.asDomainModel
import com.example.droidchat.data.network.NetworkDataSource
import com.example.droidchat.data.network.model.PaginationParams
import com.example.droidchat.data.pagingsource.MessageRemoteMediator
import com.example.droidchat.model.Chat
import com.example.droidchat.model.ChatMessage
import java.time.Instant
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ChatsRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val databaseDataSource: DataBaseDataSource,
    private val database: DroidChatDatabase,
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

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedMessages(receiverId: Int): Flow<PagingData<ChatMessage>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            remoteMediator = MessageRemoteMediator(
                networkDataSource = networkDataSource,
                databaseDataSource = databaseDataSource,
                database = database,
                receiverId = receiverId
            ),
            pagingSourceFactory = {
                databaseDataSource.getPagedMessages(receiverId)
            }
        ).flow.map {
            it.map { messageEntity ->
                val selfUser = selfUserManager.selfUser.firstOrNull()
                messageEntity.asDomainModel(selfUserId = selfUser?.id)
            }
        }
    }

    override suspend fun sendMessage(receiverId: Int, message: String) {
        val selfUser = selfUserManager.selfUser.firstOrNull()

        val messageEntity = MessageEntity(
            id = null,
            isUnread = false,
            senderId = selfUser?.id ?: 0,
            receiverId = receiverId,
            text = message,
            timestamp = Instant.now().toEpochMilli()
        )

        databaseDataSource.insertMessages(listOf(messageEntity))

    }
}

