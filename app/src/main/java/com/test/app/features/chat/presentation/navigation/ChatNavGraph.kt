package com.test.app.features.chat.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.test.app.features.chat.presentation.di.ChatComponentHolder
import com.test.app.features.chat.presentation.screens.chat.ChatScreen
import com.test.app.features.chat.presentation.screens.chats.ChatsScreen
import getVm

fun NavGraphBuilder.chatGraph(navController: NavController,
                              beforeInit: () -> Unit,
                              onOpenProfile: () -> Unit) {
    composable<Chats> {
        beforeInit()
        ChatsScreen(
            viewModel = it.getVm(factory = ChatComponentHolder.get().chatsVmFactory()),
            onOpenProfile = onOpenProfile,
            onChatClick = { id, title, image -> navController.navigate(Chat(id, title, image)) }
        )
    }

    composable<Chat> {
        val route = it.toRoute<Chat>()
        ChatScreen(
            viewModel = it.getVm(factory = ChatComponentHolder.get().chatVmFactory()),
            chatId = route.id,
            title = route.title,
            image = route.image,
            onBackClick = navController::navigateUp
        )
    }
}