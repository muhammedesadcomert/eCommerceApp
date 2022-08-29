package com.muhammedesadcomert.ecommerceapp.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.muhammedesadcomert.ecommerceapp.databinding.FragmentCartItemBinding
import com.muhammedesadcomert.ecommerceapp.model.Product
import com.squareup.picasso.Picasso

class CartAdapter(private val onItemClicked: (Product) -> Unit) :
    ListAdapter<Product, CartAdapter.CartViewHolder>(DIFF_CALLBACK) {

    private val products: ArrayList<Product> = arrayListOf()
    var deleteFromCart: (id: String) -> Unit = {}

    inner class CartViewHolder(private val binding: FragmentCartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                productTitle.text = product.name
                (product.price + " $").also { productPrice.text = it }
                Picasso.get().load(product.imageURL).into(productImage)

                deleteFromCart.setOnClickListener {
                    deleteFromCart(product.id)
                }

                itemView.setOnClickListener {
                    onItemClicked(product)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
        val cartItemBinding = FragmentCartItemBinding.inflate(adapterLayout, parent, false)
        return CartViewHolder(cartItemBinding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)
    }

    fun updateList(list: ArrayList<Product>) {
        products.clear()
        products.addAll(list)
    }

    val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun getItemCount() = differ.currentList.size

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }
    }
}