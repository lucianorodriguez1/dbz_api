package com.example.dbz_api

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TerminosCondiciones : AppCompatActivity() {
    lateinit var btn_aceptar_tyc: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_terminos_condiciones)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btn_aceptar_tyc = findViewById(R.id.btn_aceptar_tyc)
        btn_aceptar_tyc.setOnClickListener{
            Log.i("TODO","se aceptan los terminos y condiciones")
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}