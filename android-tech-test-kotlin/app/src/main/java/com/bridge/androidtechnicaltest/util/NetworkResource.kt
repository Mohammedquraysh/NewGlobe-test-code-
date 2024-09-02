package com.bridge.androidtechnicaltest.util




sealed class NetworkResource<T> {
    data class Success<T>(val data: T) : NetworkResource<T>()
    data class Error<T>(val throwable: Throwable) : NetworkResource<T>()
    class Loading<T> : NetworkResource<T>()
}
