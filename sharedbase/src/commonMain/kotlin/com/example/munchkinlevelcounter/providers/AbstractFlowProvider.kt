package com.example.munchkinlevelcounter.providers

import com.example.munchkinlevelcounter.dispatchers.defaultDispatcher
import com.example.munchkinlevelcounter.objects.ResultState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow

abstract class AbstractFlowProvider {

    protected val scope = CoroutineScope(defaultDispatcher)

    protected fun <RESULT> resultFlowSafe(
        initialData: RESULT? = null,
        request: suspend () -> RESULT
    ) = flow {
        emit(ResultState.Pending(data = initialData))

        val result = try {
            ResultState.Success(data = request())
        }catch (e: Throwable){
            ResultState.Fail(error = e)
        }

        emit(result)
    }

    protected fun <RESULT> resultFlow(
        initialData: RESULT? = null,
        request: suspend () -> RESULT
    ) = flow {
        emit(ResultState.Pending(data = initialData))

        emit(ResultState.Success(data = request()))
    }

    fun destroy() {
        scope.cancel()
    }

}