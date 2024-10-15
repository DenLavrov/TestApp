package com.test.app.features.auth.presentation.screens.phone

import android.telephony.TelephonyManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arpitkatiyarprojects.countrypicker.CountryPickerOutlinedTextField
import com.test.app.R
import com.test.app.core.presentation.utils.ObserveLifecycleEvents
import com.test.app.core.presentation.views.MainButton
import com.test.app.ui.theme.TestAppTheme
import com.test.app.ui.theme.dimens

@Composable
fun PhoneScreen(viewModel: PhoneViewModel, goToCode: (String) -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(true) {
        context.getSystemService(TelephonyManager::class.java)?.let {
            viewModel.dispatch(
                PhoneAction.UpdateCountry(
                    state.countryNumber,
                    it.networkCountryIso ?: it.simCountryIso
                )
            )
        }
    }
    ObserveLifecycleEvents {
        viewModel.effects.collect {
            if (it is PhoneEffect.CodeSent) {
                goToCode(it.phone)
            }
        }
    }
    PhoneScreenContent(
        state = state,
        onAction = viewModel::dispatch
    )

    if (state.error.isNullOrEmpty().not())
        AlertDialog(
            onDismissRequest = {
                viewModel.dispatch(PhoneAction.DismissError)
            },
            confirmButton = {
                Text(
                    text = stringResource(R.string.ok),
                    modifier = Modifier.clickable {
                        viewModel.dispatch(PhoneAction.DismissError)
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
private fun PhoneScreenPreview() {
    TestAppTheme {
        PhoneScreenContent(
            state = PhoneState.empty
        ) {}
    }
}

@Composable
private fun PhoneScreenContent(
    state: PhoneState,
    onAction: (PhoneAction) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Scaffold { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(MaterialTheme.dimens.largeSpace)
                .imePadding()
                .clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = focusManager::clearFocus
                )
        ) {
            CountryPickerOutlinedTextField(
                mobileNumber = state.phone,
                enabled = state.isLoading.not(),
                onMobileNumberChange = { onAction(PhoneAction.UpdatePhone(it)) },
                onCountrySelected = {
                    onAction(PhoneAction.UpdateCountry(it.countryPhoneNumberCode, it.countryCode))
                },
                shape = MaterialTheme.shapes.medium,
                label = { Text(text = stringResource(R.string.auth_phone)) },
                isError = state.isValid.not(),
                defaultCountryCode = state.countryCode,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                onDone = {
                    focusManager.clearFocus()
                    onAction(PhoneAction.SendCode)
                }
            )
            MainButton(
                text = stringResource(R.string.auth_phone_send_code),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                isLoading = state.isLoading,
                onClick = {
                    focusManager.clearFocus()
                    onAction(PhoneAction.SendCode)
                }
            )
        }
    }
}