package com.example.comunicaplus.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuickPhrasesScreen(
    onVolver: () -> Unit
) {

    val frases = listOf(
        "Hola, ¿cómo estás?",
        "Necesito ayuda, por favor.",
        "No puedo escuchar bien.",
        "¿Puedes escribirlo?",
        "¿Puedes repetirlo más lento?",
        "Muchas gracias.",
        "Necesito ir al baño.",
        "¿Dónde está la salida?",
        "Necesito llamar a alguien.",
        "No entendí.",
        "Sí, por favor.",
        "No, gracias."
    )

    var fraseSeleccionada by remember {
        mutableStateOf("")
    }

    var mostrarGrande by remember {
        mutableStateOf(false)
    }

    // =====================================================
    // MOSTRAR FRASE EN GRANDE
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
                horizontalAlignment =
                    Alignment.CenterHorizontally,
                verticalArrangement =
                    Arrangement.Center
            ) {

                Text(
                    text = fraseSeleccionada,
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
                        text = "VOLVER A FRASES"
                    )
                }
            }
        }

        return
    }

    // =====================================================
    // PANTALLA DE FRASES RÁPIDAS
    // =====================================================

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            TextButton(
                onClick = onVolver
            ) {

                Text(
                    text = "← Volver al inicio"
                )
            }

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "Frases rápidas",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Selecciona una frase para mostrarla rápidamente a otra persona.",
                fontSize = 16.sp
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement =
                    Arrangement.spacedBy(12.dp),
                verticalArrangement =
                    Arrangement.spacedBy(12.dp),
                contentPadding =
                    PaddingValues(bottom = 20.dp)
            ) {

                items(frases) { frase ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(125.dp)
                            .clickable {

                                fraseSeleccionada = frase
                                mostrarGrande = true
                            }
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(14.dp),
                            contentAlignment =
                                Alignment.Center
                        ) {

                            Text(
                                text = frase,
                                textAlign = TextAlign.Center,
                                fontSize = 17.sp,
                                fontWeight =
                                    FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}