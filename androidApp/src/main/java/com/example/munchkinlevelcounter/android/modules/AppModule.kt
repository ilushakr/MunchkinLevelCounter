package com.example.munchkinlevelcounter.android.modules

import com.example.munchkinlevelcounter.android.sharedprefs.LocalDataProviderImpl
import com.example.sharedmainfeature.data.local.LocalDataProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidAppModule = module {
    factory<LocalDataProvider> { LocalDataProviderImpl(androidContext()) }
}