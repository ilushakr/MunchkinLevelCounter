package com.example.munchkinlevelcounter.android

import android.app.Application
import com.example.munchkinlevelcounter.android.modules.androidAppModule
import com.example.munchkinlevelcounter.di.sharedBase
import com.example.sharedmainfeature.di.sharedFeatures
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        GlobalContext.startKoin {
            //inject Android context
            androidContext(this@BaseApplication)
            modules(sharedBase())
            modules(sharedFeatures())
            modules(androidAppModule)
        }

    }
}