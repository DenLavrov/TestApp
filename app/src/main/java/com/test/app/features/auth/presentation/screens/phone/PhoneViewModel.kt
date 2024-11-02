package com.test.app.features.auth.presentation.screens.phone

import androidx.lifecycle.SavedStateHandle
import com.test.app.core.presentation.vm.BaseViewModel
import com.test.app.features.auth.domain.exceptions.ValidationException
import com.test.app.features.auth.domain.use_cases.SendCodeUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import com.test.app.core.presentation.vm.ViewModelAssistedFactory

class PhoneViewModel @AssistedInject constructor(
    private val sendCodeUseCase: SendCodeUseCase,
    @Assisted savedStateHandle: SavedStateHandle
) : BaseViewModel<PhoneState, PhoneAction>(
    PhoneState.empty,
    PhoneState.serializer(),
    savedStateHandle
) {

    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<PhoneViewModel>

    override suspend fun reduce(prevState: PhoneState, action: PhoneAction): Flow<PhoneState> {
        return when (action) {
            PhoneAction.DismissError -> flowOf(prevState.copy(error = null))
            is PhoneAction.UpdatePhone -> flowOf(
                prevState.copy(
                    phone = action.phone,
                    isValid = true
                )
            )

            is PhoneAction.UpdateCountry -> flowOf(
                prevState.copy(
                    countryNumber = action.countryNumber,
                    countryCode = action.countryCode
                )
            )

            PhoneAction.SendCode -> handleSendCodeAction(prevState)
        }
    }

    private fun handleSendCodeAction(prevState: PhoneState) =
        flowOf { sendCodeUseCase(prevState.countryNumber + prevState.phone) }
            .toState(
                onError = {
                    if (it is ValidationException) {
                        prevState.copy(isValid = false, isLoading = false)
                    } else {
                        prevState.copy(error = it.localizedMessage, isLoading = false)
                    }
                },
                onLoading = { prevState.copy(isLoading = true) },
                onContent = {
                    effect(PhoneEffect.CodeSent(it))
                    prevState
                }
            )
}