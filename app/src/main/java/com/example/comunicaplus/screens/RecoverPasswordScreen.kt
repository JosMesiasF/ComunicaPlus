package com.example.comunicaplus.screens

import android.util.Patterns
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
import com.google.firebase.auth.FirebaseAuth

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

    var cargando by remember {
        mutableStateOf(false)
    }

    val auth = remember {
        FirebaseAuth.getInstance()
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
                text = "Firebase enviará las instrucciones de recuperación a tu correo.",
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
                singleLine = true,
                enabled = !cargando
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            // BOTÓN RECUPERACIÓN
            Button(
                onClick = {

                    mensajeError = ""
                    mensajeExito = ""

                    val correoLimpio =
                        correo.trim()

                    when {

                        correoLimpio.isBlank() -> {

                            mensajeError =
                                "Debes ingresar tu correo electrónico."
                        }

                        !Patterns.EMAIL_ADDRESS
                            .matcher(correoLimpio)
                            .matches() -> {

                            mensajeError =
                                "Ingresa un correo electrónico válido."
                        }

                        else -> {

                            cargando = true

                            auth.sendPasswordResetEmail(
                                correoLimpio
                            ).addOnCompleteListener { task ->

                                cargando = false

                                if (task.isSuccessful) {

                                    mensajeError = ""

                                    mensajeExito =
                                        "✓ Solicitud enviada correctamente."

                                } else {

                                    mensajeExito = ""

                                    mensajeError =
                                        "No fue posible enviar el correo de recuperación. Verifica tu conexión e inténtalo nuevamente."
                                }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                enabled = !cargando
            ) {

                if (cargando) {

                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )

                } else {

                    Text(
                        text = "RECUPERAR CONTRASEÑA"
                    )
                }
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
                    text = "Revisa tu bandeja de entrada y también la carpeta de spam.",
                    fontSize = 14.sp
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            TextButton(
                onClick = onVolver,
                enabled = !cargando
            ) {

                Text(
                    text = "← Volver al inicio de sesión"
                )
            }
        }
    }
}