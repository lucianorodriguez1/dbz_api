package com.example.dbz_api

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dbz_api.baseDeDatos.FuncBBDD
import com.example.dbz_api.baseDeDatos.SQLite

class LoginActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    private val PERMISSION_REQUEST_CODE = 1001
    lateinit var etUsuario: EditText
    lateinit var etPassword: EditText
    lateinit var cbRecordarusuario: CheckBox
    lateinit var btnRgegristarse: Button
    lateinit var btnIniciarsesion: Button
    lateinit var basededatos: FuncBBDD


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
        setSupportActionBar(toolbar)
        toolbar.setLogo(R.drawable.esfera)
        supportActionBar!!.title=getString(R.string.titulo_toolbar)

        etUsuario = findViewById(R.id.etUsuario)
        etPassword = findViewById(R.id.etPassword)
        btnRgegristarse = findViewById(R.id.btnRegistrarse)
        cbRecordarusuario = findViewById(R.id.cbRecordarusuario)
        btnIniciarsesion = findViewById(R.id.btnIniciarsesion)
        basededatos = FuncBBDD()

        crearCanalNotificacion()

        var preferencias =
            getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
        var usuarioGuardado =
            preferencias.getString(resources.getString(R.string.nombre_usuario), "")
        var passwordGuardado =
            preferencias.getString(resources.getString(R.string.password_usuario), "")

        if (usuarioGuardado != "" && passwordGuardado != "") {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnRgegristarse.setOnClickListener {
            val intent = Intent(this, TerminosCondiciones::class.java)
            startActivity(intent)
        }


        btnIniciarsesion.setOnClickListener {
            var mensaje = ""
            if (etUsuario.text.toString().isEmpty() || etPassword.text.toString().isEmpty()) {
                mensaje += " completar datos"
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
            } else {
                basededatos.verificarLogin(
                    this,
                    etUsuario.text.toString(),
                    etPassword.text.toString()
                ) { verificacionDeInicioSesion ->

                    if (cbRecordarusuario.isChecked) {
                        val preferencias = getSharedPreferences(
                            resources.getString(R.string.sp_credenciales),
                            MODE_PRIVATE
                        )
                        preferencias.edit().putString(
                            resources.getString(R.string.nombre_usuario),
                            etUsuario.text.toString()
                        ).apply()
                        preferencias.edit().putString(
                            resources.getString(R.string.password_usuario),
                            etPassword.text.toString()
                        ).apply()
                    }

                    if (cbRecordarusuario.isChecked) {
                        var preferencias = getSharedPreferences(
                            resources.getString(R.string.sp_credenciales),
                            MODE_PRIVATE
                        )
                        preferencias.edit().putString(
                            resources.getString(R.string.nombre_usuario),
                            etUsuario.text.toString()
                        ).apply()

                        verificarPermisosNotificacion()
                    }

                    if (verificacionDeInicioSesion != -1) {

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {

                        Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }

    }


    private fun crearCanalNotificacion() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notificaciones"
            val descriptionText = "Canal para notificaciones de usuario"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("canal_id", name, importance).apply {
                description = descriptionText
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }


    }

    private fun verificarPermisosNotificacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    PERMISSION_REQUEST_CODE
                )
            } else {
                mostrarNotificacion()
            }
        } else {
            mostrarNotificacion()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    mostrarNotificacion()
                } else {
                    Toast.makeText(this, "Permiso de notificaciones denegado", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun mostrarNotificacion() {
        if (NotificationManagerCompat.from(this).areNotificationsEnabled()) {
            val builder = NotificationCompat.Builder(this, "canal_id")
                .setSmallIcon(R.drawable.mi_imagen)
                .setContentTitle("Usuario Recordado")
                .setContentText("Has activado la opción de recordar usuario.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val notificationManager = NotificationManagerCompat.from(this)
            notificationManager.notify(1, builder.build())
        } else {

            Toast.makeText(this, "Las notificaciones fueron deshabilitadas", Toast.LENGTH_SHORT)
                .show()
        }
    }


}