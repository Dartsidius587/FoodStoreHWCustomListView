package com.example.foodstorehwcustomlistview

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.io.IOException

class ActivityCreatingStore : AppCompatActivity() {

    private val GALLERY_REQUEST = 302

    private var bitmap: Bitmap? = null
    private val products: MutableList<Product> = mutableListOf()

    private lateinit var titleCreatingTB: Toolbar
    private lateinit var editPictureProductIV: ImageView
    private lateinit var productNameET: EditText
    private lateinit var productPriceET:EditText
    private lateinit var addBTN:Button
    private lateinit var productListLV: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creating_store)
        init()

        editPictureProductIV.setOnClickListener{
            val picturePickerIntent = Intent(Intent.ACTION_PICK)
            picturePickerIntent.type = "image/*"
            startActivityForResult(picturePickerIntent, GALLERY_REQUEST)
        }

        addBTN.setOnClickListener{
            createProduct()

            val adapter = ListAdapter(this@ActivityCreatingStore, products)
            productListLV.adapter = adapter
            adapter.notifyDataSetChanged()
            clearEditFelds()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        editPictureProductIV = findViewById(R.id.editPictureProductIV)
        when(requestCode){
            GALLERY_REQUEST -> if(resultCode === RESULT_OK){
                val selectedPicture: Uri? = data?.data
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPicture)
                } catch (e:IOException){
                    e.printStackTrace()
                }
                editPictureProductIV.setImageBitmap(bitmap)
            }
        }
    }

    private fun clearEditFelds() {
        productNameET.text.clear()
        productPriceET.text.clear()
        editPictureProductIV.setImageResource(R.drawable.ic_product)
    }

    private fun createProduct() {
        val productName = productNameET.text.toString()
        val productPrice = productPriceET.text.toString()
        val productPicture = bitmap
        val product = Product(productName, productPrice, productPicture)
        products.add(product)
    }

    private fun init(){
        titleCreatingTB=findViewById(R.id.titleCreatingTB)
        setSupportActionBar(titleCreatingTB)
        title="\tАссортимент магазина"
        titleCreatingTB.setTitleTextColor(Color.BLUE)
        titleCreatingTB.setLogo(R.drawable.ic_title_shop)

        editPictureProductIV=findViewById(R.id.editPictureProductIV)
        productNameET=findViewById(R.id.productNameET)
        productPriceET=findViewById(R.id.productPriceET)
        addBTN=findViewById(R.id.addBTN)
        productListLV=findViewById(R.id.productListLV)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mune_exit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menuExit) finish()
        return super.onOptionsItemSelected(item)
    }
}