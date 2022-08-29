package com.muhammedesadcomert.ecommerceapp.ui.login.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.muhammedesadcomert.ecommerceapp.R
import com.muhammedesadcomert.ecommerceapp.databinding.FragmentSignInBinding
import com.muhammedesadcomert.ecommerceapp.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSignInBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav_view)?.visibility = View.GONE

        with(binding) {
            signIn.setOnClickListener {
                viewModel.signIn(username.text.toString(), password.text.toString())
                viewModel.auth.observe(viewLifecycleOwner) { authMessage ->
                    if (authMessage == "Successful") {
                        findNavController()
                            .navigate(SignInFragmentDirections.actionSignInFragmentToNavigationHome())
                    } else {
                        Toast.makeText(context, authMessage, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}