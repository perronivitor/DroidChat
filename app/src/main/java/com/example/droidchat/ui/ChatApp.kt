package com.example.droidchat.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.droidchat.navigation.ChatNavHost
import com.example.droidchat.navigation.DroidChatNavigationState
import com.example.droidchat.ui.components.BottomNavigationMenu
import com.example.droidchat.ui.theme.Grey1

@Composable
fun ChatApp(navigationState: DroidChatNavigationState) {
    val topLevelDestination = remember(navigationState.topLevelDestination) {
        navigationState.topLevelDestination.toSet()
    }

    Scaffold(
        bottomBar = {
            if (navigationState.currentTopLevelDestination in topLevelDestination) {
                BottomNavigationMenu(navigationState = navigationState)
            }
        },
        contentColor = Grey1,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            ChatNavHost(navigationState = navigationState)
        }
    }
}