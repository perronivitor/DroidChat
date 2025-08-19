package com.example.droidchat.data.repository

import androidx.paging.PagingData
import com.example.droidchat.model.User
import kotlinx.coroutines.flow.Flow

private const val LIMIT = 10

interface UserRepository {

    suspend fun getUser(userId: Int): Result<User>

    fun getUsers(limit: Int = LIMIT): Flow<PagingData<User>>
}

