package com.example.droidchat.ui.feature.chats

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.droidchat.ui.theme.DroidChatTheme

@Composable
fun ChatsScreenRoute() {
    ChatsScreenScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsScreenScreen() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Chats")
                    }
                )
            }
        ) {paddingValues ->
            LazyColumn(
                contentPadding = paddingValues
            ) {
                items(100){
                    Text(text = "Item $it")
                }
            }
        }
}

@Preview
@Composable
private fun ChatsScreenScreenPreview() {
    DroidChatTheme {
        ChatsScreenScreen()
    }
}