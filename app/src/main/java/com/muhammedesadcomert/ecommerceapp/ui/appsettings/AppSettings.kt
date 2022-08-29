package com.muhammedesadcomert.ecommerceapp.ui.appsettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.muhammedesadcomert.ecommerceapp.databinding.FragmentAppSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppSettings : Fragment() {

    private var _binding: FragmentAppSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppSettingsBinding.inflate(layoutInflater)
        return binding.root
    }
}