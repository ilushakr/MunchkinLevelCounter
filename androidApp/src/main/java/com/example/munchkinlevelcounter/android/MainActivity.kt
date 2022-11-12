package com.example.munchkinlevelcounter.android

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.munchkinlevelcounter.android.navigation.Navigation
import com.example.munchkinlevelcounter.android.ui.theme.MyApplicationTheme
import com.example.munchkinlevelcounter.android.viewmodels.AppSettingsViewModel
import com.example.munchkinlevelcounter.android.viewmodels.MunckinsViewModel

class MainActivity : ComponentActivity(), ActivityViewModelProvider {

    private val munchkinViewModel: MunckinsViewModel by viewModels()
    private val appSettingsViewModel: AppSettingsViewModel by viewModels()

    override fun munchkinViewModel() = munchkinViewModel
    override fun appSettingsViewModel() = appSettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {

                val keepScreenOn by appSettingsViewModel.keepScreenOn.collectAsState()

                when (keepScreenOn) {
                    true -> window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                    false -> window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                }
                Navigation(this)
            }
        }
    }
}

interface ActivityViewModelProvider {
    fun munchkinViewModel(): MunckinsViewModel
    fun appSettingsViewModel(): AppSettingsViewModel
}

