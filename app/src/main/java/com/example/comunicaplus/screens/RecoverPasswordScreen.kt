package com.example.comunicaplus.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comunicaplus.data.buscarUsuarioPorCorreo

@Composable
fun RecoverPasswordScreen(
    onVolver: () -> Unit
) {

    var correo by remember {
        mutableStateOf("")
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
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Recuperar contraseña",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = "Ingresa el correo asociado a tu cuenta."
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Te mostraremos una confirmación para continuar con la recuperación.",
                fontSize = 14.sp
            )

            Spacer(
                modifier = Modifier.height(32.dp)
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
                modifier = Modifier.height(24.dp)
            )

            // BOTÓN RECUPERACIÓN
            Button(
                onClick = {

                    mensajeError = ""
                    mensajeExito = ""

                    when {

                        correo.isBlank() -> {

                            mensajeError =
                                "Debes ingresar tu correo electrónico."
                        }

                        !correoValido(correo) -> {

                            mensajeError =
                                "Ingresa un correo electrónico válido."
                        }

                        else -> {

                            val usuarioEncontrado =
                                buscarUsuarioPorCorreo(correo)

                            if (usuarioEncontrado != null) {

                                mensajeError = ""

                                mensajeExito =
                                    "✓ Se encontró la cuenta asociada a ${usuarioEncontrado.correo}."

                            } else {

                                mensajeExito = ""

                                mensajeError =
                                    "No existe una cuenta asociada a este correo."
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            ) {

                Text(
                    text = "RECUPERAR CONTRASEÑA"
                )
            }

            // ERROR
            if (mensajeError.isNotEmpty()) {

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                Text(
                    text = mensajeError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            // ÉXITO
            if (mensajeExito.isNotEmpty()) {

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                Text(
                    text = mensajeExito,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text = "Las instrucciones de recuperación serían enviadas al correo registrado.",
                    fontSize = 14.sp
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            TextButton(
                onClick = onVolver
            ) {

                Text(
                    text = "← Volver al inicio de sesión"
                )
            }
        }
    }
}