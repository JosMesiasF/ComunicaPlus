package com.example.comunicaplus.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comunicaplus.data.FirebaseDataService

@Composable
fun AccessibilitySettingsScreen(
    onVolver: () -> Unit,
    onCuentaEliminada: () -> Unit
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

    var mensajeError by remember {
        mutableStateOf("")
    }

    var cargando by remember {
        mutableStateOf(true)
    }

    var guardando by remember {
        mutableStateOf(false)
    }

    var eliminando by remember {
        mutableStateOf(false)
    }

    var mostrarDialogoEliminar by remember {
        mutableStateOf(false)
    }

    var passwordEliminar by remember {
        mutableStateOf("")
    }


    // =====================================================
    // LEER PREFERENCIAS DESDE FIRESTORE
    // READ
    // =====================================================

    LaunchedEffect(Unit) {

        FirebaseDataService.obtenerPerfilUsuario(

            onSuccess = { datos ->

                textoGrande =
                    datos["textoGrande"] as? Boolean ?: false

                altoContraste =
                    datos["altoContraste"] as? Boolean ?: false

                vibracion =
                    datos["vibracion"] as? Boolean ?: false

                cargando = false
            },

            onError = { error ->

                mensajeError = error
                cargando = false
            }
        )
    }


    // =====================================================
    // DIÁLOGO ELIMINAR CUENTA
    // =====================================================

    if (mostrarDialogoEliminar) {

        AlertDialog(
            onDismissRequest = {

                if (!eliminando) {
                    mostrarDialogoEliminar = false
                    passwordEliminar = ""
                }
            },

            title = {
                Text(
                    text = "Eliminar cuenta"
                )
            },

            text = {

                Column {

                    Text(
                        text = "Esta acción eliminará tu cuenta y los datos asociados. No se puede deshacer."
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    OutlinedTextField(
                        value = passwordEliminar,
                        onValueChange = {
                            passwordEliminar = it
                        },
                        label = {
                            Text(
                                text = "Contraseña"
                            )
                        },
                        visualTransformation =
                            PasswordVisualTransformation(),
                        singleLine = true,
                        enabled = !eliminando
                    )
                }
            },

            confirmButton = {

                Button(
                    onClick = {

                        mensajeError = ""

                        if (passwordEliminar.isBlank()) {

                            mensajeError =
                                "Debes ingresar tu contraseña."

                        } else {

                            eliminando = true

                            FirebaseDataService
                                .eliminarCuentaUsuario(

                                    password = passwordEliminar,

                                    onSuccess = {

                                        eliminando = false
                                        mostrarDialogoEliminar = false

                                        onCuentaEliminada()
                                    },

                                    onError = { error ->

                                        eliminando = false
                                        mensajeError = error
                                    }
                                )
                        }
                    },
                    enabled = !eliminando
                ) {

                    if (eliminando) {

                        CircularProgressIndicator(
                            modifier =
                                Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )

                    } else {

                        Text(
                            text = "ELIMINAR"
                        )
                    }
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {

                        mostrarDialogoEliminar = false
                        passwordEliminar = ""
                    },
                    enabled = !eliminando
                ) {

                    Text(
                        text = "CANCELAR"
                    )
                }
            }
        )
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
                onClick = onVolver,
                enabled = !guardando && !eliminando
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


            // =================================================
            // CARGANDO DATOS
            // =================================================

            if (cargando) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.Center
                ) {

                    CircularProgressIndicator()
                }

                Spacer(
                    modifier = Modifier.height(24.dp)
                )
            }


            // =================================================
            // CHECKLIST
            // =================================================

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
                descripcion =
                    "Facilita la lectura del contenido.",
                seleccionado = textoGrande,
                habilitado = !cargando &&
                        !guardando &&
                        !eliminando,
                onCambio = {

                    textoGrande = it
                    mensajeGuardado = ""
                    mensajeError = ""
                }
            )

            HorizontalDivider()

            OpcionChecklist(
                texto = "Alto contraste",
                descripcion =
                    "Mejora la diferenciación visual.",
                seleccionado = altoContraste,
                habilitado = !cargando &&
                        !guardando &&
                        !eliminando,
                onCambio = {

                    altoContraste = it
                    mensajeGuardado = ""
                    mensajeError = ""
                }
            )

            HorizontalDivider()

            OpcionChecklist(
                texto = "Vibración",
                descripcion =
                    "Utiliza respuesta háptica como apoyo.",
                seleccionado = vibracion,
                habilitado = !cargando &&
                        !guardando &&
                        !eliminando,
                onCambio = {

                    vibracion = it
                    mensajeGuardado = ""
                    mensajeError = ""
                }
            )

            Spacer(
                modifier = Modifier.height(32.dp)
            )


            // =================================================
            // TABLA RESUMEN
            // =================================================

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


            // =================================================
            // GUARDAR PREFERENCIAS
            // UPDATE
            // =================================================

            Button(
                onClick = {

                    mensajeGuardado = ""
                    mensajeError = ""
                    guardando = true

                    FirebaseDataService
                        .guardarPreferenciasAccesibilidad(

                            textoGrande = textoGrande,
                            altoContraste = altoContraste,
                            vibracion = vibracion,

                            onSuccess = {

                                guardando = false

                                mensajeGuardado =
                                    "✓ Preferencias guardadas correctamente."
                            },

                            onError = { error ->

                                guardando = false
                                mensajeError = error
                            }
                        )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                enabled = !cargando &&
                        !guardando &&
                        !eliminando
            ) {

                if (guardando) {

                    CircularProgressIndicator(
                        modifier =
                            Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )

                } else {

                    Text(
                        text = "GUARDAR PREFERENCIAS"
                    )
                }
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


            if (mensajeError.isNotEmpty()) {

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                Text(
                    text = mensajeError,
                    color =
                        MaterialTheme.colorScheme.error
                )
            }


            Spacer(
                modifier = Modifier.height(36.dp)
            )

            HorizontalDivider()

            Spacer(
                modifier = Modifier.height(24.dp)
            )


            // =================================================
            // ELIMINAR CUENTA
            // DELETE
            // =================================================

            Text(
                text = "Cuenta",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Puedes eliminar permanentemente tu cuenta y los datos asociados."
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            OutlinedButton(
                onClick = {

                    passwordEliminar = ""
                    mensajeError = ""
                    mostrarDialogoEliminar = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                enabled = !cargando &&
                        !guardando &&
                        !eliminando
            ) {

                Text(
                    text = "ELIMINAR CUENTA"
                )
            }

            Spacer(
                modifier = Modifier.height(32.dp)
            )
        }
    }
}


@Composable
fun OpcionChecklist(
    texto: String,
    descripcion: String,
    seleccionado: Boolean,
    habilitado: Boolean = true,
    onCambio: (Boolean) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Checkbox(
            checked = seleccionado,
            onCheckedChange = onCambio,
            enabled = habilitado
        )

        Spacer(
            modifier = Modifier.width(10.dp)
        )

        Column {

            Text(
                text = texto,
                fontWeight =
                    FontWeight.SemiBold
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
            text =
                if (activo) {
                    "Activado"
                } else {
                    "Desactivado"
                },
            modifier = Modifier.weight(1f),
            fontWeight =
                FontWeight.SemiBold
        )
    }
}