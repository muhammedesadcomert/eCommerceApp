package com.muhammedesadcomert.ecommerceapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.muhammedesadcomert.ecommerceapp.R
import com.muhammedesadcomert.ecommerceapp.databinding.FragmentCategoryCardBinding
import com.muhammedesadcomert.ecommerceapp.model.Category

class CategoryAdapter(private val categories: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(val binding: FragmentCategoryCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
        val categoryCardBinding = FragmentCategoryCardBinding.inflate(adapterLayout, parent, false)
        return CategoryViewHolder(categoryCardBinding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]

        holder.binding.apply {
            categoryTitle.setText(category.titleResourceId)

            categoryCard.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.action_categories_to_home)

            }
        }
    }

    override fun getItemCount() = categories.size
}