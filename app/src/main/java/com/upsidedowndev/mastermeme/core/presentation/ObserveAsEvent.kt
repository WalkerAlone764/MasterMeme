package com.upsidedowndev.mastermeme.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@Composable
fun<T> ObserveAsEvent(
    flow: Flow<T>,
    onEvent: (T) -> Unit
) {
    val lifecycleowner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleowner.lifecycle) {
        lifecycleowner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                flow.collect(onEvent)
            }
        }
    }
}