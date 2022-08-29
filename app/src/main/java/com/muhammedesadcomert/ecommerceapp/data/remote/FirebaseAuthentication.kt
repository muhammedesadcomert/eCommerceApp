package com.muhammedesadcomert.ecommerceapp.data.remote

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class FirebaseAuthentication @Inject constructor(private val firebaseAuth: FirebaseAuth) {
    fun signIn(email: String, password: String) =
        firebaseAuth.signInWithEmailAndPassword(email, password)

    fun signUp(email: String, password: String) =
        firebaseAuth.createUserWithEmailAndPassword(email, password)

    fun getCurrentUser() = firebaseAuth.currentUser

    fun signOut() = firebaseAuth.signOut()
}