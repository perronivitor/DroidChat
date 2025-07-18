package com.example.droidchat.data.mapper

import com.example.droidchat.data.network.model.PaginatedUserResponse
import com.example.droidchat.model.User

fun PaginatedUserResponse.asDomainModel() = this.users.map { userResponse ->
    User(
        id = userResponse.id,
        firstName = userResponse.firstName,
        lastName = userResponse.lastName,
        profilePictureUrl = userResponse.profilePictureUrl.orEmpty(),
        username = userResponse.username,
        self = false
    )
}
