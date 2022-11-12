package com.example.munchkinlevelcounter.di

import com.example.munchkinlevelcounter.cache.DatabaseDriverFactory
import org.koin.dsl.module

val cacheBaseModule = module {

    single { DatabaseDriverFactory() }

}