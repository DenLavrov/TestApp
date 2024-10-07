package com.test.app.features.auth.presentation.screens.code

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.test.app.R
import com.test.app.ui.theme.TestAppTheme

@Composable
fun CodeScreen(viewModel: CodeViewModel, phone: String, onRegister: () -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.dispatch(CodeAction.Init(phone))
        viewModel.effects.collect {
            when (it) {
                is CodeEffect.Register -> onRegister()
            }
        }
    }

    CodeScreenContent(state) { viewModel.dispatch(it) }
    if (state.error.isNullOrEmpty().not())
        AlertDialog(
            onDismissRequest = {
                viewModel.dispatch(CodeAction.DismissError)
            },
            confirmButton = {
                Text(
                    text = stringResource(R.string.ok),
                    modifier = Modifier.clickable {
                        viewModel.dispatch(CodeAction.DismissError)
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
private fun CodeScreenPreview() {
    TestAppTheme {
        CodeScreenContent(state = CodeState.empty, onNewAction = {})
    }
}

@Composable
private fun CodeScreenContent(state: CodeState, onNewAction: (CodeAction) -> Unit) {
    val focusManager = LocalFocusManager.current
    Scaffold { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(dimensionResource(R.dimen.space_large))
                .imePadding()
                .clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = { focusManager.clearFocus() })
        ) {
            OutlinedTextField(
                value = state.code,
                onValueChange = { onNewAction(CodeAction.Update(it)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                label = { Text(text = stringResource(R.string.auth_code)) },
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium)),
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                isError = state.isValid.not(),
                enabled = state.isLoading.not(),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                    onNewAction(CodeAction.Login)
                })
            )
            Button(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                onClick = {
                    focusManager.clearFocus()
                    onNewAction(CodeAction.Login)
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
                    Text(text = stringResource(R.string.auth_code_check))
            }
        }
    }
}