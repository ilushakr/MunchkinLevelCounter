package com.example.munchkinlevelcounter.android.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText {

    data class ExternalString(val value: String) : UiText()
    class ResourceString(@StringRes val res: Int, vararg val args: Any) : UiText()

    @Composable
    fun asString(): String {
        return when (this) {
            is ExternalString -> value
            is ResourceString -> stringResource(id = res, formatArgs = args)
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is ExternalString -> value
            is ResourceString -> context.getString(res, args)
        }
    }
}