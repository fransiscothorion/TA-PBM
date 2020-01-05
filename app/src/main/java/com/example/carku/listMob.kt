package com.example.carku

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class listMob : AppCompatActivity(){
    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list)

        button = findViewById(R.id.btnBook)
        button.setOnClickListener {
            val intent = Intent( this, input::class.java)
            startActivity(intent)
        }
    }

}
