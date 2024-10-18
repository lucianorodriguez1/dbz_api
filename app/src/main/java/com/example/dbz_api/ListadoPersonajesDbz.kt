package com.example.dbz_api

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dbz_api.Models.PersonajeApiResponse
import com.example.dbz_api.Models.PersonajeResponse
import com.example.dbz_api.databinding.ActivityListadoPersonajesDbzBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListadoPersonajesDbz : AppCompatActivity() {

    private lateinit var binding: ActivityListadoPersonajesDbzBinding
    private lateinit var adapter: PersonajeAdapater
    private val personajes = mutableListOf<PersonajeResponse>()
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityListadoPersonajesDbzBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.right, systemBars.right, systemBars.bottom)
            insets
        }

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setLogo(R.drawable.ic_android_black_24dp)
        supportActionBar!!.title=getString(R.string.titulo_toolbar)

        initRecyclerView()

        mostrarPersonajes()

    }

    private fun initRecyclerView(){

        adapter = PersonajeAdapater(personajes){personaje->
            val intent = Intent(this,InformacionDePersonaje::class.java)

            intent.putExtra("nombre",personaje.name)
            intent.putExtra("ki",personaje.ki)
            intent.putExtra("maxKi",personaje.maxKi)
            intent.putExtra("description",personaje.description)
            intent.putExtra("race",personaje.race)
            intent.putExtra("gender",personaje.gender)
            intent.putExtra("image",personaje.image)
            intent.putExtra("affiliation",personaje.affiliation)

            startActivity(intent)
        }

        binding.recyclerView.layoutManager = GridLayoutManager(this,2)
        binding.recyclerView.adapter = adapter

    }

    private fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl("https://dragonball-api.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    private fun mostrarPersonajes(){

        CoroutineScope(Dispatchers.IO).launch {

            val call: Response<PersonajeApiResponse> = getRetrofit().create(APIservice::class.java).getPersonajes("characters?page=1&limit=101")
            val response:PersonajeApiResponse? = call.body()

            runOnUiThread{
                if(call.isSuccessful && response != null){

                    val personajeList: List<PersonajeResponse> = response.items

                    personajes.clear()
                    personajes.addAll(personajeList)

                    adapter.notifyDataSetChanged()


                }else {
                    mostrarError()
                }
            }

        }

    }

    private fun mostrarError(){

        Toast.makeText(this,"Error al cargar datos", Toast.LENGTH_LONG).show()

    }


}