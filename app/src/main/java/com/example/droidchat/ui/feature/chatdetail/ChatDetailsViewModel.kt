package com.example.droidchat.ui.feature.chatdetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.cachedIn
import com.example.droidchat.data.repository.ChatsRepository
import com.example.droidchat.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ChatDetailViewModel @Inject constructor(
    private val chatRepository: ChatsRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val chatDetailRoute = savedStateHandle.toRoute<Route.ChatDetailRoute>()

    var messageText by mutableStateOf("")
        private set

    val pagingChatMessages = chatRepository.getPagedMessages(
        receiverId = chatDetailRoute.userId
    ).cachedIn(viewModelScope)

    fun onMessageChange(message: String) {
        messageText = message
    }

    fun sendMessage() {
        viewModelScope.launch {
            chatRepository.sendMessage(
                receiverId = chatDetailRoute.userId,
                message = messageText
            )
        }
    }
}