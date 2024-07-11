package com.example.foodstorehwcustomlistview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class ListAdapter(context: Context, productList: MutableList<Product>) :
    ArrayAdapter<Product>(context, R.layout.item_list, productList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val product = getItem(position)
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)
        }
        addList(view, product)
        return view!!
    }

    private fun addList(
        view: View?,
        product: Product?
    ) {
        val pictureProductIV = view?.findViewById<ImageView>(R.id.pictureProductIV)
        val productNameTV = view?.findViewById<TextView>(R.id.productNameTV)
        val productPriceTV = view?.findViewById<TextView>(R.id.productPriceTV)

        pictureProductIV?.setImageBitmap(product?.picture)
        productNameTV?.text = product?.name
        productPriceTV?.text = product?.price
    }
}