package com.example.foodstorehwcustomlistview

import java.io.Serializable

class Product(
    val name: String,
    val description: String,
    val price: String,
    val picture: String?
) : Serializable