package com.example.foodstorehwcustomlistview

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var creatingBTN:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        creatingBTN=findViewById(R.id.creatingBTN)

        creatingBTN.setOnClickListener{
            val intent = Intent(this, ActivityCreatingStore::class.java)
            startActivity(intent)
            finish()
        }
    }
}