package com.muhammedesadcomert.ecommerceapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muhammedesadcomert.ecommerceapp.databinding.FragmentAddressCardBinding
import com.muhammedesadcomert.ecommerceapp.model.Address

class AddressAdapter(private val addresses: List<Address>) :
    RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    class AddressViewHolder(val binding: FragmentAddressCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
        val addressCardBinding = FragmentAddressCardBinding.inflate(adapterLayout, parent, false)
        return AddressViewHolder(addressCardBinding)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val address = addresses[position]

        holder.binding.apply {
            addressTitle.setText(address.titleResourceId)
        }

        holder.binding.addressCard.setOnClickListener {

        }
    }

    override fun getItemCount() = addresses.size
}