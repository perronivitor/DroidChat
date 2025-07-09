package com.example.droidchat.ui.feature.chats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.droidchat.R
import com.example.droidchat.model.Chat
import com.example.droidchat.ui.components.ChatItem
import com.example.droidchat.ui.feature.chats.ChatsViewModel.ChatsListUiState.Error
import com.example.droidchat.ui.feature.chats.ChatsViewModel.ChatsListUiState.Loading
import com.example.droidchat.ui.feature.chats.ChatsViewModel.ChatsListUiState.Success
import com.example.droidchat.ui.theme.DroidChatTheme

@Composable
fun ChatsScreenRoute(
    viewModel: ChatsViewModel = hiltViewModel(),
) {
    val chatsListUiState = viewModel.chatsListUiState.collectAsStateWithLifecycle()

    ChatsScreenScreen(
        chatsListUiState = chatsListUiState.value
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsScreenScreen(
    chatsListUiState: ChatsViewModel.ChatsListUiState,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = AnnotatedString.fromHtml(
                            stringResource(
                                id = R.string.feature_chats_greeting,
                                "Vitor"
                            )
                        ),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                expandedHeight = 100.dp
            )
        },
        containerColor = MaterialTheme.colorScheme.primary
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.extraLarge.copy(
                        bottomStart = CornerSize(0.dp),
                        bottomEnd = CornerSize(0.dp)
                    )
                )
                .clip(
                    shape = MaterialTheme.shapes.extraLarge.copy(
                        bottomStart = CornerSize(0.dp),
                        bottomEnd = CornerSize(0.dp)
                    )
                )
                .fillMaxSize()
        ) {
            when (chatsListUiState) {
                is Loading -> {
                    CircularProgressIndicator()
                }

                is Success -> {
                    ChatsListContent(chats = chatsListUiState.chats)
                }

                is Error -> {
                    Text(text = "Error")
                }
            }
        }
    }
}

@Composable
fun ChatsListContent(chats: List<Chat>) {
    LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp)) {
        itemsIndexed(chats) { index, chat ->
            ChatItem(chat = chat)
        }
    }
}

@Preview
@Composable
private fun ChatsScreenLoadingPreview() {
    DroidChatTheme {
        ChatsScreenScreen(
            chatsListUiState = Loading
        )
    }
}

@Preview
@Composable
private fun ChatsScreenSuccessPreview() {
    DroidChatTheme {
        ChatsScreenScreen(
            chatsListUiState = Success(
                chats = emptyList()
            )
        )
    }
}

@Preview
@Composable
private fun ChatsScreenErrorPreview() {
    DroidChatTheme {
        ChatsScreenScreen(
            chatsListUiState = Error
        )
    }
}