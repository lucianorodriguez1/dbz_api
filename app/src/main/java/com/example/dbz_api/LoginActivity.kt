package com.example.dbz_api

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    lateinit var etUsuario: EditText
    lateinit var etPassword: EditText
    lateinit var cbRecordarusuario: CheckBox
    lateinit var btnRgegristarse: Button
    lateinit var btnIniciarsesion: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        etUsuario = findViewById(R.id.etUsuario)
        etPassword = findViewById(R.id.etPassword)
        btnRgegristarse = findViewById(R.id.btnRegistrarse)
        cbRecordarusuario = findViewById(R.id.cbRecordarusuario)
        btnIniciarsesion = findViewById(R.id.btnIniciarsesion)

        btnRgegristarse.setOnClickListener {
            Toast.makeText(this, "crear usuario", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, TerminosCondiciones::class.java)
            startActivity(intent)
        }

        btnIniciarsesion.setOnClickListener {
            var mensaje = "boton iniciar sesion"
            if (etUsuario.text.toString().isEmpty() || etPassword.text.toString().isEmpty()) {
                mensaje += " completar datos"
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
            } else {

                var intent = Intent(this, ListadoPersonajesDbz::class.java)
                startActivity(intent)
            }

        }
    }

}