package com.example.comunicaplus.screens

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
fun SpeechToTextScreen(
    onVolver: () -> Unit
) {

    var textoReconocido by remember {
        mutableStateOf("")
    }

    var mensajeError by remember {
        mutableStateOf("")
    }

    var mostrarGrande by remember {
        mutableStateOf(false)
    }

    val speechLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.StartActivityForResult()
        ) { resultado ->

            if (resultado.resultCode == Activity.RESULT_OK) {

                val resultados =
                    resultado.data
                        ?.getStringArrayListExtra(
                            RecognizerIntent.EXTRA_RESULTS
                        )

                if (!resultados.isNullOrEmpty()) {

                    textoReconocido =
                        resultados[0]

                    mensajeError = ""

                } else {

                    mensajeError =
                        "No fue posible reconocer el mensaje."
                }
            }
        }

    // MOSTRAR TEXTO EN GRANDE
    if (mostrarGrande) {

        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                horizontalAlignment =
                    Alignment.CenterHorizontally,
                verticalArrangement =
                    Arrangement.Center
            ) {

                Text(
                    text = textoReconocido,
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
                        text = "VOLVER"
                    )
                }
            }
        }

        return
    }

    // VOZ A TEXTO
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
                ),
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            TextButton(
                onClick = onVolver,
                modifier = Modifier.align(
                    Alignment.Start
                )
            ) {

                Text(
                    text = "← Volver al inicio"
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Voz a texto",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text =
                    "Permite que otra persona hable y transforma su voz en texto para facilitar la comunicación.",
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )

            Spacer(
                modifier = Modifier.height(36.dp)
            )

            Text(
                text = "🎤",
                fontSize = 70.sp
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Button(
                onClick = {

                    mensajeError = ""

                    try {

                        val intent =
                            Intent(
                                RecognizerIntent
                                    .ACTION_RECOGNIZE_SPEECH
                            ).apply {

                                putExtra(
                                    RecognizerIntent
                                        .EXTRA_LANGUAGE_MODEL,
                                    RecognizerIntent
                                        .LANGUAGE_MODEL_FREE_FORM
                                )

                                putExtra(
                                    RecognizerIntent.EXTRA_LANGUAGE,
                                    "es-CL"
                                )

                                putExtra(
                                    RecognizerIntent.EXTRA_PROMPT,
                                    "Habla ahora"
                                )
                            }

                        speechLauncher.launch(
                            intent
                        )

                    } catch (e: Exception) {

                        mensajeError =
                            "El reconocimiento de voz no está disponible en este dispositivo."
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
            ) {

                Text(
                    text = "INICIAR RECONOCIMIENTO"
                )
            }

            Spacer(
                modifier = Modifier.height(30.dp)
            )

            if (textoReconocido.isNotEmpty()) {

                Text(
                    text = "Texto reconocido",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier =
                        Modifier.fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Card(
                    modifier =
                        Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = textoReconocido,
                        modifier =
                            Modifier.padding(20.dp),
                        fontSize = 22.sp
                    )
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Button(
                    onClick = {
                        mostrarGrande = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
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

                        textoReconocido = ""
                        mensajeError = ""

                    },
                    modifier =
                        Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = "LIMPIAR TEXTO"
                    )
                }
            }

            if (mensajeError.isNotEmpty()) {

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    text = mensajeError,
                    color =
                        MaterialTheme.colorScheme.error
                )
            }

            Spacer(
                modifier = Modifier.height(32.dp)
            )
        }
    }
}