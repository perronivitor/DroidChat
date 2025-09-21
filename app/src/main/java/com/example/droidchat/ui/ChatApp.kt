package com.example.droidchat.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
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
        contentColor = Grey1
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .consumeWindowInsets(innerPadding)
                .padding(innerPadding)
                .imePadding()
                .fillMaxSize()
        ) {
            ChatNavHost(navigationState = navigationState)
        }
    }
}