package com.test.app.features.profile.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.test.app.R
import com.test.app.core.views.SimpleScaffold
import com.test.app.ui.theme.TestAppTheme

@Composable
fun ProfileScreen(viewModel: ProfileViewModel, onEdit: () -> Unit, onBack: () -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.dispatch(ProfileAction.Init)
        viewModel.effects.collect {
            if (it is ProfileEffect.Back)
                onBack()
        }
    }

    ProfileScreenContent(state = state, onEdit, onBack) {
        viewModel.dispatch(ProfileAction.Logout)
    }

    if (state.error.isNullOrEmpty().not())
        AlertDialog(
            onDismissRequest = {
                viewModel.dispatch(ProfileAction.DismissError)
            },
            confirmButton = {
                Text(
                    text = stringResource(R.string.ok),
                    modifier = Modifier.clickable {
                        viewModel.dispatch(ProfileAction.DismissError)
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
private fun ProfilePreview() {
    TestAppTheme {
        ProfileScreenContent(
            state = ProfileState.empty,
            onEditClick = {},
            {}
        ) {
        }
    }
}

@Composable
private fun ProfileScreenContent(
    state: ProfileState,
    onEditClick: () -> Unit,
    onBack: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    SimpleScaffold(
        title = stringResource(R.string.profile_title),
        onBackClick = onBack,
        actions = {
            Text(
                text = stringResource(R.string.profile_logout),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = onLogoutClick
                ).padding(
                    end = dimensionResource(
                        R.dimen.space_small
                    )
                )
            )
        }
    ) {
        if (state.isLoading)
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    Modifier.align(Alignment.Center)
                )
            }
        else
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 24.dp, start = 24.dp, end = 24.dp)
            ) {
                Column(
                    Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 24.dp)
                        .verticalScroll(scrollState)
                ) {
                    AsyncImage(
                        model = state.avatar,
                        contentDescription = null,
                        contentScale = ContentScale.None,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                            .size(200.dp),
                        error = painterResource(id = R.drawable.camera)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = state.userName,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = state.phone,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    state.birthday?.takeIf { it.isNotEmpty() }?.let {
                        Text(
                            textAlign = TextAlign.Center,
                            text = stringResource(R.string.profile_birthday, it),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    state.zodiac?.let {
                        Text(
                            textAlign = TextAlign.Center,
                            text = stringResource(R.string.profile_zodiac, stringResource(it)),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    state.city?.takeIf { it.isNotEmpty() }?.let {
                        Text(
                            textAlign = TextAlign.Center,
                            text = stringResource(R.string.profile_city_format, it),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    state.about?.takeIf { it.isNotEmpty() }?.let {
                        Text(
                            textAlign = TextAlign.Center,
                            text = stringResource(R.string.profile_about_format, it),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
                Button(
                    onClick = onEditClick,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.profile_edit_button_text)
                    )
                }
            }
    }
}