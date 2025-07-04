package com.example.droidchat.ui.feature.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.droidchat.data.repository.ChatsRepository
import com.example.droidchat.model.Chat
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val chatsRepository: ChatsRepository,
) : ViewModel() {

    private val _chatsListUiState = MutableStateFlow<ChatsListUiState>(ChatsListUiState.Loading)
    val chatsListUiState = _chatsListUiState.asStateFlow()

    init {
        getChats()
    }

    private fun getChats() {
        viewModelScope.launch {
            chatsRepository.getChats(
                offset = 0,
                limit = 10
            ).fold(
                onSuccess = { chats ->
                    _chatsListUiState.emit(ChatsListUiState.Success(chats))
                },
                onFailure = {
                    _chatsListUiState.emit(ChatsListUiState.Error)
                }
            )
        }
    }

    sealed interface ChatsListUiState {
        object Loading : ChatsListUiState
        data class Success(val chats: List<Chat>) : ChatsListUiState
        data object Error : ChatsListUiState
    }


}