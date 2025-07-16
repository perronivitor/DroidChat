package com.example.droidchat.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.droidchat.ui.theme.DroidChatTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun ChatItemShimmer(modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .shimmer()
    ) {
        val (
            avatarRef,
            firstNameRef,
            lastMessageRef,
            lastMessageTimeRef,
            unreadCountRef,
        ) = createRefs()

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(60.dp)
                .background(Color.Gray)
                .constrainAs(avatarRef) {
                    top.linkTo(anchor = parent.top, margin = 16.dp)
                    start.linkTo(anchor = parent.start)
                    bottom.linkTo(anchor = parent.bottom, margin = 16.dp)
                },
        )

        Box(
            modifier = Modifier
                .height(16.dp)
                .background(Color.Gray)
                .constrainAs(firstNameRef) {
                    top.linkTo(anchor = avatarRef.top)
                    start.linkTo(anchor = avatarRef.end, margin = 16.dp)
                    end.linkTo(anchor = lastMessageTimeRef.start, margin = 16.dp)
                    bottom.linkTo(anchor = lastMessageRef.top)
                    width = Dimension.fillToConstraints
                }
        )

        Box(
            modifier = Modifier
                .height(16.dp)
                .background(Color.Gray)
                .constrainAs(lastMessageRef) {
                top.linkTo(anchor = firstNameRef.bottom)
                start.linkTo(anchor = avatarRef.end, margin = 16.dp)
                end.linkTo(anchor = unreadCountRef.start, margin = 16.dp)
                bottom.linkTo(anchor = avatarRef.bottom)
                width = Dimension.fillToConstraints
            },
        )

        Box(
            modifier = Modifier
                .size(16.dp)
                .background(Color.Gray)
                .constrainAs(lastMessageTimeRef) {
                    top.linkTo(anchor = firstNameRef.top)
                    end.linkTo(anchor = parent.end)
                    bottom.linkTo(anchor = firstNameRef.bottom)
                    width = Dimension.wrapContent
                }
        )

        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(Color.Gray)
                .padding(horizontal = 4.dp)
                .constrainAs(unreadCountRef) {
                    top.linkTo(anchor = lastMessageTimeRef.bottom)
                    end.linkTo(anchor = parent.end)
                    bottom.linkTo(anchor = lastMessageRef.bottom)
                    width = Dimension.wrapContent
                }
        )
    }
}

@Preview
@Composable
private fun ChatItemShimmerPreview() {
    DroidChatTheme {
        ChatItemShimmer()
    }
}