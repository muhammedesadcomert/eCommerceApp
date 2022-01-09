package com.muhammedesadcomert.ecommerceapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.muhammedesadcomert.ecommerceapp.R
import com.muhammedesadcomert.ecommerceapp.databinding.FragmentShoppingCartCardBinding
import com.muhammedesadcomert.ecommerceapp.model.Product
import com.squareup.picasso.Picasso

class CartAdapter : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private val products: ArrayList<Product> = arrayListOf()
    var deleteFromCart: (id: String) -> Unit = {}

    class ViewHolder(val binding: FragmentShoppingCartCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
        val cartCardBinding = FragmentShoppingCartCardBinding.inflate(adapterLayout, parent, false)
        return ViewHolder(cartCardBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]

        holder.binding.apply {
            productTitle.text = product.name
            (product.price + " $").also { productPrice.text = it }
            Picasso.get().load(product.imageURL).into(holder.binding.productImage)
        }

        holder.binding.cartProductCard.setOnClickListener {
            val bundle = bundleOf("productId" to product.id)
            Navigation.findNavController(it)
                .navigate(R.id.action_shopping_cart_to_productPage, bundle)
        }

        holder.binding.deleteFromCart.setOnClickListener {
            deleteFromCart(product.id)
        }
    }

    override fun getItemCount() = products.size

    fun updateList(list: ArrayList<Product>) {
        products.clear()
        products.addAll(list)
    }
}