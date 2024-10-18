package com.example.dbz_api

import com.example.dbz_api.Models.PersonajeApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIservice {

    @GET
    suspend fun getPersonajes(@Url url: String): Response<PersonajeApiResponse>
}