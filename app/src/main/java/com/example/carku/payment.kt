package com.example.carku

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class payment : AppCompatActivity(){
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.payment)

        button = findViewById(R.id.btnPaypal)
        button.setOnClickListener {
            val intent = Intent(this, paypal::class.java)
            startActivity(intent)
        }
    }

}
