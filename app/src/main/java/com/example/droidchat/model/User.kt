package com.example.droidchat.model

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val profilePictureUrl: String,
    val username: String,
    val self: Boolean,
)

