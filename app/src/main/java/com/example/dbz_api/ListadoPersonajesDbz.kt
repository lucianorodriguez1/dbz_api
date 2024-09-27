package com.example.dbz_api

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class ListadoPersonajesDbz : AppCompatActivity() {

    lateinit var rvPersonajes : RecyclerView
    lateinit var personajesAdapater : PersonajeAdapater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listado_personajes_dbz)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvPersonajes = findViewById(R.id.rvPersonajes)
        personajesAdapater = PersonajeAdapater(getPersonajes(),this)


        rvPersonajes.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        rvPersonajes.adapter = personajesAdapater

    }

    private fun getPersonajes() : MutableList<Personaje>{
        var personajes : MutableList<Personaje> = ArrayList()
        personajes.add(Personaje(1, "Goku"))
        personajes.add(Personaje(2, "Vegeta"))
        personajes.add(Personaje(3, "Gohan"))
        personajes.add(Personaje(4, "Piccolo"))
        personajes.add(Personaje(5, "Trunks"))
        personajes.add(Personaje(6, "Krillin"))
        personajes.add(Personaje(7, "Freezer"))
        personajes.add(Personaje(8, "Cell"))
        personajes.add(Personaje(9, "Majin Buu"))
        personajes.add(Personaje(10, "Bulma"))

        return personajes
    }
}