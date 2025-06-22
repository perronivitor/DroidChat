package com.example.droidchat.data.manager.selfuser

import android.content.Context
import com.example.droidchat.SelfUser
import com.example.droidchat.data.dataStore.selfUserStore
import com.example.droidchat.data.di.IoDispatcher
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface SelfUserManager {

    val selfUser: Flow<SelfUser>

    suspend fun saveSelfUser(
        firstName: String,
        lastName: String,
        profilePictureUrl: String,
        username: String,
        id: Int,
    )

    suspend fun cleanSelfUser()
}

class SelfUserManagerImpl @Inject constructor(
    @ApplicationContext context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : SelfUserManager {

    private val selfUserStore = context.selfUserStore

    override val selfUser: Flow<SelfUser>
        get() = selfUserStore.data

    override suspend fun saveSelfUser(
        firstName: String,
        lastName: String,
        profilePictureUrl: String,
        username: String,
        id: Int,
    ) {
        withContext(ioDispatcher) {
            selfUserStore.updateData { selfUser ->
                selfUser.toBuilder()
                    .setUsername(username)
                    .setProfilePictureUrl(profilePictureUrl)
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setId(id)
                    .build()
            }
        }
    }

    override suspend fun cleanSelfUser() {
        withContext(ioDispatcher) {
            selfUserStore.updateData { selfUser ->
                selfUser.toBuilder()
                    .clearUsername()
                    .clearProfilePictureUrl()
                    .clearFirstName()
                    .clearLastName()
                    .clearId()
                    .build()
            }
        }
    }

}