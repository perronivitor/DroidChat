package com.example.droidchat.navigation.extemsion

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

fun AnimatedContentTransitionScope<NavBackStackEntry>.slideInTo(
    direction: AnimatedContentTransitionScope.SlideDirection,
): EnterTransition {
    return slideIntoContainer(
        towards = direction,
        animationSpec = tween(durationMillis = 300)
    )
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.slideOutTo(
    direction: AnimatedContentTransitionScope.SlideDirection,
): ExitTransition {
    return slideOutOfContainer(
        towards = direction,
        animationSpec = tween(durationMillis = 300)
    )
}

