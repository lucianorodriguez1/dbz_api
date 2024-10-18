package com.example.dbz_api

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionScene.Transition.TransitionOnClick
import androidx.recyclerview.widget.RecyclerView
import com.example.dbz_api.Models.PersonajeResponse
import com.example.dbz_api.databinding.ItemPersonajeBinding
import com.squareup.picasso.Picasso

class PersonajeAdapater(val personajes: List<PersonajeResponse>, val onClick: (PersonajeResponse)->Unit):
    RecyclerView.Adapter<PersonajeAdapater.PersonajeViewHolder>() {

    class PersonajeViewHolder(view: View): RecyclerView.ViewHolder(view){

        private val binding = ItemPersonajeBinding.bind(view)

        fun bind(pj:PersonajeResponse){

            Picasso.get().load(pj.image).into(binding.imgPersonaje)
            binding.tvnamePj.text=pj.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonajeViewHolder {
        val view = LayoutInflater.from(parent.context)
        return PersonajeViewHolder(view.inflate(R.layout.item_personaje,parent,false))
    }

    override fun getItemCount():Int{

        return personajes.size

    }

    override fun onBindViewHolder(holder: PersonajeViewHolder, position: Int) {
        val item:PersonajeResponse = personajes[position]
        holder.bind(item)


        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

}