package com.test.app.features.profile.presentation.screens.edit_profile

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.test.app.R
import com.test.app.core.di.CoreComponentHolder
import com.test.app.core.presentation.utils.ObserveLifecycleEvents
import com.test.app.core.presentation.utils.convertMillisToYyyyMMddFormat
import com.test.app.core.presentation.utils.getBase64ImageData
import com.test.app.core.presentation.views.MainButton
import com.test.app.core.presentation.views.SimpleScaffold
import com.test.app.features.profile.domain.models.AvatarData
import com.test.app.ui.theme.TestAppTheme
import com.test.app.ui.theme.dimens
import kotlinx.coroutines.launch

@Composable
fun EditProfileScreen(viewModel: EditProfileViewModel, onBack: () -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(true) {
        viewModel.dispatch(EditProfileAction.Init)
    }
    ObserveLifecycleEvents {
        viewModel.effects.collect {
            if (it is EditProfileEffect.Back)
                onBack()
        }
    }

    EditProfileContent(
        state,
        onAction = viewModel::dispatch,
        onBack = { showDialog = true }
    )

    if (state.error.isNullOrEmpty().not())
        AlertDialog(
            onDismissRequest = {
                viewModel.dispatch(EditProfileAction.DismissError)
            },
            confirmButton = {
                Text(
                    text = stringResource(R.string.ok),
                    modifier = Modifier.clickable {
                        viewModel.dispatch(EditProfileAction.DismissError)
                    }
                )
            },
            text = {
                Text(text = state.error!!)
            }
        )

    if (showDialog && state.error.isNullOrEmpty())
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Text(
                    text = stringResource(R.string.dialog_confirm),
                    Modifier.clickable {
                        showDialog = false
                        onBack()
                    })
            },
            dismissButton = {
                Text(
                    text = stringResource(R.string.dialog_cancel),
                    Modifier.clickable {
                        showDialog = false
                    }
                )
            },
            title = {
                Text(
                    text = stringResource(R.string.quit_dialog_title),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(text = stringResource(R.string.quit_dialog_message))
            }
        )
}

@Composable
@Preview
private fun EditProfilePreview() {
    TestAppTheme {
        EditProfileContent(
            state = EditProfileState.empty,
            onAction = { /*TODO*/ },
            onBack = {}
        )
    }
}

@Composable
private fun EditProfileContent(
    state: EditProfileState,
    onAction: (EditProfileAction) -> Unit,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    BackHandler(onBack = onBack)
    SimpleScaffold(
        title = stringResource(R.string.profile_title),
        onBackClick = onBack
    ) {
        if (state.isLoading)
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    Modifier.align(Alignment.Center)
                )
            }
        else
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(
                        bottom = MaterialTheme.dimens.largeSpace,
                        start = MaterialTheme.dimens.largeSpace,
                        end = MaterialTheme.dimens.largeSpace
                    )
                    .clickable(
                        interactionSource = null,
                        indication = null,
                        onClick = focusManager::clearFocus
                    ),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.largeSpace)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                        .padding(top = MaterialTheme.dimens.largeSpace)
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.largeSpace)
                ) {
                    AvatarContent(avatar = state.avatar?.filename) {
                        onAction(EditProfileAction.UpdateAvatar(it))
                    }
                    BirthdayContent(birthday = state.birthday) {
                        onAction(EditProfileAction.UpdateBirthday(it))
                    }
                    OutlinedTextField(
                        value = state.city,
                        onValueChange = {
                            onAction(EditProfileAction.UpdateCity(it))
                        },
                        shape = MaterialTheme.shapes.medium,
                        label = { Text(text = stringResource(R.string.profile_city)) },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        })
                    )
                    OutlinedTextField(
                        value = state.about,
                        onValueChange = {
                            onAction(EditProfileAction.UpdateAbout(it))
                        },
                        shape = MaterialTheme.shapes.medium,
                        label = { Text(text = stringResource(R.string.profile_about)) },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                    )
                }
                MainButton(
                    text = stringResource(R.string.edit_profile_save),
                    onClick = {
                        focusManager.clearFocus()
                        onAction(EditProfileAction.Save)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
    }
}

@Composable
private fun AvatarContent(avatar: String?, onAvatarChange: (AvatarData?) -> Unit) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val pickImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            it?.let { uri ->
                coroutineScope.launch {
                    onAvatarChange(
                        AvatarData(
                            uri.toString(),
                            uri.getBase64ImageData(
                                context,
                                CoreComponentHolder.get().provideDispatchers()
                            )
                        )
                    )
                }
            }
        }

    AsyncImage(
        avatar,
        contentDescription = null,
        error = painterResource(R.drawable.camera),
        modifier = Modifier
            .size(MaterialTheme.dimens.profileImage)
            .clip(CircleShape)
            .background(Color.LightGray)
            .clickable {
                pickImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            },
        contentScale = ContentScale.None.takeIf { avatar.isNullOrEmpty() }
            ?: ContentScale.FillBounds
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BirthdayContent(birthday: String?, onBirthdayChange: (String) -> Unit) {
    val datePickerState = rememberDatePickerState()
    var isDialogOpen by remember {
        mutableStateOf(false)
    }
    val isDialogConfirmEnabled by remember {
        derivedStateOf { datePickerState.selectedDateMillis != null }
    }

    Column {
        birthday?.let {
            Text(
                text = stringResource(R.string.profile_birthday, it),
                textAlign = TextAlign.Center
            )
        }
        Button(onClick = { isDialogOpen = true }) {
            Text(text = stringResource(id = R.string.edit_profile_pick_date))
        }
    }
    if (isDialogOpen)
        DatePickerDialog(
            onDismissRequest = {
                isDialogOpen = false
            },
            confirmButton = {
                Button(
                    onClick = {
                        onBirthdayChange(convertMillisToYyyyMMddFormat(datePickerState.selectedDateMillis!!))
                        isDialogOpen = false
                    },
                    enabled = isDialogConfirmEnabled
                ) {
                    Text(text = stringResource(R.string.edit_profile_save))
                }
            },
            dismissButton = {
                Button(onClick = { isDialogOpen = false }) {
                    Text(text = stringResource(R.string.dialog_cancel))
                }
            }
        ) {
            DatePicker(datePickerState)
        }
}
