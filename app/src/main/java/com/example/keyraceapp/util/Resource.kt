package com.example.keyraceapp.util

sealed class Resource<T>() {
    abstract val data: T?
    data class Success<T>(override val data: T?): Resource<T>()

    data class Error<T>(val message: String,override  val data: T? = null): Resource<T>()
    data class Loading<T>(val isLoading: Boolean = true, override val data: T? = null): Resource<T>()
}