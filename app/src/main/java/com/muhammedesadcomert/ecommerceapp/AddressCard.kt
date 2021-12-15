package com.muhammedesadcomert.ecommerceapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muhammedesadcomert.ecommerceapp.databinding.FragmentAddressCardBinding

class AddressCard : Fragment() {

    private var _binding: FragmentAddressCardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddressCardBinding.inflate(layoutInflater)



        return binding.root
    }
}