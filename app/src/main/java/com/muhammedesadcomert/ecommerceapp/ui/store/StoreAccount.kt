package com.muhammedesadcomert.ecommerceapp.ui.store

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.muhammedesadcomert.ecommerceapp.databinding.FragmentStoreAccountBinding
import com.muhammedesadcomert.ecommerceapp.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoreAccount : Fragment() {

    private var _binding: FragmentStoreAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoreAccountBinding.inflate(layoutInflater)



        binding.signOut.setOnClickListener {
            auth = Firebase.auth
            auth.signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }

        return binding.root
    }
}