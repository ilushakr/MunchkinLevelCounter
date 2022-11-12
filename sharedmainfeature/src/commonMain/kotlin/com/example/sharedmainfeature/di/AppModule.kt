package com.example.sharedmainfeature.di

import com.example.sharedmainfeature.data.Provider
import org.koin.dsl.module

fun sharedFeatures() = listOf(
    cacheModule,
    appModule
)

val appModule = module {
    factory {
        Provider(get())
    }
}