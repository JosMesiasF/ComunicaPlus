package com.example.comunicaplus.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comunicaplus.data.validarCredenciales

@Composable
fun LoginScreen(
    onLoginExitoso: () -> Unit,
    onCrearCuenta: () -> Unit,
    onRecuperarPassword: () -> Unit
) {

    var correo by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var recordarSesion by remember {
        mutableStateOf(false)
    }

    var mensajeError by remember {
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
                text = "Comunica+",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Comunicación más accesible para todos",
                fontSize = 16.sp
            )

            Spacer(
                modifier = Modifier.height(40.dp)
            )

            // CORREO
            OutlinedTextField(
                value = correo,
                onValueChange = {
                    correo = it
                    mensajeError = ""
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
                modifier = Modifier.height(16.dp)
            )

            // CONTRASEÑA
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    mensajeError = ""
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Contraseña")
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                singleLine = true
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // RECORDAR SESIÓN
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Checkbox(
                    checked = recordarSesion,
                    onCheckedChange = {
                        recordarSesion = it
                    }
                )

                Text(
                    text = "Recordar mi sesión"
                )
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // RECUPERAR CONTRASEÑA
            TextButton(
                onClick = onRecuperarPassword
            ) {
                Text(
                    text = "¿Olvidaste tu contraseña?"
                )
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            // INICIAR SESIÓN
            Button(
                onClick = {

                    when {

                        correo.isBlank() || password.isBlank() -> {

                            mensajeError =
                                "Debes ingresar correo y contraseña."
                        }

                        !correo.contains("@") ||
                                !correo.contains(".") -> {

                            mensajeError =
                                "Ingresa un correo electrónico válido."
                        }

                        else -> {

                            val credencialesCorrectas =
                                validarCredenciales(
                                    correo = correo,
                                    password = password
                                )

                            if (credencialesCorrectas) {

                                mensajeError = ""
                                onLoginExitoso()

                            } else {

                                mensajeError =
                                    "Correo o contraseña incorrectos."
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            ) {

                Text(
                    text = "INICIAR SESIÓN"
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
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = "¿No tienes una cuenta?"
            )

            TextButton(
                onClick = onCrearCuenta
            ) {

                Text(
                    text = "Crear cuenta"
                )
            }
        }
    }
}