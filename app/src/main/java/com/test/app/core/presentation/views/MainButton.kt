package com.test.app.core.presentation.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.test.app.ui.theme.dimens

@Composable
fun MainButton(
    text: String,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = isLoading.not(),
        contentPadding = PaddingValues(vertical = MaterialTheme.dimens.buttonPadding),
        shape = MaterialTheme.shapes.medium
    ) {
        if (isLoading)
            CircularProgressIndicator(
                modifier = Modifier.size(MaterialTheme.dimens.largeSpace),
                color = MaterialTheme.colorScheme.background
            )
        else
            Text(text = text)
    }
}