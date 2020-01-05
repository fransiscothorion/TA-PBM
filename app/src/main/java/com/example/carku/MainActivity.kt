package com.example.carku

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var userName : EditText
    private lateinit var button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        userName = findViewById(R.id.tvUsername)
        button = findViewById(R.id.btnLogin)
        button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("username", userName.text.toString())
            startActivity(intent)
        }

    }
}

