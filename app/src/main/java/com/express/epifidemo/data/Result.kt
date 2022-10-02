package com.express.epifidemo.data

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    object Loading : Result<Nothing>()
    data class Failure(val message: String) : Result<Nothing>()
}