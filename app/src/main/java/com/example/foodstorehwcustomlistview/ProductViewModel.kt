package com.example.foodstorehwcustomlistview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

object ProductViewModel: ViewModel() {

    private var listProductsViewModel: MutableLiveData<MutableList<Product>> = MutableLiveData(mutableListOf())

    fun getListProductsViewModel() = listProductsViewModel

    fun addProductList(product: Product){
        listProductsViewModel.value?.add(product)
        listProductsViewModel.value = listProductsViewModel.value
    }

    fun updateProductsListViewModel(product: Product, index: Int?){
        if (index != null) listProductsViewModel.value?.add(index+1,product)
        if (index != null) listProductsViewModel.value?.removeAt(index)
    }
}