package com.muhammedesadcomert.ecommerceapp.ui.login.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.muhammedesadcomert.ecommerceapp.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            signUp.setOnClickListener {
                viewModel.signUp(username.text.toString(), password.text.toString())
                viewModel.auth.observe(viewLifecycleOwner) { authMessage ->
                    if (authMessage == "Successful") {
                        findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToNavigationHome())
                    } else {
                        Toast.makeText(context, authMessage, Toast.LENGTH_LONG).show()
                    }
                }
            }
            buttonSignIn.setOnClickListener {
                findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment())
            }
        }
    }
}