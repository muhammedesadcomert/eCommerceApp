package com.muhammedesadcomert.ecommerceapp.data.repository

import com.muhammedesadcomert.ecommerceapp.data.remote.FirestoreService
import javax.inject.Inject

class ProductRepository @Inject constructor(private val firestore: FirestoreService) {
    fun getProductsOrderByDate() = firestore.getProductsOrderByDate()
}