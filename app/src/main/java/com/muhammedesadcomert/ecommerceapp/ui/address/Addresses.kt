package com.muhammedesadcomert.ecommerceapp.ui.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.muhammedesadcomert.ecommerceapp.data.DataSource
import com.muhammedesadcomert.ecommerceapp.databinding.FragmentAddressesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Addresses : Fragment() {

    private var _binding: FragmentAddressesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddressesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addresses = DataSource().loadAddresses()
        binding.addressesRv.apply {
            adapter = AddressAdapter(addresses)
            layoutManager = GridLayoutManager(context, 1)
            setHasFixedSize(true)
        }
    }
}