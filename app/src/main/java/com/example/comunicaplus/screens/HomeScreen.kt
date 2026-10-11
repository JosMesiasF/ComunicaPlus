package com.example.comunicaplus.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun HomeScreen(
    onEscribirMensaje: () -> Unit,
    onVozATexto: () -> Unit,
    onFrasesRapidas: () -> Unit,
    onPreferencias: () -> Unit,
    onCerrarSesion: () -> Unit,
    onAyuda: () -> Unit = {}
) {

    var nombreUsuario by remember {
        mutableStateOf("")
    }

    var cargandoNombre by remember {
        mutableStateOf(true)
    }

    val auth = remember {
        FirebaseAuth.getInstance()
    }

    val db = remember {
        FirebaseFirestore.getInstance()
    }

    // =====================================================
    // OBTENER NOMBRE DEL USUARIO DESDE FIRESTORE
    // =====================================================

    LaunchedEffect(Unit) {

        val uid = auth.currentUser?.uid

        if (uid != null) {

            db.collection("usuarios")
                .document(uid)
                .get()
                .addOnSuccessListener { documento ->

                    nombreUsuario =
                        documento.getString("nombre")
                            ?: ""

                    cargandoNombre = false
                }
                .addOnFailureListener {

                    cargandoNombre = false
                }

        } else {

            cargandoNombre = false
        }
    }

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
                modifier = Modifier.height(12.dp)
            )

            // =====================================================
            // SALUDO PERSONALIZADO
            // =====================================================

            if (cargandoNombre) {

                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp
                )

            } else {

                Text(
                    text =
                        if (nombreUsuario.isNotBlank()) {
                            "Hola, $nombreUsuario 👋"
                        } else {
                            "Hola 👋"
                        },
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Herramientas de comunicación accesible",
                fontSize = 17.sp
            )

            Spacer(
                modifier = Modifier.height(28.dp)
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
                modifier = Modifier.height(14.dp)
            )

            // =====================================================
            // AYUDA / TUTORIAL
            // =====================================================

            OutlinedButton(
                onClick = onAyuda,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {

                Text(
                    text = "AYUDA / TUTORIAL"
                )
            }

            Spacer(
                modifier = Modifier.height(28.dp)
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