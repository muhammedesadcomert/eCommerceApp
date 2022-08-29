package com.muhammedesadcomert.ecommerceapp.common

sealed class LoginResource {
    object Success : LoginResource()
    data class Failure(val message: String) : LoginResource()
    object Loading : LoginResource()
}