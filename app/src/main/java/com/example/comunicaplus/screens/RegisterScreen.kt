package com.example.comunicaplus.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comunicaplus.data.cantidadUsuariosRegistrados
import com.example.comunicaplus.data.correoYaRegistrado
import com.example.comunicaplus.data.registrarUsuario
import com.example.comunicaplus.model.Usuario

@Composable
fun RegisterScreen(
    onRegistroExitoso: () -> Unit,
    onVolver: () -> Unit
) {

    var nombre by remember {
        mutableStateOf("")
    }

    var correo by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var rangoEdad by remember {
        mutableStateOf("Seleccionar edad")
    }

    var menuEdadAbierto by remember {
        mutableStateOf(false)
    }

    var tipoComunicacion by remember {
        mutableStateOf("Texto")
    }

    var aceptaTerminos by remember {
        mutableStateOf(false)
    }

    var mensajeError by remember {
        mutableStateOf("")
    }

    var mensajeExito by remember {
        mutableStateOf("")
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 28.dp)
                .verticalScroll(
                    rememberScrollState()
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            Text(
                text = "Crear cuenta",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "Configura tu perfil de comunicación"
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = "Usuarios registrados: $cantidadUsuariosRegistrados / 5",
                fontWeight = FontWeight.SemiBold
            )

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            // NOMBRE
            OutlinedTextField(
                value = nombre,
                onValueChange = {

                    nombre = it
                    mensajeError = ""
                    mensajeExito = ""
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Nombre completo")
                },
                singleLine = true
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            // CORREO
            OutlinedTextField(
                value = correo,
                onValueChange = {

                    correo = it
                    mensajeError = ""
                    mensajeExito = ""
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Correo electrónico")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                singleLine = true
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            // CONTRASEÑA
            OutlinedTextField(
                value = password,
                onValueChange = {

                    password = it
                    mensajeError = ""
                    mensajeExito = ""
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Contraseña")
                },
                supportingText = {
                    Text(
                        "Mínimo 8 caracteres, una mayúscula y un número."
                    )
                },
                visualTransformation =
                    PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                singleLine = true
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            // COMBO BOX / RANGO DE EDAD
            Text(
                text = "Rango de edad",
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.SemiBold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {

                OutlinedButton(
                    onClick = {
                        menuEdadAbierto = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = rangoEdad
                    )
                }

                DropdownMenu(
                    expanded = menuEdadAbierto,
                    onDismissRequest = {
                        menuEdadAbierto = false
                    }
                ) {

                    val opcionesEdad = listOf(
                        "18 a 29 años",
                        "30 a 44 años",
                        "45 a 59 años",
                        "60 años o más"
                    )

                    opcionesEdad.forEach { edad ->

                        DropdownMenuItem(
                            text = {
                                Text(edad)
                            },
                            onClick = {

                                rangoEdad = edad
                                menuEdadAbierto = false
                                mensajeError = ""
                            }
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            // RADIO BUTTONS
            Text(
                text = "Forma de comunicación preferida",
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.SemiBold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            OpcionComunicacion(
                texto = "Texto",
                seleccionado =
                    tipoComunicacion == "Texto",
                onSeleccionar = {
                    tipoComunicacion = "Texto"
                }
            )

            OpcionComunicacion(
                texto = "Voz a texto",
                seleccionado =
                    tipoComunicacion == "Voz a texto",
                onSeleccionar = {
                    tipoComunicacion = "Voz a texto"
                }
            )

            OpcionComunicacion(
                texto = "Frases rápidas",
                seleccionado =
                    tipoComunicacion == "Frases rápidas",
                onSeleccionar = {
                    tipoComunicacion = "Frases rápidas"
                }
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            // CHECKBOX
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Checkbox(
                    checked = aceptaTerminos,
                    onCheckedChange = {

                        aceptaTerminos = it
                        mensajeError = ""
                    }
                )

                Text(
                    text = "Acepto los términos y condiciones"
                )
            }

            Spacer(
                modifier = Modifier.height(22.dp)
            )

            // BOTÓN REGISTRO
            Button(
                onClick = {

                    mensajeExito = ""

                    when {

                        nombre.isBlank() ||
                                correo.isBlank() ||
                                password.isBlank() -> {

                            mensajeError =
                                "Completa todos los campos obligatorios."
                        }

                        !correoValido(correo) -> {

                            mensajeError =
                                "Ingresa un correo electrónico válido."
                        }

                        correoYaRegistrado(correo) -> {

                            mensajeError =
                                "El correo electrónico ya está registrado."
                        }

                        !passwordValida(password) -> {

                            mensajeError =
                                "La contraseña debe tener mínimo 8 caracteres, una mayúscula y un número."
                        }

                        rangoEdad ==
                                "Seleccionar edad" -> {

                            mensajeError =
                                "Selecciona un rango de edad."
                        }

                        !aceptaTerminos -> {

                            mensajeError =
                                "Debes aceptar los términos y condiciones."
                        }

                        cantidadUsuariosRegistrados >= 5 -> {

                            mensajeError =
                                "Se alcanzó el máximo de 5 usuarios."
                        }

                        else -> {

                            val nuevoUsuario = Usuario(
                                id =
                                    cantidadUsuariosRegistrados + 1,
                                nombre = nombre.trim(),
                                correo = correo.trim(),
                                password = password,
                                rangoEdad = rangoEdad,
                                tipoComunicacion =
                                    tipoComunicacion
                            )

                            val registrado =
                                registrarUsuario(
                                    nuevoUsuario
                                )

                            if (registrado) {

                                mensajeError = ""

                                mensajeExito =
                                    "✓ Usuario registrado correctamente."

                            } else {

                                mensajeError =
                                    "No fue posible registrar el usuario."
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            ) {

                Text(
                    text = "CREAR CUENTA"
                )
            }

            // ERROR
            if (mensajeError.isNotEmpty()) {

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = mensajeError,
                    color =
                        MaterialTheme.colorScheme.error
                )
            }

            // ÉXITO
            if (mensajeExito.isNotEmpty()) {

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = mensajeExito,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Button(
                    onClick = onRegistroExitoso,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = "IR AL INICIO DE SESIÓN"
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            TextButton(
                onClick = onVolver
            ) {

                Text(
                    text = "← Volver al inicio de sesión"
                )
            }

            Spacer(
                modifier = Modifier.height(30.dp)
            )
        }
    }
}


// =========================================================
// RADIO BUTTON
// =========================================================

@Composable
fun OpcionComunicacion(
    texto: String,
    seleccionado: Boolean,
    onSeleccionar: () -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment =
            Alignment.CenterVertically
    ) {

        RadioButton(
            selected = seleccionado,
            onClick = onSeleccionar
        )

        Text(
            text = texto
        )
    }
}


// =========================================================
// VALIDACIONES
// =========================================================

fun correoValido(
    correo: String
): Boolean {

    val patronCorreo =
        Regex(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        )

    return patronCorreo.matches(
        correo.trim()
    )
}


fun passwordValida(
    password: String
): Boolean {

    val tieneMayuscula =
        password.any {
            it.isUpperCase()
        }

    val tieneNumero =
        password.any {
            it.isDigit()
        }

    return password.length >= 8 &&
            tieneMayuscula &&
            tieneNumero
}