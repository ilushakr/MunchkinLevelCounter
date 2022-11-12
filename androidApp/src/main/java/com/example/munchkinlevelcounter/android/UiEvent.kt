package com.example.munchkinlevelcounter.android

import com.example.sharedmainfeature.objects.Munchkin

sealed class UiEvent {
    data class CreateMunchkinScreen(val munchkin: Munchkin) : UiEvent()
    object SettingsScreen : UiEvent()
    data class SaveNewMunchkin(val munchkin: Munchkin, val editableMunchkin: Munchkin) : UiEvent()
    data class OpenDetailedList(val munchkin: Munchkin) : UiEvent()
    object OnBackClick : UiEvent()
    data class KillMunchkin(val munchkin: Munchkin) : UiEvent()
    object KillAllMunchkins: UiEvent()
    data class DeleteMunchkin(val munchkin: Munchkin): UiEvent()

    object ToggleScreenAlwaysOn: UiEvent()
}