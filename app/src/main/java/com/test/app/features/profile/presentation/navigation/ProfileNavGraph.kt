package com.test.app.features.profile.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.test.app.core.di.CoreComponentHolder
import com.test.app.features.profile.presentation.di.ProfileComponentHolder
import com.test.app.features.profile.presentation.screens.edit_profile.EditProfileScreen
import com.test.app.features.profile.presentation.screens.profile.ProfileScreen
import getVm

fun NavGraphBuilder.profileGraph(navController: NavController) {
    composable<Profile> {
        ProfileScreen(it.getVm(ProfileComponentHolder.get().profileVmFactory()), onEdit = {
            navController.navigate(EditProfile)
        }, navController::navigateUp)
    }

    composable<EditProfile> {
        EditProfileScreen(
            it.getVm(factory = ProfileComponentHolder.get().editProfileVmFactory()),
            navController::navigateUp
        )
    }
}