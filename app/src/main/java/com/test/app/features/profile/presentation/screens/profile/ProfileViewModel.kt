package com.test.app.features.profile.presentation.screens.profile

import androidx.lifecycle.SavedStateHandle
import com.test.app.core.BaseViewModel
import com.test.app.core.data.Storage
import com.test.app.core.di.ViewModelAssistedFactory
import com.test.app.features.profile.domain.use_cases.GetProfileUseCase
import com.test.app.features.profile.domain.use_cases.LogoutUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion

class ProfileViewModel @AssistedInject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val logoutUseCase: LogoutUseCase,
    @Assisted savedStateHandle: SavedStateHandle
) : BaseViewModel<ProfileState, ProfileAction>(
    ProfileState.empty,
    ProfileState.serializer(),
    savedStateHandle
) {

    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<ProfileViewModel>

    override fun reduce(prevState: ProfileState, action: ProfileAction): Flow<ProfileState> {
        return when (action) {
            ProfileAction.Logout -> logoutUseCase().ignoreState()
            ProfileAction.DismissError -> flowOf(prevState.copy(error = null))
                .onCompletion { effect(ProfileEffect.Back) }

            ProfileAction.Init -> getProfileUseCase()
                .toState(
                    onContent = {
                        prevState.copy(
                            birthday = it.birthday,
                            userName = it.userName,
                            avatar = it.avatar?.filename.orEmpty(),
                            about = it.about,
                            zodiac = it.zodiacSign,
                            phone = it.phone,
                            city = it.city,
                            isLoading = false
                        )
                    },
                    onLoading = { prevState.copy(isLoading = true) },
                    onError = { prevState.copy(error = it.localizedMessage) }
                )
        }
    }
}