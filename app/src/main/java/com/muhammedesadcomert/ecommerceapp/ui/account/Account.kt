package com.muhammedesadcomert.ecommerceapp.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.muhammedesadcomert.ecommerceapp.R
import com.muhammedesadcomert.ecommerceapp.databinding.FragmentAccountBinding
import com.muhammedesadcomert.ecommerceapp.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Account : Fragment(R.layout.fragment_account) {

    private lateinit var binding: FragmentAccountBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAccountBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.accountSettings.setOnClickListener {
            findNavController().navigate(R.id.action_account_to_accountSettings)
        }

        binding.addresses.setOnClickListener {
            findNavController().navigate(R.id.action_account_to_addressesPage)
        }

        binding.appSettings.setOnClickListener {
            findNavController().navigate(R.id.action_account_to_appSettings)
        }

        binding.navigationFavorites.setOnClickListener {
            findNavController().navigate(R.id.action_account_to_favorites)
        }

        binding.signOut.setOnClickListener {
//            viewModel.
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }
    }
}