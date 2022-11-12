package com.example.munchkinlevelcounter.objects

sealed class ResultState<T> {
    data class Pending<T>(val data: T? = null) : ResultState<T>()
    data class Success<T>(val data: T) : ResultState<T>()
    data class Fail<T>(val error: Throwable, val data: T? = null) : ResultState<T>()
}