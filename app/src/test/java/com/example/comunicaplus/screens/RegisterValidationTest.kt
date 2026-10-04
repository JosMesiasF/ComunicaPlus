package com.example.comunicaplus.screens

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RegisterValidationTest {

    // =====================================================
    // PRUEBAS DE CORREO
    // =====================================================

    @Test
    fun correoValido_conCorreoCorrecto_retornaTrue() {

        val resultado =
            correoValido(
                "usuario@correo.cl"
            )

        assertTrue(resultado)
    }


    @Test
    fun correoValido_sinArroba_retornaFalse() {

        val resultado =
            correoValido(
                "usuariocorreo.cl"
            )

        assertFalse(resultado)
    }


    @Test
    fun correoValido_sinDominio_retornaFalse() {

        val resultado =
            correoValido(
                "usuario@correo"
            )

        assertFalse(resultado)
    }


    @Test
    fun correoValido_conEspacios_retornaTrue() {

        val resultado =
            correoValido(
                " usuario@correo.cl "
            )

        assertTrue(resultado)
    }


    // =====================================================
    // PRUEBAS DE CONTRASEÑA
    // =====================================================

    @Test
    fun passwordValida_conPasswordCorrecta_retornaTrue() {

        val resultado =
            passwordValida(
                "Prueba123"
            )

        assertTrue(resultado)
    }


    @Test
    fun passwordValida_menorAOchoCaracteres_retornaFalse() {

        val resultado =
            passwordValida(
                "Prue1"
            )

        assertFalse(resultado)
    }


    @Test
    fun passwordValida_sinMayuscula_retornaFalse() {

        val resultado =
            passwordValida(
                "prueba123"
            )

        assertFalse(resultado)
    }


    @Test
    fun passwordValida_sinNumero_retornaFalse() {

        val resultado =
            passwordValida(
                "PruebaTest"
            )

        assertFalse(resultado)
    }
}