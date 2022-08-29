package com.muhammedesadcomert.ecommerceapp.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.muhammedesadcomert.ecommerceapp.R
import com.muhammedesadcomert.ecommerceapp.data.DataSource
import com.muhammedesadcomert.ecommerceapp.databinding.FragmentCategoriesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Categories : Fragment(R.layout.fragment_categories) {

    private lateinit var binding: FragmentCategoriesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCategoriesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categories = DataSource().loadCategories()
        binding.recyclerView.adapter = CategoryAdapter(categories)
        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerView.setHasFixedSize(true)
    }
}