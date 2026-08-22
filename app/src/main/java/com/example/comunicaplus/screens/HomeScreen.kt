package com.example.comunicaplus.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    onEscribirMensaje: () -> Unit,
    onVozATexto: () -> Unit,
    onFrasesRapidas: () -> Unit,
    onPreferencias: () -> Unit,
    onCerrarSesion: () -> Unit
) {

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
                text = "Comunica+",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Herramientas de comunicación accesible",
                fontSize = 17.sp
            )

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            Button(
                onClick = onEscribirMensaje,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "ESCRIBIR MENSAJE"
                )
            }

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            Button(
                onClick = onVozATexto,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "VOZ A TEXTO"
                )
            }

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            Button(
                onClick = onFrasesRapidas,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "FRASES RÁPIDAS"
                )
            }

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            OutlinedButton(
                onClick = onPreferencias,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "PREFERENCIAS DE ACCESIBILIDAD"
                )
            }

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            TextButton(
                onClick = onCerrarSesion
            ) {
                Text(
                    text = "Cerrar sesión"
                )
            }
        }
    }
}