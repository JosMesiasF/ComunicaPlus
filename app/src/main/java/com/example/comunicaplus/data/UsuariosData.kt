package com.example.comunicaplus.data

import com.example.comunicaplus.model.Usuario

// Array con capacidad máxima de 5 usuarios
val usuariosRegistrados: Array<Usuario?> = arrayOfNulls(5)

var cantidadUsuariosRegistrados = 0

// Registra un usuario dentro del array
fun registrarUsuario(usuario: Usuario): Boolean {

    if (cantidadUsuariosRegistrados >= usuariosRegistrados.size) {
        return false
    }

    usuariosRegistrados[cantidadUsuariosRegistrados] = usuario
    cantidadUsuariosRegistrados++

    return true
}

// Convierte los elementos no nulos del Array en una List
fun obtenerUsuariosRegistrados(): List<Usuario> {

    return usuariosRegistrados
        .filterNotNull()
}

// Verifica si el correo ya existe
fun correoYaRegistrado(correo: String): Boolean {

    return obtenerUsuariosRegistrados()
        .any { usuario ->

            usuario.correo.equals(
                correo,
                ignoreCase = true
            )
        }
}

// Busca y devuelve un usuario según su correo
fun buscarUsuarioPorCorreo(
    correo: String
): Usuario? {

    return obtenerUsuariosRegistrados()
        .firstOrNull { usuario ->

            usuario.correo.equals(
                correo,
                ignoreCase = true
            )
        }
}

// Valida correo y contraseña para el Login
fun validarCredenciales(
    correo: String,
    password: String
): Boolean {

    return obtenerUsuariosRegistrados()
        .any { usuario ->

            usuario.correo.equals(
                correo,
                ignoreCase = true
            ) &&
                    usuario.password == password
        }
}

// Obtiene solamente los correos registrados
fun obtenerCorreosRegistrados(): List<String> {

    return obtenerUsuariosRegistrados()
        .map { usuario ->

            usuario.correo
        }
}