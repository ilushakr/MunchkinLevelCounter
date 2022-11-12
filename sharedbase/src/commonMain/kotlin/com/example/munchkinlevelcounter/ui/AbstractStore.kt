package com.example.munchkinlevelcounter.ui

import com.example.munchkinlevelcounter.dispatchers.defaultDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

abstract class AbstractStore<STATE : IState>(initState: STATE) {
    protected val mutex = Mutex()
    protected val scope = CoroutineScope(defaultDispatcher)
    protected val state = MutableStateFlow(initState)

    fun state(): StateFlow<STATE> = state

    fun handle(action: IAction) {
        scope.launch {
            mutex.withLock {
                val newState = newState(state.value, action)
                moveToState(newState)
            }
        }
    }

    protected fun moveToState(newState: STATE) {
        if (newState == state.value) {
            return
        }

        state.value = newState
    }

    protected open fun newState(oldState: STATE, action: IAction): STATE {
        throw IllegalStateException("Can't handle action: $action")
    }
}
