package com.example.comunicaplus.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AccessibilitySettingsScreen(
    onVolver: () -> Unit
) {

    var textoGrande by remember {
        mutableStateOf(false)
    }

    var altoContraste by remember {
        mutableStateOf(false)
    }

    var vibracion by remember {
        mutableStateOf(false)
    }

    var mensajeGuardado by remember {
        mutableStateOf("")
    }

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
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Preferencias de accesibilidad",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Selecciona las ayudas que deseas utilizar durante la comunicación.",
                fontSize = 16.sp
            )

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            // CHECKLIST
            Text(
                text = "Ayudas de accesibilidad",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            OpcionChecklist(
                texto = "Texto grande",
                descripcion = "Facilita la lectura del contenido.",
                seleccionado = textoGrande,
                onCambio = {
                    textoGrande = it
                    mensajeGuardado = ""
                }
            )

            HorizontalDivider()

            OpcionChecklist(
                texto = "Alto contraste",
                descripcion = "Mejora la diferenciación visual.",
                seleccionado = altoContraste,
                onCambio = {
                    altoContraste = it
                    mensajeGuardado = ""
                }
            )

            HorizontalDivider()

            OpcionChecklist(
                texto = "Vibración",
                descripcion = "Utiliza respuesta háptica como apoyo.",
                seleccionado = vibracion,
                onCambio = {
                    vibracion = it
                    mensajeGuardado = ""
                }
            )

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            // TABLA
            Text(
                text = "Resumen de configuración",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    // ENCABEZADO
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {

                        Text(
                            text = "Preferencia",
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Estado",
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    HorizontalDivider()

                    FilaTabla(
                        nombre = "Texto grande",
                        activo = textoGrande
                    )

                    HorizontalDivider()

                    FilaTabla(
                        nombre = "Alto contraste",
                        activo = altoContraste
                    )

                    HorizontalDivider()

                    FilaTabla(
                        nombre = "Vibración",
                        activo = vibracion
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            Button(
                onClick = {
                    mensajeGuardado =
                        "✓ Preferencias guardadas correctamente."
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            ) {

                Text(
                    text = "GUARDAR PREFERENCIAS"
                )
            }

            if (mensajeGuardado.isNotEmpty()) {

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                Text(
                    text = mensajeGuardado,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(
                modifier = Modifier.height(30.dp)
            )
        }
    }
}


@Composable
fun OpcionChecklist(
    texto: String,
    descripcion: String,
    seleccionado: Boolean,
    onCambio: (Boolean) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(
            checked = seleccionado,
            onCheckedChange = onCambio
        )

        Spacer(
            modifier = Modifier.width(10.dp)
        )

        Column {

            Text(
                text = texto,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = descripcion,
                fontSize = 14.sp
            )
        }
    }
}


@Composable
fun FilaTabla(
    nombre: String,
    activo: Boolean
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text(
            text = nombre,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = if (activo) {
                "Activado"
            } else {
                "Desactivado"
            },
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.SemiBold
        )
    }
}