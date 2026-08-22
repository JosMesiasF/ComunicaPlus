package com.example.comunicaplus.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WriteMessageScreen(
    onVolver: () -> Unit
) {

    var mensaje by remember {
        mutableStateOf("")
    }

    var mostrarGrande by remember {
        mutableStateOf(false)
    }

    var mensajeError by remember {
        mutableStateOf("")
    }

    // =====================================================
    // MODO MOSTRAR MENSAJE EN GRANDE
    // =====================================================

    if (mostrarGrande) {

        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = mensaje,
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(
                    modifier = Modifier.height(40.dp)
                )

                Button(
                    onClick = {
                        mostrarGrande = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {

                    Text(
                        text = "VOLVER A EDITAR"
                    )
                }
            }
        }

        return
    }

    // =====================================================
    // MODO ESCRIBIR
    // =====================================================

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            TextButton(
                onClick = onVolver
            ) {

                Text(
                    text = "← Volver al inicio"
                )
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Escribir mensaje",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Escribe un mensaje y muéstralo en pantalla grande para facilitar la comunicación.",
                fontSize = 16.sp
            )

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            OutlinedTextField(
                value = mensaje,
                onValueChange = {

                    mensaje = it
                    mensajeError = ""
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 180.dp),
                label = {
                    Text(
                        text = "Escribe tu mensaje"
                    )
                },
                placeholder = {
                    Text(
                        text = "Ejemplo: Necesito ayuda, por favor."
                    )
                },
                minLines = 6,
                maxLines = 10
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "${mensaje.length} caracteres",
                fontSize = 14.sp
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Button(
                onClick = {

                    if (mensaje.isBlank()) {

                        mensajeError =
                            "Debes escribir un mensaje antes de mostrarlo."

                    } else {

                        mensajeError = ""
                        mostrarGrande = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {

                Text(
                    text = "MOSTRAR EN GRANDE"
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            OutlinedButton(
                onClick = {

                    mensaje = ""
                    mensajeError = ""
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            ) {

                Text(
                    text = "LIMPIAR MENSAJE"
                )
            }

            if (mensajeError.isNotEmpty()) {

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = mensajeError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(
                modifier = Modifier.height(32.dp)
            )
        }
    }
}