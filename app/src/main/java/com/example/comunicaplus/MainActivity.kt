package com.example.comunicaplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.comunicaplus.navigation.AppNavigation
import com.example.comunicaplus.ui.theme.ComunicaPlusTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            ComunicaPlusTheme {

                AppNavigation()
            }
        }
    }
}