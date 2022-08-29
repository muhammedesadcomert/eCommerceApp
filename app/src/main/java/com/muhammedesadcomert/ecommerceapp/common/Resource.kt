package com.muhammedesadcomert.ecommerceapp.common

sealed class Resource<out T> {
    data class Success<out T>(val data: T?) : Resource<T>()
    data class Failure(val message: String) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}
