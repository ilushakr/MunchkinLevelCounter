package com.example.munchkinlevelcounter.android.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.munchkinlevelcounter.android.navigation.NavigationEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun collectNavigationEvents(
    navigationEvents: Flow<NavigationEvent>,
    key: Any? = true,
    callback: (navigationEvent: NavigationEvent) -> Unit
){
    LaunchedEffect(key1 = key) {
        navigationEvents.collectLatest(callback)
    }
}