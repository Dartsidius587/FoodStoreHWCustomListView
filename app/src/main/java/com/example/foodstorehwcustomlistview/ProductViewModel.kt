package com.example.foodstorehwcustomlistview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductViewModel: ViewModel() {

    var productList: MutableList<Product> = mutableListOf()
    val listProductViewModel: MutableLiveData<Product> by lazy { MutableLiveData<Product>() }
}