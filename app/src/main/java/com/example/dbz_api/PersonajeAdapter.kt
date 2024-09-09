package com.example.dbz_api

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PersonajesAdapter(var personajes: MutableList<Personaje>, var context: Context):
    RecyclerView.Adapter<PersonajesAdapter.PersonajeViewHolder>() {

    class PersonajeViewHolder(view: View): RecyclerView .ViewHolder(view){

        lateinit var nombre: TextView

        val btnVer : Button = view.findViewById(R.id.btnVer)

        init {
            nombre = view.findViewById(R.id.tv_nombre)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonajeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_personaje,parent,false)
        return PersonajeViewHolder(view)
    }

    override fun getItemCount() = personajes.size

    override fun onBindViewHolder(holder: PersonajeViewHolder, position: Int) {
        val item = personajes.get(position)
        holder.nombre.text = item.nombre


        holder.btnVer.setOnClickListener{

            val intent = Intent(context, InformacionDePersonaje::class.java )
            context.startActivity(intent)
        }

    }

}