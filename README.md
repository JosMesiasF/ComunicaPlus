# ComunicaPlus

Aplicación móvil desarrollada en Android Studio con Kotlin y Jetpack Compose, orientada a facilitar la comunicación de personas con discapacidad sensorial auditiva en situaciones cotidianas.

## Funcionalidades

- Inicio de sesión.
- Registro de usuarios.
- Recuperación de contraseña.
- Registro dinámico de hasta 5 usuarios mediante un array.
- Validación de correo electrónico y contraseña.
- Escritura de mensajes y visualización en pantalla grande.
- Conversión de voz a texto mediante reconocimiento de voz del dispositivo.
- Frases rápidas organizadas en una grilla.
- Preferencias de accesibilidad mediante checklist.
- Tabla resumen de preferencias.
- Navegación entre pantallas mediante Navigation Compose.

## Tecnologías utilizadas

- Android Studio
- Kotlin
- Jetpack Compose
- Material Design 3
- Navigation Compose
- RecognizerIntent
- Git
- GitHub

## Estructura principal

```text
com.example.comunicaplus
│
├── data
│   └── UsuariosData.kt
│
├── model
│   └── Usuario.kt
│
├── navigation
│   └── AppNavigation.kt
│
├── screens
│   ├── LoginScreen.kt
│   ├── RegisterScreen.kt
│   ├── RecoverPasswordScreen.kt
│   ├── HomeScreen.kt
│   ├── WriteMessageScreen.kt
│   ├── SpeechToTextScreen.kt
│   ├── QuickPhrasesScreen.kt
│   └── AccessibilitySettingsScreen.kt
│
└── MainActivity.kt