package com.test.app.main.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.test.app.core.BaseViewModel
import com.test.app.core.data.Storage
import com.test.app.core.di.ViewModelAssistedFactory
import com.test.app.main.data.repository.IMainRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class MainViewModel @AssistedInject constructor(
    private val mainRepository: IMainRepository,
    storage: Storage,
    @Assisted savedStateHandle: SavedStateHandle
) : BaseViewModel<MainState, MainAction>(
    MainState(storage.getString(Storage.TOKEN_KEY).isNullOrEmpty().not()),
    MainState.serializer(),
    savedStateHandle
) {

    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<MainViewModel>

    init {
        viewModelScope.launch {
            mainRepository.isAuthorized.collect {
                dispatch(MainAction.Update(it))
            }
        }
    }

    override fun reduce(prevState: MainState, action: MainAction): Flow<MainState> {
        return when (action) {
            is MainAction.Update -> flowOf(prevState.copy(isAuthorized = action.isAuthorized))
        }
    }
}