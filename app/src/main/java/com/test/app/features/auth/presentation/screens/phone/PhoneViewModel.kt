package com.test.app.features.auth.presentation.screens.phone

import androidx.lifecycle.SavedStateHandle
import com.test.app.core.BaseViewModel
import com.test.app.features.auth.domain.models.ValidationError
import com.test.app.features.auth.domain.use_cases.SendCodeUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import com.test.app.core.di.ViewModelAssistedFactory

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

    override fun reduce(prevState: PhoneState, action: PhoneAction): Flow<PhoneState> {
        return when (action) {
            PhoneAction.DismissError -> flowOf(prevState.copy(error = null))
            is PhoneAction.Update -> flowOf(
                prevState.copy(
                    phone = action.phone,
                    countryCode = action.countryCode,
                    countryNumber = action.countryNumber,
                    isValid = true
                )
            )

            PhoneAction.SendCode -> sendCodeUseCase(prevState.countryNumber + prevState.phone)
                .toState(
                    onError = {
                        if (it is ValidationError) {
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
    }
}