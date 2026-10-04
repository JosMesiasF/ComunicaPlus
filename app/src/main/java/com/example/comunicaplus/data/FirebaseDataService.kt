package com.example.comunicaplus.data

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseDataService {

    private val auth =
        FirebaseAuth.getInstance()

    private val db =
        FirebaseFirestore.getInstance()


    // =====================================================
    // OBTENER UID DEL USUARIO ACTUAL
    // =====================================================

    fun obtenerUidActual(): String? {
        return auth.currentUser?.uid
    }


    // =====================================================
    // LEER PERFIL DEL USUARIO
    // READ
    // =====================================================

    fun obtenerPerfilUsuario(
        onSuccess: (Map<String, Any>) -> Unit,
        onError: (String) -> Unit
    ) {

        val uid = obtenerUidActual()

        if (uid == null) {

            onError(
                "No existe una sesión activa."
            )

            return
        }

        db.collection("usuarios")
            .document(uid)
            .get()
            .addOnSuccessListener { documento ->

                if (documento.exists()) {

                    onSuccess(
                        documento.data ?: emptyMap()
                    )

                } else {

                    onError(
                        "No se encontró el perfil del usuario."
                    )
                }
            }
            .addOnFailureListener {

                onError(
                    "No fue posible obtener los datos del usuario."
                )
            }
    }


    // =====================================================
    // ACTUALIZAR PERFIL
    // UPDATE
    // =====================================================

    fun actualizarPerfilUsuario(
        nombre: String,
        rangoEdad: String,
        tipoComunicacion: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        val uid = obtenerUidActual()

        if (uid == null) {

            onError(
                "No existe una sesión activa."
            )

            return
        }

        val datosActualizados =
            mapOf(
                "nombre" to nombre,
                "rangoEdad" to rangoEdad,
                "tipoComunicacion" to tipoComunicacion
            )

        db.collection("usuarios")
            .document(uid)
            .update(datosActualizados)
            .addOnSuccessListener {

                onSuccess()
            }
            .addOnFailureListener {

                onError(
                    "No fue posible actualizar el perfil."
                )
            }
    }


    // =====================================================
    // GUARDAR PREFERENCIAS DE ACCESIBILIDAD
    // UPDATE
    // =====================================================

    fun guardarPreferenciasAccesibilidad(
        textoGrande: Boolean,
        altoContraste: Boolean,
        vibracion: Boolean,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        val uid = obtenerUidActual()

        if (uid == null) {

            onError(
                "No existe una sesión activa."
            )

            return
        }

        val preferencias =
            mapOf(
                "textoGrande" to textoGrande,
                "altoContraste" to altoContraste,
                "vibracion" to vibracion
            )

        db.collection("usuarios")
            .document(uid)
            .update(preferencias)
            .addOnSuccessListener {

                onSuccess()
            }
            .addOnFailureListener {

                onError(
                    "No fue posible guardar las preferencias."
                )
            }
    }


    // =====================================================
    // GUARDAR MENSAJE ESCRITO
    // CREATE
    // =====================================================

    fun guardarMensaje(
        texto: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        val uid = obtenerUidActual()

        if (uid == null) {

            onError(
                "No existe una sesión activa."
            )

            return
        }

        val mensaje =
            hashMapOf<String, Any>(
                "texto" to texto.trim(),
                "creadoEn" to FieldValue.serverTimestamp()
            )

        db.collection("usuarios")
            .document(uid)
            .collection("mensajes")
            .add(mensaje)
            .addOnSuccessListener {

                onSuccess()
            }
            .addOnFailureListener {

                onError(
                    "No fue posible guardar el mensaje."
                )
            }
    }


    // =====================================================
    // GUARDAR TRANSCRIPCIÓN DE VOZ
    // CREATE
    // =====================================================

    fun guardarTranscripcion(
        texto: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        val uid = obtenerUidActual()

        if (uid == null) {

            onError(
                "No existe una sesión activa."
            )

            return
        }

        val transcripcion =
            hashMapOf<String, Any>(
                "texto" to texto.trim(),
                "creadoEn" to FieldValue.serverTimestamp()
            )

        db.collection("usuarios")
            .document(uid)
            .collection("transcripciones")
            .add(transcripcion)
            .addOnSuccessListener {

                onSuccess()
            }
            .addOnFailureListener {

                onError(
                    "No fue posible guardar la transcripción."
                )
            }
    }


    // =====================================================
    // ELIMINAR CUENTA COMPLETA
    // DELETE
    // =====================================================

    fun eliminarCuentaUsuario(
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        val usuario =
            auth.currentUser

        if (usuario == null) {

            onError(
                "No existe una sesión activa."
            )

            return
        }

        val uid =
            usuario.uid

        val correo =
            usuario.email

        if (correo == null) {

            onError(
                "No fue posible obtener el correo del usuario."
            )

            return
        }

        if (password.isBlank()) {

            onError(
                "Debes ingresar tu contraseña para eliminar la cuenta."
            )

            return
        }


        // Se vuelve a validar la identidad antes
        // de realizar una operación sensible.
        val credencial =
            EmailAuthProvider.getCredential(
                correo,
                password
            )


        usuario.reauthenticate(
            credencial
        ).addOnSuccessListener {

            // Primero eliminamos los mensajes.
            eliminarSubcoleccion(
                uid = uid,
                nombreSubcoleccion = "mensajes",

                onSuccess = {

                    // Luego las transcripciones.
                    eliminarSubcoleccion(
                        uid = uid,
                        nombreSubcoleccion = "transcripciones",

                        onSuccess = {

                            // Después eliminamos el perfil.
                            db.collection("usuarios")
                                .document(uid)
                                .delete()
                                .addOnSuccessListener {

                                    // Finalmente eliminamos
                                    // Firebase Authentication.
                                    usuario.delete()
                                        .addOnSuccessListener {

                                            onSuccess()
                                        }
                                        .addOnFailureListener {

                                            onError(
                                                "Los datos fueron eliminados, pero no fue posible eliminar la cuenta de autenticación."
                                            )
                                        }
                                }
                                .addOnFailureListener {

                                    onError(
                                        "No fue posible eliminar el perfil del usuario."
                                    )
                                }
                        },

                        onError = onError
                    )
                },

                onError = onError
            )

        }.addOnFailureListener {

            onError(
                "La contraseña ingresada no es correcta."
            )
        }
    }


    // =====================================================
    // ELIMINAR DOCUMENTOS DE UNA SUBCOLECCIÓN
    // =====================================================

    private fun eliminarSubcoleccion(
        uid: String,
        nombreSubcoleccion: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("usuarios")
            .document(uid)
            .collection(nombreSubcoleccion)
            .get()
            .addOnSuccessListener { documentos ->

                if (documentos.isEmpty) {

                    onSuccess()

                    return@addOnSuccessListener
                }

                val batch =
                    db.batch()

                documentos.documents.forEach { documento ->

                    batch.delete(
                        documento.reference
                    )
                }

                batch.commit()
                    .addOnSuccessListener {

                        onSuccess()
                    }
                    .addOnFailureListener {

                        onError(
                            "No fue posible eliminar los datos asociados al usuario."
                        )
                    }
            }
            .addOnFailureListener {

                onError(
                    "No fue posible obtener los datos asociados al usuario."
                )
            }
    }


    // =====================================================
    // CERRAR SESIÓN
    // =====================================================

    fun cerrarSesion() {

        auth.signOut()
    }
}