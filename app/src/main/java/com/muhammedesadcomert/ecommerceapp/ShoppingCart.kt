package com.muhammedesadcomert.ecommerceapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.muhammedesadcomert.ecommerceapp.adapter.CartAdapter
import com.muhammedesadcomert.ecommerceapp.databinding.FragmentShoppingCartBinding
import com.muhammedesadcomert.ecommerceapp.model.Product

class ShoppingCart : Fragment(R.layout.fragment_shopping_cart) {

    private lateinit var binding: FragmentShoppingCartBinding
    private lateinit var cart: ArrayList<Product>
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingCartBinding.inflate(layoutInflater)
        db = Firebase.firestore
        auth = Firebase.auth
        cart = ArrayList()

        db.collection("Users").document(auth.uid.toString()).collection("Cart")
            .orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Toast.makeText(activity, error.localizedMessage, Toast.LENGTH_LONG).show()
                } else if (value != null && !value.isEmpty) {

                    cart.clear()

                    for (document in value.documents) {
                        val id = document.id
                        db.collection("Products").document(id).get().addOnSuccessListener {
                            val name = it["name"] as String
                            val price = it["price"] as String
                            val category = it["category"] as String
                            val imageURL = it["imageURL"] as String
                            val product = Product(id, name, price, category, imageURL)
                            cart.add(product)
                            binding.recyclerView.apply {
                                adapter = CartAdapter(cart)
                                layoutManager = GridLayoutManager(context, 1)
                                setHasFixedSize(true)
                            }
                        }.addOnFailureListener {
                            Toast.makeText(activity, it.localizedMessage, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        return binding.root
    }
}