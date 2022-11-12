package com.example.munchkinlevelcounter.android.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.munchkinlevelcounter.android.R
import com.example.munchkinlevelcounter.android.UiEvent
import com.example.munchkinlevelcounter.android.navigation.NavigationEvent
import com.example.munchkinlevelcounter.android.navigation.Screens
import com.example.munchkinlevelcounter.android.utils.UiText
import com.example.munchkinlevelcounter.android.utils.toArgs
import com.example.sharedmainfeature.data.Provider
import com.example.sharedmainfeature.objects.Munchkin
import com.example.sharedmainfeature.presentation.MunchkinController
import com.example.sharedmainfeature.presentation.MunchkinStore
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MunckinsViewModel : ViewModel() {

    private val munchkinController by lazy { MunchkinController.initController() }

    val state: StateFlow<MunchkinStore.MunchkinState>
        get() = munchkinController.state()

    private val _navigationEvent = Channel<NavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    init {

        munchkinController.getMunchkins()

        viewModelScope.launch {
            state.collectLatest { state ->
                if (state.error is Provider.MunchkinAlreadyExistError) {
                    state.error?.message?.let { errorMessage ->
                        sendUiEvent(
                            NavigationEvent.ShowSnackbar(
                                UiText.ExternalString(
                                    errorMessage
                                )
                            )
                        )
                    }

                }
            }
        }
    }

    fun insertMunchkin(munchkin: Munchkin) {
        munchkinController.insertMunchkin(munchkin)
    }

    fun updateMunchkin(munchkin: Munchkin) {
        munchkinController.updateMunchkinById(munchkin)
    }

    fun deleteMunchkinById(munchkinId: Int) {
        munchkinController.deleteMunchkinById(munchkinId)
    }

    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.CreateMunchkinScreen -> {
                sendUiEvent(
                    NavigationEvent.Navigate(
                        Screens.CreateMunchkinScreen.withArgs(
                            toArgs(event.munchkin)
                        )
                    )
                )
            }
            UiEvent.SettingsScreen -> {
                sendUiEvent(
                    NavigationEvent.Navigate(
                        Screens.AppSettingsScreen.route
                    )
                )
            }
            is UiEvent.OpenDetailedList -> {
                sendUiEvent(
                    NavigationEvent.Navigate(
                        Screens.DetailedMunchkinsListScreen.withArgs(
                            toArgs(event.munchkin)
                        )
                    )
                )
            }
            UiEvent.OnBackClick -> {
                sendUiEvent(NavigationEvent.PopBackStack)
            }
            is UiEvent.SaveNewMunchkin -> {
                if (!event.editableMunchkin.isEmptyMunchkin() && event.editableMunchkin == event.munchkin) {
                    sendUiEvent(NavigationEvent.PopBackStack)
                }

                if (event.editableMunchkin.name.isBlank()) {
                    sendUiEvent(
                        NavigationEvent.ShowSnackbar(
                            UiText.ResourceString(R.string.blank_name)
                        )
                    )
                } else {
                    if(event.editableMunchkin.isEmptyMunchkin()){
                        munchkinController.insertMunchkin(event.editableMunchkin)
                    }else{
                        munchkinController.updateMunchkinById(event.editableMunchkin)
                    }
                }
            }
            is UiEvent.KillMunchkin -> {
                val killedMunchkin = event.munchkin.copy(level = 1, strength = 0)
                munchkinController.updateMunchkinById(killedMunchkin)
                sendUiEvent(
                    NavigationEvent.ShowSnackbar(
                        UiText.ExternalString("${killedMunchkin.name} is killed"),
                        UiText.ResourceString(R.string.cancel_snackbar),
                        onAction = {
                            munchkinController.updateMunchkinById(event.munchkin)
                        }
                    )
                )
            }
            is UiEvent.KillAllMunchkins -> {
                state.value.data?.let { munchkins ->
                    munchkins.forEach { munchkin ->
                        val killedMunchkin = munchkin.copy(level = 1, strength = 0)
                        munchkinController.updateMunchkinById(killedMunchkin)
                    }
                    sendUiEvent(
                        NavigationEvent.ShowSnackbar(
                            UiText.ExternalString("Restored"),
                            UiText.ResourceString(R.string.cancel_snackbar),
                            onAction = {
                                munchkins.forEach {
                                    munchkinController.updateMunchkinById(it)
                                }
                            }
                        )
                    )
                }
            }
            is UiEvent.DeleteMunchkin -> {
                sendUiEvent(
                    NavigationEvent.ShowSnackbar(
                        UiText.ExternalString("${event.munchkin.name} deleted"),
                        UiText.ResourceString(R.string.cancel_snackbar),
                        onAction = {
                            munchkinController.insertMunchkin(event.munchkin)
                        }
                    )
                )
                event.munchkin.id?.let {
                    munchkinController.deleteMunchkinById(it)
                }
            }
            UiEvent.ToggleScreenAlwaysOn -> Unit
        }
    }

    private fun sendUiEvent(event: NavigationEvent) {
        viewModelScope.launch {
            _navigationEvent.send(event)
        }
    }

}