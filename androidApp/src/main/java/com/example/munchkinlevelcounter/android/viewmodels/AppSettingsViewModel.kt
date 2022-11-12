package com.example.munchkinlevelcounter.android.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.munchkinlevelcounter.android.BuildConfig
import com.example.munchkinlevelcounter.android.UiEvent
import com.example.munchkinlevelcounter.android.navigation.NavigationEvent
import com.example.sharedmainfeature.data.local.LocalDataProvider
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AppSettingsViewModel : ViewModel(), KoinComponent {

    private val localDataProvider by inject<LocalDataProvider>()

    val appVersion = BuildConfig.VERSION_NAME

    private val _navigationEvent = Channel<NavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private val _keepScreenOn = MutableStateFlow(localDataProvider.getKeepScreenOn())
    val keepScreenOn = _keepScreenOn.asStateFlow()

    fun onEvent(event: UiEvent) {
        when (event) {
            UiEvent.OnBackClick -> sendUiEvent(NavigationEvent.PopBackStack)
            UiEvent.ToggleScreenAlwaysOn -> {
                localDataProvider.putKeepScreenOn(!_keepScreenOn.value)
                _keepScreenOn.value = localDataProvider.getKeepScreenOn()
            }
            else -> Unit
        }
    }

    private fun sendUiEvent(event: NavigationEvent) {
        viewModelScope.launch {
            _navigationEvent.send(event)
        }
    }


}