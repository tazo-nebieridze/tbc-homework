package com.example.firstcomposeproject.presentation.utils

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> CollectSideEffect(flow: Flow<T>, sideEffect: suspend (T) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(sideEffect)
        }
    }
}
suspend fun SnackbarHostState.showSnackbar(
    message: String,
    actionLabel: String? = null,
    duration: SnackbarDuration = SnackbarDuration.Short,
    onActionPerformed: (() -> Unit)? = null
) {
    val result = showSnackbar(
        message = message,
        actionLabel = actionLabel,
        duration = duration
    )
    if (result == SnackbarResult.ActionPerformed) {
        onActionPerformed?.invoke()
    }
}