package com.muhammedesadcomert.ecommerceapp.model

import java.io.Serializable

data class Product(
    val id: String,
    var name: String,
    var price: String,
    var category: String,
    var imageURL: String
): Serializable