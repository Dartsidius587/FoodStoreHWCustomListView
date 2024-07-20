package com.example.foodstorehwcustomlistview

import android.content.Intent
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

    val GALLERY_REQUEST = 302
    private var product: Product? = null
    private var photoProductUri: Uri? = null

    private lateinit var titleCreatingTB: Toolbar
    private lateinit var editPictureProductIV: ImageView
    private lateinit var productNameTV: TextView
    private lateinit var descriptionProductTV: TextView
    private lateinit var productPriceTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_info)
        init()

        product = intent.extras?.getSerializable("product") as Product

        val name = product?.name
        val description = product?.description
        val price = product?.price
        val picture: Uri? = Uri.parse(product?.picture)

        productNameTV.text = name
        descriptionProductTV.text = description
        productPriceTV.text = price
        editPictureProductIV.setImageURI(picture)

        editPictureProductIV.setOnClickListener {
            val pictureIntent = Intent(Intent.ACTION_PICK)
            pictureIntent.type = "image/*"
            startActivityForResult(pictureIntent, GALLERY_REQUEST)
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
                val products = intent.getSerializableExtra("products")
                var check = intent.extras?.getBoolean("check")
                val product = Product(
                    productNameTV.text.toString(),
                    descriptionProductTV.text.toString(),
                    productPriceTV.text.toString(),
                    photoProductUri.toString()
                )
                val list: MutableList<Product> = products as MutableList<Product>
                if (itemList != null) {
                    swap(itemList, product, products)
                }
                check = false
                val intent = Intent(this, ActivityCreatingStore::class.java)
                intent.putExtra("list", list as ArrayList<Product>)
                intent.putExtra("newCheck", check)
                startActivity(intent)
                finish()
            }

            R.id.menuExit -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun swap(item: Int, product: Product, products: MutableList<Product>) {
        products.add(item + 1, product)
        products.removeAt(item)
    }
}