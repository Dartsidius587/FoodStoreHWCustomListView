package com.example.foodstorehwcustomlistview

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider

class ActivityCreatingStore : AppCompatActivity(), IntentProduct {

    private val GALLERY_REQUEST = 302
    private var product: Product? = null
    private var photoProductUri: Uri? = null
    private var listAdapter: ListAdapter? = null
    private var itemList: Int? = null

    private lateinit var productsListVM: ProductViewModel
    private lateinit var titleCreatingTB: Toolbar
    private lateinit var editPictureProductIV: ImageView
    private lateinit var productNameET: EditText
    private lateinit var descriptionProductET: EditText
    private lateinit var productPriceET: EditText
    private lateinit var addBTN: Button
    private lateinit var productListLV: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creating_store)
        productListLV = findViewById(R.id.productListLV)
        init()

        productsListVM = ViewModelProvider(this)[ProductViewModel::class.java]

        productsListVM.getListProductsViewModel().observe(this) {
            val products = it
            listAdapter = ListAdapter(this@ActivityCreatingStore, products)
            productListLV.adapter = listAdapter
            listAdapter?.notifyDataSetChanged()
        }

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

        addBTN.setOnClickListener {
            createProduct()
            clearEditFelds()
            listAdapter?.notifyDataSetChanged()
        }

        productListLV.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val product = listAdapter!!.getItem(position)
                photoProductUri = null
                itemList = position
                intentProduct(product as Product)
            }
    }

    override fun onResume() {
        super.onResume()
        productsListVM.getListProductsViewModel().observe(this) {
            val products = it
            listAdapter = ListAdapter(this@ActivityCreatingStore, products)
            productListLV.adapter = listAdapter
            listAdapter?.notifyDataSetChanged()
        }
    }

    private fun createProduct() {
        val productName = productNameET.text.toString()
        val productDescription = descriptionProductET.text.toString()
        val productPrice = productPriceET.text.toString()
        val productPicture = photoProductUri.toString()
        product = Product(productName, productDescription, productPrice, productPicture)
        productsListVM.addProductList(product!!)
        clearEditFelds()
    }

    private fun clearEditFelds() {
        productNameET.text.clear()
        descriptionProductET.text.clear()
        productPriceET.text.clear()
        editPictureProductIV.setImageResource(R.drawable.ic_product)
        photoProductUri = null
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
        title = "\tАссортимент магазина"
        titleCreatingTB.setTitleTextColor(Color.BLUE)
        titleCreatingTB.setLogo(R.drawable.ic_title_shop)

        editPictureProductIV = findViewById(R.id.editPictureProductIV)
        productNameET = findViewById(R.id.productNameET)
        descriptionProductET = findViewById(R.id.descriptionProductET)
        productPriceET = findViewById(R.id.productPriceET)
        addBTN = findViewById(R.id.addBTN)

    }

    override fun intentProduct(product: Product) {

        val intent = Intent(this, ProductInfoActivity::class.java)
        intent.putExtra("product", product)
        intent.putExtra("position", itemList)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_creating_product, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuExit) {
            Toast.makeText(this,"Программа завершена.",Toast.LENGTH_LONG).show()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}