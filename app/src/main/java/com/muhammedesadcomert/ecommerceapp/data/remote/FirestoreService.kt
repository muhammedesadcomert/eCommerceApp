package com.muhammedesadcomert.ecommerceapp.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirestoreService @Inject constructor(private val firestore: FirebaseFirestore) {
    fun getProductsOrderByDate() = firestore.collection("Products").orderBy("date")
}