package com.test.app.features.auth.presentation.screens.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.test.app.R
import com.test.app.core.presentation.views.MainButton
import com.test.app.core.presentation.views.SimpleScaffold
import com.test.app.ui.theme.TestAppTheme
import com.test.app.ui.theme.dimens

@Composable
fun RegisterScreen(viewModel: RegisterViewModel, phone: String, onBack: () -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.dispatch(RegisterAction.Init(phone))
    }

    RegisterScreenContent(
        state,
        viewModel::dispatch,
        onBack
    )

    if (state.error.isNullOrEmpty().not())
        AlertDialog(
            onDismissRequest = {
                viewModel.dispatch(RegisterAction.DismissError)
            },
            confirmButton = {
                Text(
                    text = stringResource(R.string.ok),
                    modifier = Modifier.clickable {
                        viewModel.dispatch(RegisterAction.DismissError)
                    }
                )
            },
            text = {
                Text(text = state.error!!)
            }
        )
}

@Preview
@Composable
private fun RegisterScreenPreview() {
    TestAppTheme {
        RegisterScreenContent(
            state = RegisterState.empty,
            onAction = {}) {
        }
    }
}

@Composable
private fun RegisterScreenContent(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
    onBack: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    SimpleScaffold(title = state.phone, onBackClick = onBack) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(MaterialTheme.dimens.largeSpace)
                .clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = focusManager::clearFocus
                )
        ) {
            Column(
                Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.mediumSpace)
            ) {
                OutlinedTextField(
                    value = state.userName,
                    onValueChange = { onAction(RegisterAction.UpdateUserName(it)) },
                    label = { Text(text = stringResource(R.string.registration_username)) },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .fillMaxWidth(),
                    isError = state.isUserNameValid.not(),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(
                            FocusDirection.Down
                        )
                    }),
                    enabled = state.isLoading.not(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )
                OutlinedTextField(
                    value = state.name,
                    onValueChange = { onAction(RegisterAction.UpdateName(it)) },
                    label = { Text(text = stringResource(R.string.registration_name)) },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .fillMaxWidth(),
                    enabled = state.isLoading.not(),
                    isError = state.isNameValid.not(),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                        onAction(RegisterAction.Register)
                    }),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )
            }
            MainButton(
                text = stringResource(R.string.registration_register_button),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                isLoading = state.isLoading,
                onClick = {
                    focusManager.clearFocus()
                    onAction(RegisterAction.Register)
                }
            )
        }
    }
}