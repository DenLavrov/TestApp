package com.test.app.features.profile.presentation.screens.edit_profile

import android.content.Context
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.test.app.R
import com.test.app.core.views.SimpleScaffold
import com.test.app.features.auth.presentation.screens.phone.PhoneAction
import com.test.app.features.profile.data.models.AvatarData
import com.test.app.ui.theme.TestAppTheme
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.util.Base64
import java.util.Date
import java.util.Locale

@Composable
fun EditProfileScreen(viewModel: EditProfileViewModel, onBack: () -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        viewModel.dispatch(EditProfileAction.Init)
        viewModel.effects.collect {
            if (it is EditProfileEffect.Back)
                onBack()
        }
    }

    EditProfileContent(
        state,
        onSaveClick = {
            viewModel.dispatch(EditProfileAction.Save)
        },
        onBack = { showDialog = true },
        onUpdateClick = { avatar, birthday, about, city ->
            viewModel.dispatch(EditProfileAction.Update(avatar, birthday, about, city))
        }
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
            onSaveClick = { /*TODO*/ },
            onBack = {},
            onUpdateClick = { _, _, _, _ -> })
    }
}

@Composable
private fun EditProfileContent(
    state: EditProfileState,
    onSaveClick: () -> Unit,
    onUpdateClick: (AvatarData?, String?, String, String) -> Unit,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    BackHandler {
        onBack()
    }
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
                    .padding(bottom = 24.dp, start = 24.dp, end = 24.dp)
                    .clickable(
                        interactionSource = null,
                        indication = null,
                        onClick = { focusManager.clearFocus() }
                    )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                        .padding(top = 24.dp)
                        .weight(1f)
                ) {
                    AvatarContent(avatar = state.avatar) {
                        onUpdateClick(it, state.birthday, state.about, state.city)
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    BirthdayContent(birthday = state.birthday) {
                        onUpdateClick(state.avatar, it, state.about, state.city)
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    OutlinedTextField(
                        value = state.city,
                        onValueChange = {
                            onUpdateClick(state.avatar, state.birthday, state.about, it)
                        },
                        shape = RoundedCornerShape(12.dp),
                        label = { Text(text = stringResource(R.string.profile_city)) },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        })
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = state.about,
                        onValueChange = {
                            onUpdateClick(state.avatar, state.birthday, it, state.city)
                        },
                        shape = RoundedCornerShape(12.dp),
                        label = { Text(text = stringResource(R.string.profile_about)) },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                    )
                }
                Button(
                    onClick = {
                        focusManager.clearFocus()
                        onSaveClick()
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.edit_profile_save)
                    )
                }
            }
    }
}

@Composable
private fun AvatarContent(avatar: AvatarData?, onAvatarChange: (AvatarData?) -> Unit) {
    val context = LocalContext.current
    val pickImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            it?.let { uri ->
                onAvatarChange(
                    AvatarData(
                        uri.toString(), uri.getBase64ImageData(context)
                    )
                )
            }
        }

    AsyncImage(
        avatar?.filename,
        contentDescription = null,
        error = painterResource(R.drawable.camera),
        modifier = Modifier
            .size(200.dp)
            .clip(CircleShape)
            .background(Color.LightGray)
            .clickable { pickImageLauncher.launch("image/*") },
        contentScale = ContentScale.None
    )
}

private fun Uri.getBase64ImageData(context: Context): String {
    val contentResolver = context.contentResolver ?: return ""
    return contentResolver.openInputStream(this)?.use {
        Base64.getEncoder().encodeToString(it.readBytes())
    }.orEmpty()
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

    birthday?.let {
        Text(
            text = stringResource(R.string.profile_birthday, it),
            textAlign = TextAlign.Center
        )
    }
    Button(onClick = { isDialogOpen = true }) {
        Text(text = stringResource(id = R.string.edit_profile_pick_date))
    }
    if (isDialogOpen)
        DatePickerDialog(
            onDismissRequest = {
                isDialogOpen = false
            },
            confirmButton = {
                Button(
                    onClick = {
                        onBirthdayChange(convertMillisToDate(datePickerState.selectedDateMillis!!))
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

private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(Date(millis))
}