package com.muhammedesadcomert.ecommerceapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.muhammedesadcomert.ecommerceapp.R
import com.muhammedesadcomert.ecommerceapp.databinding.FragmentShoppingCartCardBinding
import com.muhammedesadcomert.ecommerceapp.model.Product
import com.squareup.picasso.Picasso

class CartAdapter(private val products: ArrayList<Product>) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

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
            db = Firebase.firestore
            auth = Firebase.auth

            db.collection("Users").document(auth.uid.toString()).collection("Cart")
                .document(product.id).delete()
        }
    }

    override fun getItemCount() = products.size
}