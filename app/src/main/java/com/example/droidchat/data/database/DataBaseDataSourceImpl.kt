package com.example.droidchat.data.database

import androidx.paging.PagingSource
import com.example.droidchat.data.database.entity.MessageEntity
import javax.inject.Inject

class DataBaseDataSourceImpl @Inject constructor(
    database: DroidChatDatabase,
) : DataBaseDataSource {

    private val messageDao = database.messageDao()

    override fun getPagedMessages(receiverId: Int): PagingSource<Int, MessageEntity> {
        return messageDao.getPagedMessages(receiverId)
    }

    override suspend fun insertMessages(messages: List<MessageEntity>) {
        messageDao.insertMessages(messages)
    }

    override suspend fun deleteMessages(receiverId: Int) {
        messageDao.deleteMessages(receiverId)
    }

}