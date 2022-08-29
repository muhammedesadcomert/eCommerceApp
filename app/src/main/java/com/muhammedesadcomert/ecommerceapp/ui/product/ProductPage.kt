package com.muhammedesadcomert.ecommerceapp.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.muhammedesadcomert.ecommerceapp.databinding.FragmentProductPageBinding
import com.muhammedesadcomert.ecommerceapp.model.Product
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductPageFragment : Fragment() {

    private lateinit var binding: FragmentProductPageBinding
    private val args: ProductPageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProductPageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val product = args.product
        bind(product)
    }

    private fun bind(product: Product) {
        binding.apply {
            productTitle.text = product.name
            productPrice.text = product.price
            Picasso.get().load(product.imageURL).into(productImage)
        }
    }
}