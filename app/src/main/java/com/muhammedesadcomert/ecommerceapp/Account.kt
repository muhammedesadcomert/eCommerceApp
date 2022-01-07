package com.muhammedesadcomert.ecommerceapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.muhammedesadcomert.ecommerceapp.databinding.FragmentAccountBinding
import com.muhammedesadcomert.ecommerceapp.ui.login.LoginActivity

class Account : Fragment(R.layout.fragment_account) {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(layoutInflater)

        binding.accountSettings.setOnClickListener {
            findNavController().navigate(R.id.action_account_to_accountSettings)
        }

        binding.addresses.setOnClickListener {
            findNavController().navigate(R.id.action_account_to_addressesPage)
        }

        binding.appSettings.setOnClickListener {
            findNavController().navigate(R.id.action_account_to_appSettings)
        }

        binding.favorites.setOnClickListener {
            findNavController().navigate(R.id.action_account_to_favorites)
        }

        binding.signOut.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}