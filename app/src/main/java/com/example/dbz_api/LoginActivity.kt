package com.example.dbz_api

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dbz_api.baseDeDatos.FuncBBDD
import com.example.dbz_api.baseDeDatos.SQLite

class LoginActivity : AppCompatActivity() {
    lateinit var etUsuario: EditText
    lateinit var etPassword: EditText
    lateinit var cbRecordarusuario: CheckBox
    lateinit var btnRgegristarse: Button
    lateinit var btnIniciarsesion: Button
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var basededatos : FuncBBDD



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        toolbar = findViewById(R.id.toolbar)
        toolbar.setLogo(R.drawable.ic_android_black_24dp)
        setSupportActionBar(toolbar)
        supportActionBar!!.title="trabajo practico"
        etUsuario = findViewById(R.id.etUsuario)
        etPassword = findViewById(R.id.etPassword)
        btnRgegristarse = findViewById(R.id.btnRegistrarse)
        cbRecordarusuario = findViewById(R.id.cbRecordarusuario)
        btnIniciarsesion = findViewById(R.id.btnIniciarsesion)
        basededatos = FuncBBDD()


        //TEST
            var preferencias = getSharedPreferences(resources.getString(R.string.sp_credenciales),
                MODE_PRIVATE);
            var usuarioGuardado = preferencias.getString(resources.getString(R.string.nombre_usuario),"");
            var passwordGuardado = preferencias.getString(resources.getString(R.string.password_usuario),"");

        if(usuarioGuardado != "" && passwordGuardado != ""){
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //FIN TEST

        btnRgegristarse.setOnClickListener {
            Toast.makeText(this, "crear usuario", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, TerminosCondiciones::class.java)
            startActivity(intent)
        }

        val dbHelper = SQLite(this, "login", null, 1)
        val db = dbHelper.writableDatabase

        btnIniciarsesion.setOnClickListener {
            var mensaje = "boton iniciar sesion"
            if (etUsuario.text.toString().isEmpty() || etPassword.text.toString().isEmpty()) {
                mensaje += " completar datos"
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
            } else {
                // Mover toda la lógica de verificación de login al callback
                basededatos.verificarLogin(this, etUsuario.text.toString(), etPassword.text.toString()) { verificacionDeInicioSesion ->
                    // Si el checkbox está marcado, guardamos las credenciales
                    if (cbRecordarusuario.isChecked) {
                        val preferencias = getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
                        preferencias.edit().putString(resources.getString(R.string.nombre_usuario), etUsuario.text.toString()).apply()
                        preferencias.edit().putString(resources.getString(R.string.password_usuario), etPassword.text.toString()).apply()
                    }

                    if (verificacionDeInicioSesion != -1) {
                        // Login exitoso
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        // Login fallido
                        Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

    }
}