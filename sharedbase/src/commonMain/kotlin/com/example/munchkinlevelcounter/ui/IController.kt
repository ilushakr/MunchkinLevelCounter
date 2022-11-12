package com.example.munchkinlevelcounter.ui

import com.example.munchkinlevelcounter.dispatchers.defaultDispatcher
import io.ktor.utils.io.core.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

interface IController<STATE : IState> : KoinComponent {
    val store: AbstractStore<STATE>
    val dispatcher: Dispatcher

    fun scope(block: suspend () -> Unit) = CoroutineScope(defaultDispatcher).launch { block() }

    fun state() = store.state()

    fun onChange(provideNewState: ((STATE) -> Unit)): Closeable {

        val job = Job()

        state().onEach {
            provideNewState(it)
        }.launchIn(
            CoroutineScope(defaultDispatcher)
        )

        return object : Closeable {
            override fun close() {
                job.cancel()
            }
        }
    }
}
