package com.test.app.features.chat.presentation.screens.chats

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.test.app.R
import com.test.app.core.presentation.views.SimpleScaffold
import com.test.app.features.chat.domain.models.Chat
import com.test.app.ui.theme.TestAppTheme
import com.test.app.ui.theme.dimens
import java.util.UUID

@Composable
fun ChatsScreen(
    viewModel: ChatsViewModel,
    onChatClick: (String, String, String) -> Unit,
    onOpenProfile: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.dispatch(ChatsAction.Init)
    }

    ChatsScreenContent(state = state, onOpenProfile, onChatClick)
}

@Composable
@Preview
private fun ChatsScreenPreview() {
    TestAppTheme {
        ChatsScreenContent(
            state = ChatsState(
                listOf(
                    Chat(
                        "https://cdn.pixabay.com/photo/2016/10/10/14/46/icon-1728549_640.jpg",
                        "Chat 1",
                        "Some message...",
                        UUID.randomUUID().toString()
                    ),
                    Chat(
                        "https://cdn.pixabay.com/photo/2016/10/10/14/46/icon-1728549_640.jpg",
                        "Chat 2",
                        "Some message...",
                        UUID.randomUUID().toString()
                    ),
                    Chat(
                        "https://cdn.pixabay.com/photo/2016/10/10/14/46/icon-1728549_640.jpg",
                        "Chat 3",
                        "Some message...",
                        UUID.randomUUID().toString()
                    ),
                    Chat(
                        "https://cdn.pixabay.com/photo/2016/10/10/14/46/icon-1728549_640.jpg",
                        "Chat 4",
                        "Some message...",
                        UUID.randomUUID().toString()
                    )
                ),
                "https://cdn.pixabay.com/photo/2016/10/10/14/46/icon-1728549_640.jpg",
            ),
            {},
            { _, _, _ -> }
        )
    }
}

@Composable
private fun ChatsScreenContent(
    state: ChatsState,
    onOpenProfile: () -> Unit,
    onItemClick: (String, String, String) -> Unit
) {
    SimpleScaffold(
        title = stringResource(R.string.chats_title),
        image = state.avatar,
        onImageClick = onOpenProfile,
        imagePlaceholder = painterResource(R.drawable.avatar_placeholder)
    ) {
        LazyColumn {
            item { Spacer(modifier = Modifier.height(MaterialTheme.dimens.smallSpace)) }
            items(state.data, key = { it.id }) {
                Row(
                    Modifier
                        .clickable {
                            onItemClick(it.id, it.title, it.image)
                        }
                        .fillMaxWidth()
                        .padding(
                            vertical = MaterialTheme.dimens.smallSpace,
                            horizontal = MaterialTheme.dimens.largeSpace
                        ),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.largeSpace)
                ) {
                    AsyncImage(
                        model = it.image,
                        contentDescription = null,
                        modifier = Modifier
                            .size(MaterialTheme.dimens.chatImage)
                            .clip(CircleShape),
                        contentScale = ContentScale.FillBounds,
                        error = painterResource(R.drawable.placeholder_error)
                    )
                    Column {
                        Text(
                            text = it.title,
                            style = MaterialTheme.typography.titleLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = it.message,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(MaterialTheme.dimens.smallSpace)) }
        }
    }
}