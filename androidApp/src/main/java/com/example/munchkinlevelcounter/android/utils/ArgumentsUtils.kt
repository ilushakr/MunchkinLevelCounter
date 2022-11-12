package com.example.munchkinlevelcounter.android.utils

import com.google.gson.Gson

fun <T> toArgs(argsClass: T): String = Gson().toJson(argsClass)

inline fun <reified T> fromArgs(args: String): T = Gson().fromJson(args, T::class.java)