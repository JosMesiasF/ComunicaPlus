package com.example.comunicaplus.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TutorialScreen(
    onVolver: () -> Unit
) {

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
                text = "Ayuda y tutorial",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Aprende rápidamente cómo utilizar ComunicaPlus.",
                fontSize = 16.sp
            )

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            TutorialPaso(
                numero = "1",
                titulo = "Inicia sesión",
                descripcion =
                    "Ingresa tu correo electrónico y contraseña para acceder a ComunicaPlus."
            )

            TutorialPaso(
                numero = "2",
                titulo = "Escribir mensaje",
                descripcion =
                    "Escribe un mensaje y muéstralo en tamaño grande para facilitar la comunicación visual."
            )

            TutorialPaso(
                numero = "3",
                titulo = "Voz a texto",
                descripcion =
                    "Permite que otra persona hable y convierte automáticamente su voz en texto."
            )

            TutorialPaso(
                numero = "4",
                titulo = "Frases rápidas",
                descripcion =
                    "Utiliza mensajes frecuentes sin necesidad de escribirlos completamente."
            )

            TutorialPaso(
                numero = "5",
                titulo = "Preferencias de accesibilidad",
                descripcion =
                    "Configura opciones como texto grande, alto contraste y vibración."
            )

            TutorialPaso(
                numero = "6",
                titulo = "Cerrar sesión",
                descripcion =
                    "Cuando termines de utilizar la aplicación, puedes cerrar tu sesión desde el menú principal."
            )

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            Button(
                onClick = onVolver,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {

                Text(
                    text = "VOLVER AL INICIO"
                )
            }

            Spacer(
                modifier = Modifier.height(32.dp)
            )
        }
    }
}

@Composable
fun TutorialPaso(
    numero: String,
    titulo: String,
    descripcion: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 8.dp
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {

            Text(
                text = "$numero. $titulo",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = descripcion,
                fontSize = 15.sp
            )
        }
    }
}