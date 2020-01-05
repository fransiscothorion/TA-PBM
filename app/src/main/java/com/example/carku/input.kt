package com.example.carku

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class input : AppCompatActivity() {
    private lateinit var txtNama : EditText
    private lateinit var txtAlamat : EditText
    private lateinit var txtNomor : EditText
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input)
        txtNama=findViewById(R.id.txtNama)

        txtAlamat=findViewById(R.id.txtAlamat)

        txtNomor=findViewById(R.id.txtNomor)

        button=findViewById(R.id.btnBook)
        button.setOnClickListener {
            val intent = Intent ( this, payment::class.java)
            intent.putExtra("nama", txtNama.text.toString())
            intent.putExtra("alamat", txtAlamat.text.toString())
            intent.putExtra("nomor", txtNomor.text.toString())
            startActivity(intent)
        }
    }
}
