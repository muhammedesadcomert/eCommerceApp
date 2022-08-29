package com.muhammedesadcomert.ecommerceapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.muhammedesadcomert.ecommerceapp.R
import com.muhammedesadcomert.ecommerceapp.databinding.FragmentHomeBinding
import com.muhammedesadcomert.ecommerceapp.model.Product
import com.muhammedesadcomert.ecommerceapp.ui.product.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Home : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var productList: ArrayList<Product>
    private lateinit var productAdapter: ProductAdapter

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

        db = Firebase.firestore
        productList = ArrayList()
        val productAdapter = ProductAdapter(productList)

//        productAdapter =

        binding.recyclerView.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
        }

        db.collection("Products").orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
//                    Toast.makeText(activity, error.localizedMessage, Toast.LENGTH_LONG).show()
                } else if (value != null && !value.isEmpty) {

                    val documents = value.documents
                    productList.clear()

                    for (document in documents) {
                        val id = document.id
                        val name = document.get("name") as String
                        val price = document.get("price") as String
                        val category = document.get("category") as String
                        val imageURL = document.get("imageURL") as String

                        val product = Product(id, name, price, category, imageURL)
                        productList.add(product)
                        binding.recyclerView.adapter = productAdapter
                    }
                }
            }
    }
}