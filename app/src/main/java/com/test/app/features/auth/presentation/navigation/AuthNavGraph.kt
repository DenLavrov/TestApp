package com.test.app.features.auth.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.toRoute
import com.test.app.features.auth.di.AuthComponentHolder
import com.test.app.features.auth.presentation.screens.code.CodeScreen
import com.test.app.features.auth.presentation.screens.phone.PhoneScreen
import com.test.app.features.auth.presentation.screens.register.RegisterScreen
import getVm

fun NavGraphBuilder.authGraph(navController: NavController, beforeInit: () -> Unit) {
    composable<Phone> {
        beforeInit()
        PhoneScreen(
            it.getVm(
                AuthComponentHolder.get().phoneVmFactory()
            )
        ) { phone -> navController.navigate(Code(phone)) }
    }

    composable<Code> {
        val phone = it.toRoute<Code>().phone
        CodeScreen(
            it.getVm(factory = AuthComponentHolder.get().codeVmFactory()),
            phone
        ) {
            navController.navigate(Register(phone), navOptions {
                popUpTo(Phone) {
                    inclusive = false
                }
            })
        }
    }

    composable<Register> {
        RegisterScreen(
            viewModel = it.getVm(factory = AuthComponentHolder.get().registerVmFactory()),
            it.toRoute<Register>().phone,
            navController::navigateUp
        )
    }
}