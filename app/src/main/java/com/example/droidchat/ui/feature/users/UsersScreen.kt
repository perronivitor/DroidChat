package com.example.droidchat.ui.feature.users

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.droidchat.model.User
import com.example.droidchat.model.fake.user2
import com.example.droidchat.model.fake.user3
import com.example.droidchat.model.fake.user4
import com.example.droidchat.ui.theme.DroidChatTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun UsersRoute(
    viewModel: UsersViewModel = hiltViewModel(),
) {
    val pagingUsers = viewModel.usersFlow.collectAsLazyPagingItems()

    UsersScreen(
        pagingUsers = pagingUsers
    )
}

@Composable
fun UsersScreen(
    pagingUsers: LazyPagingItems<User>,
) {
    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            items(pagingUsers.itemCount) { index ->
                pagingUsers[index]?.let { user ->
                    Text(user.firstName)
                }
            }
        }
    }
}

@Preview
@Composable
private fun UsersScreenPreview() {
    DroidChatTheme {
        val usersFlow = flowOf(
            PagingData.from(
                listOf(
                    user2,
                    user3,
                    user4
                )
            )
        )
        UsersScreen(
            pagingUsers = usersFlow.collectAsLazyPagingItems()
        )
    }
}