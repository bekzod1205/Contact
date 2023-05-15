package com.example.contact

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.contact.database.AppDatabase
import com.example.contact.database.Contact

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}