package com.example.sharedmainfeature.helpers

import com.example.sharedmainfeature.data.Provider
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProviderHelper: KoinComponent {
    private val provider : Provider by inject()
    fun getProvider() = provider
}