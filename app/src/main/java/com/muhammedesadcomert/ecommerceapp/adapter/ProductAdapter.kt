package com.muhammedesadcomert.ecommerceapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.muhammedesadcomert.ecommerceapp.R
import com.muhammedesadcomert.ecommerceapp.databinding.ProductCardBinding
import com.muhammedesadcomert.ecommerceapp.model.Product
import com.squareup.picasso.Picasso

class ProductAdapter(private val products: ArrayList<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(val binding: ProductCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
        val productCardBinding = ProductCardBinding.inflate(adapterLayout, parent, false)
        return ProductViewHolder(productCardBinding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]

        holder.binding.apply {
            productTitle.text = product.name
            (product.price + " $").also { productPrice.text = it }
            Picasso.get().load(product.imageURL).into(holder.binding.productImage)
        }

        holder.binding.productCard.setOnClickListener {
            val bundle = bundleOf("productId" to product.id)
            Navigation.findNavController(it).navigate(R.id.action_home_to_productPage, bundle)
        }

        holder.binding.addShoppingCart.setOnClickListener {
            
        }
    }

    override fun getItemCount() = products.size
}