package com.example.droidchat.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.droidchat.model.User
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUsers(limit: Int): Flow<PagingData<User>>
}

class UserRepositoryImpl @Inject constructor(
    private val userPagingSource: PagingSource<Int, User>,
) : UserRepository {
    override fun getUsers(limit: Int): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = limit,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { userPagingSource }
        ).flow
    }
}