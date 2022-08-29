package com.muhammedesadcomert.ecommerceapp.data.repository

import com.muhammedesadcomert.ecommerceapp.data.remote.FirebaseAuthentication
import javax.inject.Inject

class AuthRepository @Inject constructor(private val authentication: FirebaseAuthentication) {

    fun signIn(email: String, password: String) = authentication.signIn(email, password)

    fun signUp(email: String, password: String) = authentication.signUp(email, password)

    fun getCurrentUser() = authentication.getCurrentUser()

    fun signOut() = authentication.signOut()
}