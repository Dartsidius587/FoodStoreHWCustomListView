package com.example.foodstorehwcustomlistview

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider

class ProductInfoActivity : AppCompatActivity() {

    private val GALLERY_REQUEST = 302
    private var photoProductUri: Uri? = null
    private lateinit var productsListVM: ProductViewModel

    private lateinit var titleCreatingTB: Toolbar
    private lateinit var editPictureProductIV: ImageView
    private lateinit var productNameTV: TextView
    private lateinit var descriptionProductTV: TextView
    private lateinit var productPriceTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_info)
        init()

        val product = intent.extras?.getSerializable("product") as Product

        val name = product.name
        val description = product.description
        val price = product.price
        val picture: Uri? = Uri.parse(product.picture)

        productNameTV.text = name
        descriptionProductTV.text = description
        productPriceTV.text = price
        editPictureProductIV.setImageURI(picture)
        photoProductUri = picture

        productsListVM = ViewModelProvider(this)[ProductViewModel::class.java]

        editPictureProductIV.setOnClickListener {
            val picturePickerIntent: Intent?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                picturePickerIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            } else {
                picturePickerIntent = Intent(Intent.ACTION_PICK)
            }
            picturePickerIntent.type = "image/*"
            startActivityForResult(picturePickerIntent, GALLERY_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        editPictureProductIV = findViewById(R.id.editPictureProductIV)

        when (requestCode) {
            GALLERY_REQUEST -> if (resultCode === RESULT_OK) {
                photoProductUri = data?.data
                editPictureProductIV.setImageURI(photoProductUri)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
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
        menuInflater.inflate(R.menu.menu_product_info, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuBack -> {
                val itemList = intent.extras?.getInt("position")
                val product = Product(
                    productNameTV.text.toString(),
                    descriptionProductTV.text.toString(),
                    productPriceTV.text.toString(),
                    photoProductUri.toString()
                )
                productsListVM.updateProductsListViewModel(product, itemList)
                photoProductUri = null
                val intent = Intent(this, ActivityCreatingStore::class.java)
                startActivity(intent)
                finish()
            }

            R.id.menuExit -> {
                Toast.makeText(this,"Программа завершена.", Toast.LENGTH_LONG).show()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}