package com.example.carku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class home : AppCompatActivity() {
    private lateinit var tvUsername : TextView
    private lateinit var button: Button
    private lateinit var button1: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvUsername = findViewById(R.id.tvUsername)
        tvUsername.setText(intent.getStringExtra("username"))

        button = findViewById(R.id.btnCar)
        button.setOnClickListener {
            val intent = Intent( this, listMob::class.java)
            startActivity(intent)
        }
        button1 = findViewById(R.id.btnProfile)
        button1.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}