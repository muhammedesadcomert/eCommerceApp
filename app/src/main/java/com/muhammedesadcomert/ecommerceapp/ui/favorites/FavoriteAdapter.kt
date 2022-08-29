package com.muhammedesadcomert.ecommerceapp.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muhammedesadcomert.ecommerceapp.databinding.FavoriteCardBinding
import com.muhammedesadcomert.ecommerceapp.model.Product

class FavoriteAdapter(private val favorites: ArrayList<Product>) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    class FavoriteViewHolder(val binding: FavoriteCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FavoriteViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
        val favoriteCardBinding = FavoriteCardBinding.inflate(adapterLayout, parent, false)
        return FavoriteViewHolder(favoriteCardBinding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = favorites[position]

        holder.binding.apply {
//            productTitle.text =
        }
    }

    override fun getItemCount() = favorites.size

}