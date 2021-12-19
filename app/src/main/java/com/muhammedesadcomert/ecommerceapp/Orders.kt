package com.muhammedesadcomert.ecommerceapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.muhammedesadcomert.ecommerceapp.databinding.FragmentOrdersBinding
import com.muhammedesadcomert.ecommerceapp.model.Product

class OrdersFragment : Fragment() {

    private lateinit var binding: FragmentOrdersBinding
    private lateinit var orders: ArrayList<Product>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrdersBinding.inflate(layoutInflater)

        orders = ArrayList()
//        val orderAdapter = OrderAdapter(orders)
        binding.recyclerView.apply {
//            adapter =
            layoutManager = GridLayoutManager(context, 1)
            setHasFixedSize(true)
        }

        return binding.root
    }
}