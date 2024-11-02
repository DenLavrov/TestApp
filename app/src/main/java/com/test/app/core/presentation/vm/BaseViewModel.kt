package com.test.app.core.presentation.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.app.core.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.KSerializer

abstract class BaseViewModel<T, Action>(
    initialState: T,
    serializer: KSerializer<T>,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private companion object {
        const val STATE_KEY = "STATE_KEY"
    }

    private val _state = MutableStateFlow(savedStateHandle.get<String>(STATE_KEY)?.let {
        json.decodeFromString(serializer, it)
    } ?: initialState)
    val state = _state
        .onEach {
            savedStateHandle[STATE_KEY] = json.encodeToString(serializer, it)
        }.stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = initialState
        )

    private val _effects = MutableSharedFlow<Any>(1)
    val effects = _effects.asSharedFlow()

    val actualState: T
        get() = state.value

    fun dispatch(action: Action) {
        viewModelScope.launch {
            _state.emitAll(reduce(state.value, action))
        }
    }

    protected suspend fun effect(effect: Any) = _effects.emit(effect)

    protected abstract suspend fun reduce(prevState: T, action: Action): Flow<T>

    protected fun ignoreState() = flowOf(actualState)

    protected inline fun ignoreState(crossinline action: suspend () -> Unit) = flow {
        action()
        emit(actualState)
    }

    protected inline fun <S> flowOf(crossinline action: suspend () -> S) = flow { emit(action()) }

    protected fun <S> Flow<S>.toState(
        onLoading: (() -> T)? = null,
        onError: (suspend (error: Throwable) -> T)? = null,
        onContent: suspend (data: S) -> T = { actualState }
    ): Flow<T> =
        map { onContent(it) }
            .onStart { onLoading?.let { emit(it.invoke()) } }
            .catch { error ->
                onError?.let { emit(it.invoke(error)) }
            }
}
