package com.example.sharedmainfeature.helpers

import com.example.munchkinlevelcounter.di.sharedBase
import com.example.sharedmainfeature.di.sharedFeatures
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

class KoinHelper : KoinComponent {

    fun initKoin(){
        startKoin {
            modules(sharedBase())
            modules(sharedFeatures())
        }
    }
}
