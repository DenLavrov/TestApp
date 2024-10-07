package com.test.app.features.auth.presentation.screens.code

import androidx.lifecycle.SavedStateHandle
import com.test.app.core.BaseViewModel
import com.test.app.features.auth.domain.models.ValidationError
import com.test.app.features.auth.domain.use_cases.LoginUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import com.test.app.core.di.ViewModelAssistedFactory

class CodeViewModel @AssistedInject constructor(
    private val loginUseCase: LoginUseCase,
    @Assisted savedStateHandle: SavedStateHandle
) :
    BaseViewModel<CodeState, CodeAction>(
        CodeState.empty,
        CodeState.serializer(),
        savedStateHandle
    ) {

    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<CodeViewModel>

    override fun reduce(prevState: CodeState, action: CodeAction): Flow<CodeState> {
        return when (action) {
            CodeAction.DismissError -> flowOf(prevState.copy(error = null))
            is CodeAction.Init -> flowOf(prevState.copy(phone = action.phone))
            is CodeAction.Update -> flowOf(prevState.copy(code = action.code, isValid = true))
            is CodeAction.Login -> loginUseCase(prevState.phone, prevState.code)
                .toState(
                    onContent = {
                        if (it.not())
                            effect(CodeEffect.Register)
                        prevState
                    },
                    onLoading = { prevState.copy(isLoading = true) },
                    onError = {
                        if (it is ValidationError) {
                            prevState.copy(isValid = false, isLoading = false)
                        } else {
                            prevState.copy(error = it.localizedMessage, isLoading = false)
                        }
                    }
                )
        }
    }
}