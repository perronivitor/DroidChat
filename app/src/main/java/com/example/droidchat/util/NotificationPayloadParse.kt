package com.example.droidchat.util

import com.example.droidchat.model.NotificationData
import javax.inject.Inject
import kotlinx.serialization.json.Json

class NotificationPayloadParse @Inject constructor() {

    fun parse(notificationPayloadJsonString: String): NotificationData {
        return Json.decodeFromString(notificationPayloadJsonString)
    }
}