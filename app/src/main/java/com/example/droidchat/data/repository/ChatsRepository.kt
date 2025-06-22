package com.example.droidchat.data.repository

import com.example.droidchat.data.di.IoDispatcher
import com.example.droidchat.data.manager.selfuser.SelfUserManager
import com.example.droidchat.data.manager.token.TokenManager
import com.example.droidchat.data.network.NetworkDataSource
import com.example.droidchat.data.network.model.PaginationParams
import com.example.droidchat.model.Chat
import com.example.droidchat.model.User
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class ChatsRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val tokenManager: TokenManager,
    private val selfUserManager: SelfUserManager,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ChatsRepository {
    override suspend fun getChats(
        offset: Int,
        limit: Int,
    ): Result<List<Chat>> {
        return withContext(ioDispatcher) {
            runCatching {
                val token = tokenManager.accessToken.firstOrNull().orEmpty()
                val paginatedChatResponse = networkDataSource.getChats(
                    token = token,
                    paginationParams = PaginationParams(
                        offset = offset.toString(),
                        limit = limit.toString()
                    )
                )

                val selfUser = selfUserManager.selfUser.firstOrNull()
                paginatedChatResponse.chats.map { chatResponse ->
                    Chat(
                        id = chatResponse.id,
                        lastMessage = chatResponse.lastMessage,
                        members = chatResponse.members.map { userResponse ->
                            User(
                                id = userResponse.id,
                                firstName = userResponse.firstName,
                                lastName = userResponse.lastName,
                                profilePictureUrl = userResponse.profilePictureUrl.orEmpty(),
                                username = userResponse.username,
                                self = userResponse.id == selfUser?.id
                            )
                        },
                        unreadCount = chatResponse.unreadCount,
                        timestamp = ""
                    )
                }

            }
        }
    }

}

interface ChatsRepository {
    suspend fun getChats(offset: Int, limit: Int): Result<List<Chat>>
}