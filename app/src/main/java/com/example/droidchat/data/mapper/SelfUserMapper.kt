package com.example.droidchat.data.mapper

import com.example.droidchat.SelfUser
import com.example.droidchat.model.User

fun SelfUser.asDomainModel() = User(
    id = this.id,
    username = this.username,
    firstName = this.firstName,
    lastName = this.lastName,
    profilePictureUrl = this.profilePictureUrl,
    self = true
)