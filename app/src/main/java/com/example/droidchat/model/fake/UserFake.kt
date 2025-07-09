package com.example.droidchat.model.fake

import com.example.droidchat.model.User

val user1 = User(
    id = 1,
    firstName = "Vitor",
    lastName = "Perroni",
    profilePictureUrl = "https://example.com/profile_vitor.jpg",
    username = "vitorperroni",
    self = true
)

val user2 = User(
    id = 2,
    firstName = "Maria",
    lastName = "Silva",
    profilePictureUrl = "https://example.com/profile_maria.jpg",
    username = "mariasilva",
    self = false
)

val user3 = User(
    id = 3,
    firstName = "Jose",
    lastName = "Silva",
    profilePictureUrl = "https://example.com/profile_maria.jpg",
    username = "joseee",
    self = false
)

val user4 = User(
    id = 4,
    firstName = "Camila",
    lastName = "Mota",
    profilePictureUrl = "https://example.com/profile_maria.jpg",
    username = "cammimota",
    self = false
)