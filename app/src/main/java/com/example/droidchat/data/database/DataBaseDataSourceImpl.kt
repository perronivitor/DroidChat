package com.example.droidchat.data.database

import androidx.paging.PagingSource
import com.example.droidchat.data.database.entity.MessageEntity
import com.example.droidchat.data.database.entity.MessageRemoteKeyEntity
import javax.inject.Inject

class DataBaseDataSourceImpl @Inject constructor(
    database: DroidChatDatabase,
) : DataBaseDataSource {

    private val messageDao = database.messageDao()
    private val messageRemoteKeyDao = database.messageRemoteKeyDao()

    override fun getPagedMessages(receiverId: Int): PagingSource<Int, MessageEntity> {
        return messageDao.getPagedMessages(receiverId)
    }

    override suspend fun insertMessages(messages: List<MessageEntity>) {
        messageDao.insertMessages(messages)
    }

    override suspend fun deleteMessages(receiverId: Int) {
        messageDao.deleteMessages(receiverId)
    }

    override suspend fun getMessageRemoteKey(receiverId: Int): MessageRemoteKeyEntity? {
        return messageRemoteKeyDao.getRemoteKey(receiverId)
    }

    override suspend fun insertMessageRemoteKey(remoteKey: MessageRemoteKeyEntity) {
        messageRemoteKeyDao.insertRemoteKey(remoteKey)
    }

    override suspend fun clearMessageRemoteKey(receiverId: Int) {
        messageRemoteKeyDao.clearMessageRemoteKey(receiverId)
    }
}