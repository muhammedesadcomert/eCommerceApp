package com.muhammedesadcomert.ecommerceapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.muhammedesadcomert.ecommerceapp.R
import com.muhammedesadcomert.ecommerceapp.databinding.ProductCardBinding
import com.muhammedesadcomert.ecommerceapp.model.Product
import com.squareup.picasso.Picasso

class ProductAdapter(private val products: ArrayList<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

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
                        Toast.makeText(View.context, "Added to cart!", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    override fun getItemCount() = products.size
}