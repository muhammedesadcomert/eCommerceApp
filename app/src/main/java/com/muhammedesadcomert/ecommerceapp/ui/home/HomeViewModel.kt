package com.muhammedesadcomert.ecommerceapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muhammedesadcomert.ecommerceapp.data.repository.ProductRepository
import com.muhammedesadcomert.ecommerceapp.model.FirestoreResponse
import com.muhammedesadcomert.ecommerceapp.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {
    private var _products: MutableLiveData<FirestoreResponse> = MutableLiveData()
    val products: LiveData<FirestoreResponse> get() = _products

    init {
        getProducts()
    }

    private fun getProducts() {
        productRepository.getProductsOrderByDate().addSnapshotListener { value, error ->
            if (error != null) {
                _products.postValue(FirestoreResponse(null, error.localizedMessage))
            } else {
                value?.let {
                    val productList: ArrayList<Product> = ArrayList()
                    productList.clear()
                    for (document in value.documents) {
                        val id = document.id
                        val name = document.get("name") as String
                        val price = document.get("price") as String
                        val category = document.get("category") as String
                        val imageURL = document.get("imageURL") as String
                        productList.add(Product(id, name, price, category, imageURL))
                        _products.postValue(FirestoreResponse(productList, null))
                    }
                }

            }
        }
    }
}