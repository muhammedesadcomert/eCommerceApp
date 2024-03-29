package com.muhammedesadcomert.ecommerceapp.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.muhammedesadcomert.ecommerceapp.R
import com.muhammedesadcomert.ecommerceapp.databinding.FragmentCartBinding
import com.muhammedesadcomert.ecommerceapp.model.Product
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Cart : Fragment(R.layout.fragment_cart) {

    private lateinit var binding: FragmentCartBinding
    private lateinit var cart: ArrayList<Product>
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = Firebase.firestore
        auth = Firebase.auth
        cart = ArrayList()

        cartAdapter = CartAdapter { product ->
            val action =
                CartDirections.actionShoppingCartToProductPage(product)
            findNavController().navigate(action)
        }

        binding.recyclerView.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
        }

        db.collection("Users").document(auth.uid.toString()).collection("Cart")
            .orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Toast.makeText(activity, error.localizedMessage, Toast.LENGTH_LONG).show()
                } else if (value != null && !value.isEmpty) {

                    cart.clear()

                    for (document in value.documents) {
                        val id = document.id
                        db.collection("Products").document(id).get()
                            .addOnSuccessListener {
                                val name = it["name"] as String
                                val price = it["price"] as String
                                val category = it["category"] as String
                                val imageURL = it["imageURL"] as String
                                val product = Product(id, name, price, category, imageURL)

                                cart.add(product)
                                cartAdapter.differ.submitList(cart)
                            }.addOnFailureListener {
                                Toast.makeText(activity, it.localizedMessage, Toast.LENGTH_LONG)
                                    .show()
                            }
                    }
                } else {
                    cart.clear()
                    cartAdapter.updateList(cart)
                    cartAdapter.notifyItemRemoved(0)
                }
                cartAdapter.deleteFromCart = {
                    db.collection("Users").document(auth.uid.toString())
                        .collection("Cart").document(it).delete()
                }
            }
    }
}