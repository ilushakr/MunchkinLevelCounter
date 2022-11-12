package com.example.sharedmainfeature.presentation

import com.example.munchkinlevelcounter.objects.ResultState
import com.example.munchkinlevelcounter.ui.Dispatcher
import com.example.munchkinlevelcounter.ui.IController
import com.example.munchkinlevelcounter.ui.IState
import com.example.sharedmainfeature.data.Provider
import com.example.sharedmainfeature.objects.Munchkin
import io.ktor.utils.io.core.*
import org.koin.core.component.inject

interface MunchkinController : IController<MunchkinStore.MunchkinState> {

    val provider: Provider

    fun insertMunchkin(munchkin: Munchkin) {
        scope {
            provider.insertMunchkin(munchkin.copy(name = munchkin.name.trim())).collect {
                val action = getAction(it)
                dispatcher.handle(action)
            }
        }
    }

    fun updateMunchkinById(munchkin: Munchkin) {
        scope {
            provider.updateMunchkinById(munchkin.copy(name = munchkin.name.trim())).collect {
                val action = getAction(it)
                dispatcher.handle(action)
            }
        }
    }


    fun getMunchkins() {
        scope {
            provider.getAllMunchkins().collect {
                val action = getAction(it)
                dispatcher.handle(action)
            }
        }
    }

    fun deleteMunchkinById(munchkinId: Int) {
        scope {
            provider.deleteMunchkinById(munchkinId).collect {
                dispatcher.handle(getAction(it))
            }
        }
    }

    private fun getAction(resultState: ResultState<List<Munchkin>>) = when (resultState) {
        is ResultState.Fail -> MunchkinStore.Fail(error = resultState.error)
        is ResultState.Pending -> MunchkinStore.Pending
        is ResultState.Success -> MunchkinStore.Success(data = resultState.data)
    }

    companion object {
        fun initController() = object : MunchkinController {
            override val provider by inject<Provider>()
            override val store = MunchkinStore()
            override val dispatcher = Dispatcher().apply { add(store) }
        }
    }

}