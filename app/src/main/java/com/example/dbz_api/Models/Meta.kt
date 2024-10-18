package com.example.dbz_api.Models

data class Meta(
    val currentPage: Int,
    val itemCount: Int,
    val itemsPerPage: Int,
    val totalItems: Int,
    val totalPages: Int
)