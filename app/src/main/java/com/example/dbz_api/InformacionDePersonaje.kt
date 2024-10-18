package com.example.dbz_api

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso

class InformacionDePersonaje : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_informacion_de_personaje)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setLogo(R.drawable.ic_android_black_24dp)
        supportActionBar!!.title="DBZ API"



        val nombre = intent.getStringExtra("nombre")
        val ki = intent.getStringExtra("ki")
        val maxKi = intent.getStringExtra("maxKi")
        val affiliation = intent.getStringExtra("affiliation")
        val race = intent.getStringExtra("race")
        val gender = intent.getStringExtra("gender")
        val description = intent.getStringExtra("description")
        val image = intent.getStringExtra("image")

        // Configurar los valores en los TextViews y la ImageView
        findViewById<TextView>(R.id.nombre).text = nombre
        findViewById<TextView>(R.id.baseKi).text = ki
        findViewById<TextView>(R.id.totalKi).text = maxKi
        findViewById<TextView>(R.id.affiliation).text = affiliation
        findViewById<TextView>(R.id.race).text = race
        findViewById<TextView>(R.id.gender).text = gender
        findViewById<TextView>(R.id.descripcion).text = description

        // Cargar la imagen con Picasso o Glide
        val imageView = findViewById<ImageView>(R.id.imgPersonaje)
        Picasso.get().load(image).into(imageView)

    }
}