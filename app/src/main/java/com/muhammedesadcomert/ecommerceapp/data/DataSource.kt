package com.muhammedesadcomert.ecommerceapp.data

import com.muhammedesadcomert.ecommerceapp.R
import com.muhammedesadcomert.ecommerceapp.model.Address
import com.muhammedesadcomert.ecommerceapp.model.Category

class DataSource {

    fun loadCategories(): List<Category> {
        return listOf(
            Category(R.string.electronic),
            Category(R.string.home_and_life),
            Category(R.string.clothing),
            Category(R.string.cosmetic),
            Category(R.string.jewelry_and_accessories),
            Category(R.string.sport_and_outdoor),
            Category(R.string.supermarket)
        )
    }

    fun loadAddresses(): List<Address> {
        return listOf(
            Address(R.string.address_title, R.string.name, R.string.phone_number, R.string.address_line_1),
            Address(R.string.address_title, R.string.name, R.string.phone_number, R.string.address_line_1)
        )
    }
}