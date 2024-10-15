package com.test.app.core.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope

@Composable
fun ObserveLifecycleEvents(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    block: suspend CoroutineScope.() -> Unit
) {
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(state, block)
    }
}