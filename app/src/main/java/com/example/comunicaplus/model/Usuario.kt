package com.example.comunicaplus.model

data class Usuario(
    val id: Int,
    val nombre: String,
    val correo: String,
    val password: String,
    val rangoEdad: String,
    val tipoComunicacion: String
)