package com.test.app.features.profile.presentation.screens.edit_profile

import androidx.lifecycle.SavedStateHandle
import com.test.app.core.BaseViewModel
import com.test.app.core.di.ViewModelAssistedFactory
import com.test.app.features.profile.domain.use_cases.GetProfileUseCase
import com.test.app.features.profile.domain.use_cases.UpdateProfileUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class EditProfileViewModel @AssistedInject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    @Assisted savedStateHandle: SavedStateHandle
) : BaseViewModel<EditProfileState, EditProfileAction>(
    EditProfileState.empty,
    EditProfileState.serializer(),
    savedStateHandle
) {

    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<EditProfileViewModel>

    override suspend fun reduce(
        prevState: EditProfileState,
        action: EditProfileAction
    ): Flow<EditProfileState> {
        return when (action) {
            EditProfileAction.DismissError -> flowOf(prevState.copy(error = null))
            EditProfileAction.Init -> handleInitAction(prevState)
            is EditProfileAction.UpdateAvatar -> flowOf(prevState.copy(avatar = action.avatar))
            is EditProfileAction.UpdateBirthday -> flowOf(prevState.copy(birthday = action.birthday))
            is EditProfileAction.UpdateAbout -> flowOf(prevState.copy(about = action.about))
            is EditProfileAction.UpdateCity -> flowOf(prevState.copy(city = action.city))
            EditProfileAction.Save -> handleSaveAction(prevState)
        }
    }

    private suspend fun handleInitAction(prevState: EditProfileState) =
        flowOf { getProfileUseCase() }
            .map {
                prevState.copy(
                    username = it.userName,
                    phone = it.phone,
                    name = it.name.orEmpty(),
                    birthday = it.birthday,
                    avatar = it.avatar,
                    about = it.about.orEmpty(),
                    city = it.city.orEmpty()
                )
            }

    private suspend fun handleSaveAction(prevState: EditProfileState) =
        flowOf<Unit> {
            updateProfileUseCase(
                username = prevState.username,
                phone = prevState.phone,
                name = prevState.name,
                about = prevState.about,
                birthday = prevState.birthday,
                city = prevState.city,
                avatar = prevState.avatar?.takeIf { it.base64.isNotEmpty() }
            )
        }.toState(
            onContent = {
                effect(EditProfileEffect.Back)
                prevState
            },
            onLoading = { prevState.copy(isLoading = true) },
            onError = { prevState.copy(error = it.localizedMessage, isLoading = false) }
        )
}