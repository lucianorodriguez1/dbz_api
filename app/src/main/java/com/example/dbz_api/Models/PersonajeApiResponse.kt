package com.example.dbz_api.Models

data class PersonajeApiResponse(
    val items: List<PersonajeResponse>,
    val links: Links,
    val meta: Meta
)