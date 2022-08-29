package com.muhammedesadcomert.ecommerceapp.ui.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.muhammedesadcomert.ecommerceapp.databinding.FragmentStoreProductsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoreProducts : Fragment() {

    private var _binding: FragmentStoreProductsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentStoreProductsBinding.inflate(layoutInflater)

        binding.addProduct.setOnClickListener {
            val action = StoreProductsDirections.actionStoreProductsToUploadFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }
}