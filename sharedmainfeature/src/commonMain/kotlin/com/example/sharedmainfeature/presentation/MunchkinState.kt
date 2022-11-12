package com.example.sharedmainfeature.presentation

import com.example.munchkinlevelcounter.ui.AbstractStore
import com.example.munchkinlevelcounter.ui.IAction
import com.example.munchkinlevelcounter.ui.IState
import com.example.sharedmainfeature.objects.Munchkin

class MunchkinStore: AbstractStore<MunchkinStore.MunchkinState>(MunchkinState()) {

    override fun newState(oldState: MunchkinState, action: IAction): MunchkinState {
        return when(action){
            is Success -> oldState.copy(data = action.data, pending = false, error = null)
            is Pending -> oldState.copy(pending = true, error = null)
            is Fail -> oldState.copy(pending = false, error = action.error)
            else -> super.newState(oldState, action)
        }
    }

    data class MunchkinState(
        val data: List<Munchkin>? = null,
        val pending: Boolean? = null,
        val error: Throwable? = null
    ): IState

    data class Success(val data: List<Munchkin>): IAction
    object Pending: IAction
    data class Fail(val error: Throwable): IAction
}