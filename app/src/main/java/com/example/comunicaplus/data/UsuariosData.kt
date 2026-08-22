package com.example.comunicaplus.data

import com.example.comunicaplus.model.Usuario

// Array con capacidad máxima de 5 usuarios
val usuariosRegistrados: Array<Usuario?> = arrayOfNulls(5)

var cantidadUsuariosRegistrados = 0

fun registrarUsuario(usuario: Usuario): Boolean {

    if (cantidadUsuariosRegistrados >= usuariosRegistrados.size) {
        return false
    }

    usuariosRegistrados[cantidadUsuariosRegistrados] = usuario
    cantidadUsuariosRegistrados++

    return true
}

fun correoYaRegistrado(correo: String): Boolean {

    return usuariosRegistrados
        .filterNotNull()
        .any {
            it.correo.equals(
                correo,
                ignoreCase = true
            )
        }
}

fun validarCredenciales(
    correo: String,
    password: String
): Boolean {

    return usuariosRegistrados
        .filterNotNull()
        .any {
            it.correo.equals(
                correo,
                ignoreCase = true
            ) &&
                    it.password == password
        }
}