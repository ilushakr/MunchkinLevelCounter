package com.example.munchkinlevelcounter.android.navigation

import com.example.munchkinlevelcounter.android.utils.UiText

sealed class NavigationEvent {
    object PopBackStack : NavigationEvent()
    data class Navigate(val route: String) : NavigationEvent()
    data class ShowSnackbar(val message: UiText, val action: UiText? = null, val onAction: (() -> Unit)? = null) : NavigationEvent()
}