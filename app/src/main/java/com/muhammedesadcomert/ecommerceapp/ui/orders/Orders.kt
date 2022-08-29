package com.muhammedesadcomert.ecommerceapp.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.muhammedesadcomert.ecommerceapp.databinding.FragmentOrdersBinding
import com.muhammedesadcomert.ecommerceapp.model.Product
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment : Fragment() {

    private lateinit var binding: FragmentOrdersBinding
    private lateinit var orders: ArrayList<Product>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentOrdersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orders = ArrayList()
//        val orderAdapter = OrderAdapter(orders)
        binding.recyclerView.apply {
//            adapter =
            layoutManager = GridLayoutManager(context, 1)
            setHasFixedSize(true)
        }
    }
}