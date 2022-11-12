package com.example.munchkinlevelcounter.android.sharedprefs

import android.content.Context
import com.example.munchkinlevelcounter.android.R
import com.example.sharedmainfeature.data.local.LocalDataProvider

class LocalDataProviderImpl(private val context: Context) : LocalDataProvider {

    private val sharedPref by lazy {
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
    }

    override fun getKeepScreenOn(): Boolean {
        return sharedPref.getBoolean(context.getString(R.string.keep_screen_on), false)
    }

    override fun putKeepScreenOn(value: Boolean) {
        with(sharedPref.edit()) {
            putBoolean(context.getString(R.string.keep_screen_on), value)
            apply()
        }
    }

}