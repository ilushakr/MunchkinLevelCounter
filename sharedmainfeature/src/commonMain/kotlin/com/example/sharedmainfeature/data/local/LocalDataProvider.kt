package com.example.sharedmainfeature.data.local

interface LocalDataProvider {

    fun getKeepScreenOn(): Boolean

    fun putKeepScreenOn(value: Boolean)

}