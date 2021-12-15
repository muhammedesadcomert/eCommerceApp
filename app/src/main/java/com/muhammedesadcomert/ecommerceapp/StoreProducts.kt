package com.muhammedesadcomert.ecommerceapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.muhammedesadcomert.ecommerceapp.databinding.FragmentStoreProductsBinding

class StoreProducts : Fragment() {

    private var _binding: FragmentStoreProductsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoreProductsBinding.inflate(layoutInflater)

        binding.addProduct.setOnClickListener {
            startActivity(Intent(activity, UploadActivity::class.java))
        }

        return binding.root
    }
}