package com.example.foodstorehwcustomlistview

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class ActivityCreatingStore : AppCompatActivity(), IntentProduct {

    private val GALLERY_REQUEST = 302

    private var photoProductUri: Uri? = null
    private var product: Product? = null
    private var products: MutableList<Product> = mutableListOf()
    private var listAdapter: ListAdapter? = null
    private var itemList: Int? = null
    private var check = true

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
        init()

        editPictureProductIV.setOnClickListener {
            val picturePickerIntent = Intent(Intent.ACTION_PICK)
            picturePickerIntent.type = "image/*"
            startActivityForResult(picturePickerIntent, GALLERY_REQUEST)
        }

        addBTN.setOnClickListener {
            createProduct()
            listAdapter = ListAdapter(this@ActivityCreatingStore, products)
            productListLV.adapter = listAdapter
            listAdapter?.notifyDataSetChanged()
            clearEditFelds()
            photoProductUri = null
            listAdapter?.notifyDataSetChanged()
        }

        productListLV.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val product = listAdapter!!.getItem(position)
                itemList = position
                val args = Bundle()
                args.putSerializable("product", product)
                intentProduct(product as Product)
                finish()
            }

    }

    override fun onResume() {
        super.onResume()
        check = intent.extras?.getBoolean("newCheck") ?: true
        if (!check) {
            products = intent.getSerializableExtra("list") as MutableList<Product>
            listAdapter = ListAdapter(this, products)
            check = true
        }
        productListLV.adapter = listAdapter
    }

    override fun intentProduct(product: Product) {
        val intent = Intent(this, ProductInfoActivity::class.java)
        intent.putExtra("product", product)
        intent.putExtra("products", this.products as ArrayList<Product>)
        intent.putExtra("position", itemList)
        intent.putExtra("check", check)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        editPictureProductIV = findViewById(R.id.editPictureProductIV)
        when (requestCode) {
            GALLERY_REQUEST -> if (resultCode === RESULT_OK) {
                photoProductUri = data?.data
                editPictureProductIV.setImageURI(photoProductUri)
            }
        }
    }

    private fun clearEditFelds() {
        productNameET.text.clear()
        descriptionProductET.text.clear()
        productPriceET.text.clear()
        editPictureProductIV.setImageResource(R.drawable.ic_product)

    }

    private fun createProduct() {
        val productName = productNameET.text.toString()
        val productDescription = descriptionProductET.text.toString()
        val productPrice = productPriceET.text.toString()
        val productPicture = photoProductUri.toString()
        product = Product(productName, productDescription, productPrice, productPicture)
        products.add(product!!)
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
        productListLV = findViewById(R.id.productListLV)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_creating_product, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuExit) finish()
        return super.onOptionsItemSelected(item)
    }
}