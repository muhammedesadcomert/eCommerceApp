package com.muhammedesadcomert.ecommerceapp.ui.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.muhammedesadcomert.ecommerceapp.databinding.ProductCardBinding
import com.muhammedesadcomert.ecommerceapp.model.Product
import com.squareup.picasso.Picasso

class ProductAdapter(private val onItemClicked: (Product) -> Unit) :
    ListAdapter<Product, ProductAdapter.ProductViewHolder>(DIFF_CALLBACK) {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    inner class ProductViewHolder(val binding: ProductCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            with(binding) {
                productTitle.text = product.name
                (product.price + " $").also { productPrice.text = it }
                Picasso.get().load(product.imageURL).into(productImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ProductCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = differ.currentList[position]

        holder.bind(product)

        holder.itemView.setOnClickListener {
            onItemClicked(product)
        }

        holder.binding.addShoppingCart.setOnClickListener { View ->
            auth = Firebase.auth
            db = Firebase.firestore
            var count = 1
            val uid = auth.uid.toString()

            db.collection("Users").document(uid).collection("Cart").document(product.id).apply {
                get()
                    .addOnSuccessListener {
                        if (it["count"] != null) {
                            count = (it["count"] as Long).toInt()
                            count++
                        }
                        val hashMap = hashMapOf<String, Any>()
                        hashMap.apply {
                            put("count", count)
                            put("date", Timestamp.now())
                        }
                        set(hashMap)
                        Toast.makeText(View.context, "Added to cart", Toast.LENGTH_LONG).show()
                    }
            }
        }

        holder.binding.addFavorite.setOnClickListener {
            holder.binding.addedToFavorite.visibility = View.VISIBLE
            holder.binding.addFavorite.visibility = View.GONE
        }

        holder.binding.addedToFavorite.setOnClickListener {
            holder.binding.addFavorite.visibility = View.VISIBLE
            holder.binding.addedToFavorite.visibility = View.GONE
        }
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