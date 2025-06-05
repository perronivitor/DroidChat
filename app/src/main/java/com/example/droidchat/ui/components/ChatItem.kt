package com.example.droidchat.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.droidchat.R
import com.example.droidchat.ui.theme.DroidChatTheme

@Composable
fun ChatItem(modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        val (
            avatarRef,
            firstNameRef,
            lastMessageRef,
            lastMessageTimeRef,
            unreadCountRef,
        ) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.no_profile_image),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .size(60.dp)
                .constrainAs(avatarRef) {
                    top.linkTo(anchor = parent.top, margin = 16.dp)
                    start.linkTo(anchor = parent.start)
                    bottom.linkTo(anchor = parent.bottom, margin = 16.dp)
                }
        )

        Text(
            text = "Vitor",
            modifier = Modifier
                .constrainAs(firstNameRef) {
                    top.linkTo(anchor = avatarRef.top)
                    start.linkTo(anchor = avatarRef.end, margin = 16.dp)
                    end.linkTo(anchor = lastMessageTimeRef.start, margin = 16.dp)
                    bottom.linkTo(anchor = lastMessageRef.top)
                    width = Dimension.fillToConstraints
                },
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "Ola",
            modifier = Modifier.constrainAs(lastMessageRef) {
                top.linkTo(anchor = firstNameRef.bottom)
                start.linkTo(anchor = avatarRef.end, margin = 16.dp)
                end.linkTo(anchor = unreadCountRef.start, margin = 16.dp)
                bottom.linkTo(anchor = avatarRef.bottom)
                width = Dimension.fillToConstraints
            },
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = "10:10",
            modifier = Modifier
                .constrainAs(lastMessageTimeRef) {
                    top.linkTo(anchor = firstNameRef.top)
                    end.linkTo(anchor = parent.end)
                    bottom.linkTo(anchor = unreadCountRef.top)
                    width = Dimension.wrapContent
                },
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.bodySmall
        )

        Text(
            text = "2",
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(horizontal = 4.dp)
                .constrainAs(unreadCountRef) {
                    top.linkTo(anchor = lastMessageTimeRef.bottom)
                    end.linkTo(anchor = parent.end)
                    bottom.linkTo(anchor = lastMessageRef.bottom)
                    width = Dimension.wrapContent
                },
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview
@Composable
private fun ChatItemPreview() {
    DroidChatTheme {
        ChatItem()
    }
}