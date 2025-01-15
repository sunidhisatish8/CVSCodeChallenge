package com.example.cvscodechallenge.utils

sealed class ResponseState<T> {
    class Loading<T> : ResponseState<T>()
    data class Success<T>(val data: T) : ResponseState<T>()
    data class Failed<T>(val message: String?) : ResponseState<T>()
}