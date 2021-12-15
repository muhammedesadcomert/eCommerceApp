package com.muhammedesadcomert.ecommerceapp.model

import androidx.annotation.StringRes

data class Address(
    @StringRes var titleResourceId: Int,
    @StringRes var nameResourceId: Int,
    @StringRes var phoneNumberResourceId: Int,
    @StringRes var AddressResourceId: Int
)