package com.test.app.features.auth.presentation.screens.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.test.app.R
import com.test.app.core.views.SimpleScaffold
import com.test.app.ui.theme.TestAppTheme

@Composable
fun RegisterScreen(viewModel: RegisterViewModel, phone: String, onBack: () -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.dispatch(RegisterAction.Init(phone))
    }

    RegisterScreenContent(
        state,
        { userName, name -> viewModel.dispatch(RegisterAction.Update(userName, name)) },
        { viewModel.dispatch(RegisterAction.Register) },
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
            onUpdate = { _, _ -> },
            onRegisterClick = {}) {
        }
    }
}

@Composable
private fun RegisterScreenContent(
    state: RegisterState,
    onUpdate: (String, String) -> Unit,
    onRegisterClick: () -> Unit,
    onBack: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    SimpleScaffold(title = state.phone, onBackClick = onBack) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.space_large))
                .clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = { focusManager.clearFocus() })
        ) {
            Column(
                Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = state.userName,
                    onValueChange = { onUpdate(it, state.name) },
                    label = { Text(text = stringResource(R.string.registration_username)) },
                    shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium)),
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
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_medium)))
                OutlinedTextField(
                    value = state.name,
                    onValueChange = { onUpdate(state.userName, it) },
                    label = { Text(text = stringResource(R.string.registration_name)) },
                    shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium)),
                    modifier = Modifier
                        .fillMaxWidth(),
                    enabled = state.isLoading.not(),
                    isError = state.isNameValid.not(),
                    keyboardActions = KeyboardActions(onDone = { onRegisterClick() }),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )
            }
            Button(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                onClick = {
                    focusManager.clearFocus()
                    onRegisterClick()
                },
                enabled = state.isLoading.not(),
                contentPadding = PaddingValues(vertical = dimensionResource(R.dimen.button_padding)),
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium))
            ) {
                if (state.isLoading)
                    CircularProgressIndicator(
                        modifier = Modifier.size(dimensionResource(R.dimen.space_large)),
                        color = MaterialTheme.colorScheme.background
                    )
                else
                    Text(text = stringResource(R.string.registration_register_button))
            }
        }
    }
}