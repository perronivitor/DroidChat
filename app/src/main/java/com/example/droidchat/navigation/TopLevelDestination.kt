package com.example.droidchat.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.droidchat.R
import kotlin.reflect.KClass

enum class TopLevelDestination(
    @StringRes val titleTexRes: Int?,
    @DrawableRes val iconRes: Int?,
    val route: KClass<*>,
) {
    CHATS(
        titleTexRes = R.string.bottom_navigation_item_chats,
        iconRes = R.drawable.ic_bottom_nav_chats,
        route = Route.ChatsRoute::class
    ),
    PLUS_BUTTON(
        titleTexRes = null,
        iconRes = null,
        route = Route.UsersRoute::class
    ),
    PROFILE(
        titleTexRes = R.string.bottom_navigation_item_profile,
        iconRes = R.drawable.ic_bottom_nav_profile,
        route = Route.ProfileRoute::class
    )
}