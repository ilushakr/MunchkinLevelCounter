package com.example.sharedmainfeature.di

import com.example.munchkinlevelcounter.cache.DatabaseDriverFactory
import com.example.sharedmainfeature.data.database.MunchkinDao
import com.example.sharedmainfeature.data.database.MunchkinDaoImpl
import com.example.sharedmainfeature.shared.cache.MunchkinDatabase
import org.koin.dsl.module

val cacheModule = module {

    single<MunchkinDao> {
        MunchkinDatabase.Schema
        MunchkinDaoImpl(MunchkinDatabase((get() as DatabaseDriverFactory)
            .createDriver(MunchkinDatabase.Schema, "munchkin_database.db")))
    }

}