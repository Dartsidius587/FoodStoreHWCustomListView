package com.example.foodstorehwcustomlistview

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class ProductInfoActivity : AppCompatActivity() {

    private lateinit var titleCreatingTB: Toolbar
    private lateinit var editPictureProductIV: ImageView
    private lateinit var productNameTV: TextView
    private lateinit var descriptionProductTV: TextView
    private lateinit var productPriceTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_info)
        init()
        editPictureProductIV.setImageURI(Uri.parse(intent.getStringExtra("picture")))
        productNameTV.text = intent.getStringExtra("name")
        descriptionProductTV.text = intent.getStringExtra("description")
        productPriceTV.text = intent.getStringExtra("price")
    }

    private fun init() {
        titleCreatingTB = findViewById(R.id.titleCreatingTB)
        setSupportActionBar(titleCreatingTB)
        title = "\tИнформация о продукте"
        titleCreatingTB.setTitleTextColor(Color.BLUE)
        titleCreatingTB.setLogo(R.drawable.ic_title_shop)

        editPictureProductIV = findViewById(R.id.editPictureProductIV)
        productNameTV = findViewById(R.id.productNameTV)
        descriptionProductTV = findViewById(R.id.descriptionProductTV)
        productPriceTV = findViewById(R.id.productPriceTV)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_exit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuExit) finish()
        return super.onOptionsItemSelected(item)
    }
}