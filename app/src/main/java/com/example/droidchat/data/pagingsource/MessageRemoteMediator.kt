package com.example.droidchat.data.pagingsource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.droidchat.data.database.DataBaseDataSource
import com.example.droidchat.data.database.DroidChatDatabase
import com.example.droidchat.data.database.entity.MessageEntity
import com.example.droidchat.data.database.entity.MessageRemoteKeyEntity
import com.example.droidchat.data.mapper.asEntityModel
import com.example.droidchat.data.network.NetworkDataSource
import com.example.droidchat.data.network.model.PaginationParams

@OptIn(ExperimentalPagingApi::class)
class MessageRemoteMediator(
    private val networkDataSource: NetworkDataSource,
    private val databaseDataSource: DataBaseDataSource,
    private val database: DroidChatDatabase,
    private val receiverId: Int, // ID do usuário para o qual os mensagens serão carregadas
) : RemoteMediator<Int, MessageEntity>() {

    override suspend fun load(
        loadType: LoadType, // indica o tipo de carregamento (refresh, append, prepend)
        state: PagingState<Int, MessageEntity>, // contém informações sobre a página atual e a lista de itens carregados
    ): MediatorResult {
        return try {
            val offset = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = databaseDataSource.getMessageRemoteKey(receiverId)
                    remoteKey?.nextOffset
                        ?: return MediatorResult.Success(endOfPaginationReached = true)

                }
            }

            val limit = state.config.pageSize
            val paginationParams = PaginationParams(
                offset = offset.toString(),
                limit = limit.toString()
            )

            val response = networkDataSource.getMessages(
                receiverId = receiverId,
                paginationParams = paginationParams
            )

            val entities = response.asEntityModel()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    databaseDataSource.clearMessageRemoteKey(receiverId)
                    databaseDataSource.deleteMessages(receiverId)
                }

                val nextOffSet = if (response.hasMore) offset + limit else null
                databaseDataSource.insertMessageRemoteKey(
                    remoteKey = MessageRemoteKeyEntity(
                        receiverId = receiverId,
                        nextOffset = nextOffSet
                    )
                )

                databaseDataSource.insertMessages(messages = entities)
            }

            MediatorResult.Success(endOfPaginationReached = !response.hasMore)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}