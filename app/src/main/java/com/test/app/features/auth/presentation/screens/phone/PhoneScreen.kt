package com.test.app.features.auth.presentation.screens.phone

import android.telephony.TelephonyManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arpitkatiyarprojects.countrypicker.CountryPickerOutlinedTextField
import com.test.app.R
import com.test.app.ui.theme.TestAppTheme

@Composable
fun PhoneScreen(viewModel: PhoneViewModel, goToCode: (String) -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(true) {
        context.getSystemService(TelephonyManager::class.java)?.let {
            viewModel.dispatch(
                PhoneAction.Update(
                    state.phone,
                    state.countryNumber,
                    it.networkCountryIso ?: it.simCountryIso
                )
            )
        }
        viewModel.effects.collect {
            if (it is PhoneEffect.CodeSent) {
                goToCode(it.phone)
            }
        }
    }
    PhoneScreenContent(
        state = state,
        onUpdate = { countryCode, countryNumber, phone ->
            viewModel.dispatch(PhoneAction.Update(phone, countryNumber, countryCode))
        }
    ) {
        viewModel.dispatch(PhoneAction.SendCode)
    }

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
            state = PhoneState.empty,
            onUpdate = { _, _, _ -> }) {
        }
    }
}

@Composable
private fun PhoneScreenContent(
    state: PhoneState,
    onUpdate: (String, String, String) -> Unit,
    onSendCodeClick: () -> Unit
) {
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
            CountryPickerOutlinedTextField(
                mobileNumber = state.phone,
                enabled = state.isLoading.not(),
                onMobileNumberChange = { onUpdate(state.countryCode, state.countryNumber, it) },
                onCountrySelected = {
                    onUpdate(
                        it.countryCode,
                        it.countryPhoneNumberCode,
                        state.phone
                    )
                },
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium)),
                label = { Text(text = stringResource(R.string.auth_phone)) },
                isError = state.isValid.not(),
                defaultCountryCode = state.countryCode,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                onDone = {
                    focusManager.clearFocus()
                    onSendCodeClick()
                }
            )
            Button(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                onClick = {
                    focusManager.clearFocus()
                    onSendCodeClick()
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
                    Text(text = stringResource(R.string.auth_phone_send_code))
            }
        }
    }
}