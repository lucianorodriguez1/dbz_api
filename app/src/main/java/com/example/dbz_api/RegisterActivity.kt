package com.example.dbz_api

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegisterActivity : AppCompatActivity() {
    lateinit var editTextNombre : EditText
    lateinit var editEmail : EditText
    lateinit var editContraseña : EditText
    lateinit var buttonRegistrar : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        editTextNombre = findViewById(R.id.editTextNombre)
        editEmail = findViewById(R.id.editEmail)
        editContraseña = findViewById(R.id.editContraseña)
        buttonRegistrar = findViewById(R.id.buttonRegistrar)

        buttonRegistrar.setOnClickListener{
            val nombre = editTextNombre.text.toString()
            val email = editEmail.text.toString()
            val contraseña = editContraseña.text.toString()
            if(nombre.isEmpty() || email.isEmpty() || contraseña.isEmpty()) {
                Toast.makeText( this,"completar todos los datos", Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(this, "Registro completado con exito", Toast.LENGTH_SHORT).show()

                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)

            }








        }
    }
}