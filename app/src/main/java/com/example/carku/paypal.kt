package com.example.carku

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class paypal : AppCompatActivity(){
    private lateinit var button: Button
    private lateinit var email: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.paypal)

        email = findViewById(R.id.txtEmail)

        button=findViewById(R.id.btnNext)
        button.setOnClickListener {
            val intent = Intent( this, confirm::class.java)
            intent.putExtra("email", email.text.toString())
            startActivity(intent)
        }
    }
}
