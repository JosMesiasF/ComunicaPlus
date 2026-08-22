package com.example.comunicaplus.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.comunicaplus.screens.HomeScreen
import com.example.comunicaplus.screens.LoginScreen
import com.example.comunicaplus.screens.RecoverPasswordScreen
import com.example.comunicaplus.screens.RegisterScreen
import com.example.comunicaplus.screens.WriteMessageScreen
import com.example.comunicaplus.screens.SpeechToTextScreen
import com.example.comunicaplus.screens.QuickPhrasesScreen
import com.example.comunicaplus.screens.AccessibilitySettingsScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // LOGIN
        composable("login") {

            LoginScreen(
                onLoginExitoso = {

                    navController.navigate("home") {

                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                },

                onCrearCuenta = {
                    navController.navigate("registro")
                },

                onRecuperarPassword = {
                    navController.navigate("recuperar")
                }
            )
        }

        // REGISTRO
        composable("registro") {

            RegisterScreen(
                onRegistroExitoso = {

                    navController.navigate("login") {

                        popUpTo("registro") {
                            inclusive = true
                        }
                    }
                },

                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        // RECUPERAR CONTRASEÑA
        composable("recuperar") {

            RecoverPasswordScreen(
                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        // HOME
        composable("home") {

            HomeScreen(

                onEscribirMensaje = {

                    navController.navigate(
                        "escribir"
                    )
                },

                onVozATexto = {

                    navController.navigate(
                        "vozTexto"
                    )
                },

                onFrasesRapidas = {

                    navController.navigate(
                        "frases"
                    )
                },

                onPreferencias = {

                    navController.navigate(
                        "preferencias"
                    )
                },

                onCerrarSesion = {

                    navController.navigate("login") {

                        popUpTo("home") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // ESCRIBIR MENSAJE
        composable("escribir") {

            WriteMessageScreen(

                onVolver = {

                    navController.popBackStack()
                }
            )
        }

        // VOZ A TEXTO
        composable("vozTexto") {

            SpeechToTextScreen(

                onVolver = {

                    navController.popBackStack()
                }
            )
        }

        // FRASES RÁPIDAS
        composable("frases") {

            QuickPhrasesScreen(

                onVolver = {

                    navController.popBackStack()
                }
            )
        }

        // PREFERENCIAS DE ACCESIBILIDAD
        composable("preferencias") {

            AccessibilitySettingsScreen(

                onVolver = {

                    navController.popBackStack()
                }
            )
        }

    }
}