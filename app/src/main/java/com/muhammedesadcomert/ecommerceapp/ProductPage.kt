package com.muhammedesadcomert.ecommerceapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.muhammedesadcomert.ecommerceapp.databinding.FragmentProductPageBinding
import com.squareup.picasso.Picasso

class ProductPageFragment : Fragment() {

    private var _binding: FragmentProductPageBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductPageBinding.inflate(layoutInflater)

        db = Firebase.firestore

        binding.apply {
            db.collection("Products").document(requireArguments().get("productId") as String)
                .get().addOnSuccessListener { documentSnapshot ->
                    productTitle.text = documentSnapshot.get("name") as String

                    db.collection("Users").document(documentSnapshot.get("userUID") as String).get()
                        .addOnSuccessListener {
                            storeName.text = it.get("storeName") as String
                        }

                    (documentSnapshot.get("price") as String + " $").also { string ->
                        productPrice.text = string
                    }

                    Picasso.get().load(documentSnapshot.get("imageURL") as String)
                        .into(productImage)
                }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}