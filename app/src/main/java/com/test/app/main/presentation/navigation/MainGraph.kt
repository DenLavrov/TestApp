package com.test.app.main.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.test.app.core.di.CoreComponentHolder
import com.test.app.features.auth.di.AuthComponentHolder
import com.test.app.features.auth.presentation.navigation.Phone
import com.test.app.features.auth.presentation.navigation.authGraph
import com.test.app.features.chat.di.ChatComponentHolder
import com.test.app.features.chat.presentation.navigation.Chats
import com.test.app.features.chat.presentation.navigation.chatGraph
import com.test.app.features.profile.di.ProfileComponentHolder
import com.test.app.features.profile.presentation.navigation.Profile
import com.test.app.features.profile.presentation.navigation.profileGraph

@Composable
fun MainGraph(isAuthorized: Boolean, navController: NavHostController) {
    if (isAuthorized) {
        ProfileComponentHolder.init(CoreComponentHolder.get())
        ChatComponentHolder.init(ProfileComponentHolder.get().getProfileUseCase())
    }
    else
        AuthComponentHolder.init(CoreComponentHolder.get())
    NavHost(
        navController = navController, startDestination = if (isAuthorized) {
            Chats
        } else {
            Phone
        }
    ) {
        if (isAuthorized) {
            chatGraph(navController, {
                AuthComponentHolder.clear()
            }) {
                navController.navigate(Profile)
            }
            profileGraph(navController)
        } else {
            authGraph(navController) {
                ChatComponentHolder.clear()
                ProfileComponentHolder.clear()
            }
        }
    }
}