package com.muhammedesadcomert.ecommerceapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.muhammedesadcomert.ecommerceapp.adapter.AddressAdapter
import com.muhammedesadcomert.ecommerceapp.data.DataSource
import com.muhammedesadcomert.ecommerceapp.databinding.FragmentAddressesBinding

class Addresses : Fragment() {

    private var _binding: FragmentAddressesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddressesBinding.inflate(layoutInflater)

        val addresses = DataSource().loadAddresses()
        binding.addressesRv.apply {
            adapter = AddressAdapter(addresses)
            layoutManager = GridLayoutManager(context, 1)
            setHasFixedSize(true)
        }

        return binding.root
    }

}