package com.muhammedesadcomert.ecommerceapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.muhammedesadcomert.ecommerceapp.R
import com.muhammedesadcomert.ecommerceapp.databinding.FragmentHomeBinding
import com.muhammedesadcomert.ecommerceapp.ui.product.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Home : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var productAdapter: ProductAdapter
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav_view)?.visibility =
            View.VISIBLE

        initAdapter()

        viewModel.products.observe(viewLifecycleOwner) { response ->
            if (response.error != null) {
                Toast.makeText(context, response.error, Toast.LENGTH_SHORT).show()
            } else {
                productAdapter.differ.submitList(response.value)
            }
        }
    }

    private fun initAdapter() {
        productAdapter = ProductAdapter { product ->
            findNavController().navigate(HomeDirections.actionHomeToProductPage(product))
        }

        binding.recyclerView.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
        }
    }
}