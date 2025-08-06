package com.example.droidchat.data.mapper

import com.example.droidchat.data.network.model.UserResponse
import com.example.droidchat.model.User

fun UserResponse.asDomainModel() = User(
    id = id,
    firstName = firstName,
    lastName = lastName,
    profilePictureUrl = profilePictureUrl.orEmpty(),
    username = username,
    self = false
)
