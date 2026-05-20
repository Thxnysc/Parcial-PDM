package com.example.juegocolores.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.juegocolores.viewmodel.GameViewModel

@Composable
fun ResultScreen(
    viewModel: GameViewModel,
    onRestart: () -> Unit
) {
// el puntaje final obtenido y la lista de partidas del historial
    val score by viewModel.score.collectAsState()
    val history by viewModel.history.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Juego Finalizado",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))
// se imprime los puntos logrados en la última partida
        Text("Puntaje final: $score")
//  se obtiene el record guardado en el almacenamiento
        Text("Mayor puntaje: ${viewModel.getHighScore()}")

        Spacer(modifier = Modifier.height(20.dp))

        Text("Historial")
// Lista de desplazamiento para el historial de partidas
        LazyColumn {
// Recorre la lista de partidas y genera un componente de tarjeta para cada una
            items(history) { item ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(10.dp)
                    ) {

                        Text("Partida: ${item.gameNumber}")
                        Text("Puntaje: ${item.score}")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onRestart //llama a la accion de reinicio
        ) {

            Text("Jugar otra vez")
        }
    }
}