package com.example.droidchat.data.database

import androidx.paging.PagingSource
import com.example.droidchat.data.database.entity.MessageEntity

interface DataBaseDataSource {

    fun getPagedMessages(receiverId: Int): PagingSource<Int, MessageEntity>

    suspend fun insertMessages(messages: List<MessageEntity>)

    suspend fun deleteMessages(receiverId: Int)

}
