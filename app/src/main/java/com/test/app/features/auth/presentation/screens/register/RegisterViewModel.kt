package com.test.app.features.auth.presentation.screens.register

import androidx.lifecycle.SavedStateHandle
import com.test.app.core.BaseViewModel
import com.test.app.features.auth.domain.models.NameValidationError
import com.test.app.features.auth.domain.models.UserNameValidationError
import com.test.app.features.auth.domain.use_cases.RegisterUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import com.test.app.core.di.ViewModelAssistedFactory

class RegisterViewModel @AssistedInject constructor(
    private val registerUseCase: RegisterUseCase,
    @Assisted savedStateHandle: SavedStateHandle
) : BaseViewModel<RegisterState, RegisterAction>(
    RegisterState.empty,
    RegisterState.serializer(),
    savedStateHandle
) {

    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<RegisterViewModel>

    override suspend fun reduce(
        prevState: RegisterState,
        action: RegisterAction
    ): Flow<RegisterState> {
        return when (action) {
            RegisterAction.DismissError -> flowOf(prevState.copy(error = null))
            is RegisterAction.Init -> flowOf(
                prevState.copy(phone = action.phone)
            )

            is RegisterAction.UpdateUserName -> flowOf(
                prevState.copy(
                    userName = action.userName,
                    isUserNameValid = true
                )
            )

            is RegisterAction.UpdateName -> flowOf(
                prevState.copy(
                    name = action.name,
                    isNameValid = true
                )
            )

            is RegisterAction.Register -> handleRegisterAction(prevState)
        }
    }

    private fun handleRegisterAction(prevState: RegisterState) =
        flowOf { registerUseCase(prevState.phone, prevState.userName, prevState.name) }
            .toState(
                onLoading = { prevState.copy(isLoading = true) },
                onError = {
                    when (it) {
                        is UserNameValidationError -> prevState.copy(
                            isUserNameValid = false,
                            isNameValid = true,
                            isLoading = false
                        )

                        is NameValidationError -> prevState.copy(
                            isNameValid = false,
                            isUserNameValid = true,
                            isLoading = false
                        )

                        else -> prevState.copy(error = it.localizedMessage, isLoading = false)
                    }
                }
            )
}