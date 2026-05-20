package com.example.juegocolores.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WelcomeScreen(
    onStart: () -> Unit  //se ejecutara cuando se presione el boton "iniciar juego"
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Juego de Colores",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Presiona el botón que coincida con el color."
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = onStart //navega hacia el tablero del juego
        ) {

            Text("Iniciar Juego")
        }
    }
}