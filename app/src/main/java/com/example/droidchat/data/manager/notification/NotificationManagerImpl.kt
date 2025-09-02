package com.example.droidchat.data.manager.notification

import com.example.droidchat.data.di.IoDispatcher
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await

class NotificationManagerImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : NotificationManager {

    private val firebaseMessaging: FirebaseMessaging by lazy {
        Firebase.messaging
    }

    override suspend fun getToken(): String {
        return firebaseMessaging.token.await()
    }
}