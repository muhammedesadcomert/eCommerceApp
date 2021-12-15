package com.muhammedesadcomert.ecommerceapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.muhammedesadcomert.ecommerceapp.databinding.FragmentShoppingCartBinding

class ShoppingCart : Fragment(R.layout.fragment_shopping_cart) {

    private lateinit var binding: FragmentShoppingCartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingCartBinding.inflate(layoutInflater)
        return binding.root
    }
}