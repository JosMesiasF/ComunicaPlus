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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.FirebaseTooManyRequestsException

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
                singleLine = true,
                enabled = !cargando
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
                visualTransformation =
                    PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                singleLine = true,
                enabled = !cargando
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // RECORDAR SESIÓN
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Checkbox(
                    checked = recordarSesion,
                    onCheckedChange = {
                        recordarSesion = it
                    },
                    enabled = !cargando
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
                onClick = onRecuperarPassword,
                enabled = !cargando
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

                    mensajeError = ""

                    val correoLimpio =
                        correo.trim()

                    when {

                        correoLimpio.isBlank() ||
                                password.isBlank() -> {

                            mensajeError =
                                "Debes ingresar correo y contraseña."
                        }

                        !Patterns.EMAIL_ADDRESS
                            .matcher(correoLimpio)
                            .matches() -> {

                            mensajeError =
                                "Ingresa un correo electrónico válido."
                        }

                        else -> {

                            cargando = true

                            auth.signInWithEmailAndPassword(
                                correoLimpio,
                                password
                            ).addOnCompleteListener { task ->

                                cargando = false

                                if (task.isSuccessful) {

                                    mensajeError = ""

                                    onLoginExitoso()

                                } else {

                                    mensajeError =
                                        when (task.exception) {

                                            is FirebaseAuthInvalidCredentialsException ->
                                                "Correo o contraseña incorrectos."

                                            is FirebaseAuthInvalidUserException ->
                                                "La cuenta no existe o está deshabilitada."

                                            is FirebaseNetworkException ->
                                                "No fue posible conectarse. Revisa tu conexión a internet."

                                            is FirebaseTooManyRequestsException ->
                                                "Demasiados intentos. Intenta nuevamente más tarde."

                                            else ->
                                                "No fue posible iniciar sesión. Verifica tus datos."
                                        }
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
                        text = "INICIAR SESIÓN"
                    )
                }
            }

            if (mensajeError.isNotEmpty()) {

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = mensajeError,
                    color =
                        MaterialTheme.colorScheme.error
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = "¿No tienes una cuenta?"
            )

            TextButton(
                onClick = onCrearCuenta,
                enabled = !cargando
            ) {

                Text(
                    text = "Crear cuenta"
                )
            }
        }
    }
}