package com.example.finalproject.utils

sealed class Resource<T>(val data: Any? = null, val message: String? = null) {
    class Success<T>(data: T?=null) : Resource<T>(data)
    class Error<T : Any>(message: String?, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)

}