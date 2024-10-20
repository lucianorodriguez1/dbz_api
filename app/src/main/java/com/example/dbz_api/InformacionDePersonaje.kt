package com.example.dbz_api

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
        toolbar.setLogo(R.drawable.esfera)
        supportActionBar!!.title=getString(R.string.titulo_toolbar)




        val nombre = intent.getStringExtra("nombre")
        val ki = intent.getStringExtra("ki")
        val maxKi = intent.getStringExtra("maxKi")
        val affiliation = intent.getStringExtra("affiliation")
        val race = intent.getStringExtra("race")
        val gender = intent.getStringExtra("gender")
        val description = intent.getStringExtra("description")
        val image = intent.getStringExtra("image")

        findViewById<TextView>(R.id.nombre).text = nombre
        findViewById<TextView>(R.id.baseKi).text = ki
        findViewById<TextView>(R.id.totalKi).text = maxKi
        findViewById<TextView>(R.id.affiliation).text = affiliation
        findViewById<TextView>(R.id.race).text = race
        findViewById<TextView>(R.id.gender).text = gender
        findViewById<TextView>(R.id.descripcion).text = description

        val imageView = findViewById<ImageView>(R.id.imgPersonaje)
        Picasso.get().load(image).into(imageView)

        toolbar.post {
            for (i in 0 until toolbar.childCount) {
                val view = toolbar.getChildAt(i)
                if (view is TextView && view.text == supportActionBar?.title) {
                    view.setOnClickListener {
                        // Redirigir a la ventana de inicio (MainActivity en este caso)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    break
                }
            }
        }
    }




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_listado -> {
                val intent = Intent(this, ListadoPersonajesDbz::class.java)
                startActivity(intent)
            }
            R.id.item_cerrar_sesion -> {
                val preferencias = getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
                preferencias.edit().clear().apply()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}