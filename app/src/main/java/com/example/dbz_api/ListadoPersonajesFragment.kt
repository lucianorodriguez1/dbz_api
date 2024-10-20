package com.example.dbz_api

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dbz_api.Models.PersonajeApiResponse
import com.example.dbz_api.Models.PersonajeResponse
import com.example.dbz_api.databinding.FragmentListadoPersonajesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListadoPersonajesFragment : Fragment() {

    private var _binding: FragmentListadoPersonajesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PersonajeAdapater
    private val personajes = mutableListOf<PersonajeResponse>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListadoPersonajesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        mostrarPersonajes()
    }


    private fun initRecyclerView() {
        adapter = PersonajeAdapater(personajes) { personaje ->
            // Aquí puedes manejar el click en cada personaje
            val intent = Intent(activity, InformacionDePersonaje::class.java)
            intent.putExtra("nombre", personaje.name)
            intent.putExtra("ki", personaje.ki)
            intent.putExtra("maxKi", personaje.maxKi)
            intent.putExtra("description", personaje.description)
            intent.putExtra("race", personaje.race)
            intent.putExtra("gender", personaje.gender)
            intent.putExtra("image", personaje.image)
            intent.putExtra("affiliation", personaje.affiliation)
            startActivity(intent)
        }


        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = adapter
    }


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dragonball-api.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    private fun mostrarPersonajes() {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<PersonajeApiResponse> = getRetrofit().create(APIservice::class.java)
                .getPersonajes("characters?page=1&limit=101")
            val response: PersonajeApiResponse? = call.body()

            activity?.runOnUiThread {
                if (call.isSuccessful && response != null) {
                    val personajeList: List<PersonajeResponse> = response.items
                    personajes.clear()
                    personajes.addAll(personajeList)
                    adapter.notifyDataSetChanged()
                } else {
                    mostrarError()
                }
            }
        }
    }



    private fun mostrarError() {
        Toast.makeText(activity, "Error al cargar datos", Toast.LENGTH_LONG).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
