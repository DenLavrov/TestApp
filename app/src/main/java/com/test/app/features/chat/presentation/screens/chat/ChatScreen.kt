package com.test.app.features.chat.presentation.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.addOutline
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.test.app.R
import com.test.app.core.views.SimpleScaffold
import com.test.app.features.chat.data.models.ChatMessage
import com.test.app.ui.theme.TestAppTheme
import com.test.app.ui.theme.chatBackground
import com.test.app.ui.theme.messageBackground
import java.time.OffsetDateTime

@Composable
fun ChatScreen(
    viewModel: ChatViewModel,
    chatId: String,
    title: String,
    image: String,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(chatId) {
        viewModel.dispatch(ChatAction.Init(chatId))
    }

    ChatScreenContent(
        state = state,
        title,
        image,
        { viewModel.dispatch(ChatAction.UpdateText(it)) },
        { viewModel.dispatch(ChatAction.Send(it)) },
        onBackClick
    )
}

@Composable
@Preview
private fun ChatPreview() {
    TestAppTheme {
        ChatScreenContent(
            state = ChatState(
                "",
                listOf(
                    ChatMessage(
                        1,
                        "Some message",
                        ChatMessage.Type.OUTGOING,
                        OffsetDateTime.now()
                    )
                ),
                ""
            ), title = "Some title", image = "",
            {},
            {}
        ) {

        }
    }
}

@Composable
private fun ChatScreenContent(
    state: ChatState,
    title: String,
    image: String,
    onTextUpdate: (String) -> Unit,
    onSendClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    SimpleScaffold(title, onBackClick, image) {
        Column(Modifier.background(MaterialTheme.colorScheme.chatBackground)) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        interactionSource = null,
                        indication = null,
                        onClick = { focusManager.clearFocus() }
                    ),
                reverseLayout = true
            ) {
                items(state.messages, contentType = { it.type }) {
                    when (it.type) {
                        ChatMessage.Type.OUTGOING -> Message(
                            text = it.message,
                            backgroundColor = MaterialTheme.colorScheme.messageBackground,
                            textColor = MaterialTheme.colorScheme.onSurface,
                            alignment = Alignment.CenterEnd,
                            orientation = TriangleEdgeShape.Orientation.RIGHT
                        )

                        ChatMessage.Type.INCOMING -> Message(
                            text = it.message,
                            backgroundColor = MaterialTheme.colorScheme.background,
                            textColor = MaterialTheme.colorScheme.onSurface,
                            alignment = Alignment.CenterStart,
                            orientation = TriangleEdgeShape.Orientation.LEFT
                        )
                    }
                }
            }
            TextField(
                value = state.text,
                onValueChange = onTextUpdate,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            focusManager.clearFocus()
                            onSendClick(state.text)
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.send_message),
                            contentDescription = null
                        )
                    }
                },
                placeholder = { Text(text = stringResource(R.string.chat_message_placeholder)) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedIndicatorColor = MaterialTheme.colorScheme.background,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.background
                ),
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(
                    onSend = {
                        focusManager.clearFocus()
                        onSendClick(state.text)
                    }
                )
            )
        }
    }
}

@Composable
@Preview
private fun MessagePreview() {
    TestAppTheme {
        Message(
            text = "Some text",
            backgroundColor = MaterialTheme.colorScheme.messageBackground,
            textColor = MaterialTheme.colorScheme.onBackground,
            alignment = Alignment.CenterEnd,
            orientation = TriangleEdgeShape.Orientation.RIGHT
        )
    }
}

class TriangleEdgeShape(
    private val offset: Int, private val cornerRadius: Float,
    private val orientation: Orientation
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val trianglePath = Path().apply {
            if (orientation == Orientation.RIGHT) {
                addOutline(
                    Outline.Rounded(
                        RoundRect(
                            size.toRect(),
                            CornerRadius(cornerRadius, cornerRadius),
                            CornerRadius(cornerRadius, cornerRadius),
                            CornerRadius.Zero,
                            CornerRadius(cornerRadius, cornerRadius)
                        )
                    )
                )
                moveTo(x = size.width, y = size.height - offset)
                lineTo(x = size.width, y = size.height)
                lineTo(x = size.width + offset, y = size.height)
            } else {
                addOutline(
                    Outline.Rounded(
                        RoundRect(
                            size.toRect(),
                            CornerRadius(cornerRadius, cornerRadius),
                            CornerRadius(cornerRadius, cornerRadius),
                            CornerRadius(cornerRadius, cornerRadius),
                            CornerRadius.Zero
                        )
                    )
                )
                moveTo(x = 0f, y = size.height - offset)
                lineTo(x = 0f, y = size.height)
                lineTo(x = 0f - offset, y = size.height)
            }
        }
        return Outline.Generic(trianglePath)
    }

    @Immutable
    enum class Orientation {
        LEFT,
        RIGHT
    }
}

@Composable
private fun Message(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    alignment: Alignment,
    orientation: TriangleEdgeShape.Orientation
) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 8.dp)
    ) {
        Box(
            Modifier
                .background(backgroundColor, TriangleEdgeShape(10, 16f, orientation))
                .padding(8.dp)
                .align(alignment)
        ) {
            Text(text = text, color = textColor)
        }
    }
}