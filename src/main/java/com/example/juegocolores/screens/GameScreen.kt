package com.example.juegocolores.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.juegocolores.viewmodel.GameViewModel

@Composable
fun GameScreen(
    viewModel: GameViewModel,
    onFinish: () -> Unit
) {
    val currentColor by viewModel.currentColor.collectAsState()
    val score by viewModel.score.collectAsState()
    val timeLeft by viewModel.timeLeft.collectAsState()
    val finished by viewModel.gameFinished.collectAsState()
    val message by viewModel.message.collectAsState()

// Arranca el juego y el temporizador una sola vez al momento de que cargue esta pantalla
    LaunchedEffect(Unit) {
        viewModel.startGame()
    }
// pasa a la pantalla de los resultados
    LaunchedEffect(finished) {
        if (finished) {
            onFinish()
        }
    }

    // Se agregaron dos animaciones, en la animacion uno se cambia el color del fondo dependiendo del color objetivo
    // y en la segunda animacion se cambain el tamaño de los botones dependiendo al puntaje (si es par o impar)
    // animacion 1
    val animatedBackgroundColor by animateColorAsState(
        targetValue = currentColor.copy(alpha = 0.15f),
        animationSpec = tween(durationMillis = 500),
        label = "FondoAnimado"
    )

    // animacion 2
    val animatedButtonWidth by animateDpAsState(
        targetValue = if (score % 2 == 0) 110.dp else 130.dp,
        animationSpec = tween(durationMillis = 300),
        label = "BotonAnchoAnimado"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(animatedBackgroundColor) // Uso del fondo animado
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // se muestra la cuenta regresiva
        Text(text = "Tiempo restante: $timeLeft s", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(10.dp))
        // se muestran los aciertos
        Text(text = "Puntaje: $score", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(30.dp))

        // cuadro que cambia al color aleatorio el cual se debe adivinar
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(currentColor)
        )

        Spacer(modifier = Modifier.height(20.dp))
        // se muestra el mensaje de correcto e incorrecto
        Text(text = message, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(30.dp))

        Row {
            Button(
                onClick = { viewModel.selectColor(Color.Red) }, // se envia el color rojo el cual se debe validar
                modifier = Modifier.width(animatedButtonWidth), // Uso de la animacion de botones
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Rojo", color = Color.White)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = { viewModel.selectColor(Color.Green) }, // se envia el color verde el cual se debe validar
                modifier = Modifier.width(animatedButtonWidth),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
            ) {
                Text("Verde", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(
                onClick = { viewModel.selectColor(Color.Blue) }, // se envia el color azul el cual se debe validar
                modifier = Modifier.width(animatedButtonWidth),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            ) {
                Text("Azul", color = Color.White)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = { viewModel.selectColor(Color.Yellow) }, // se envia el color amarillo el cual se debe validar
                modifier = Modifier.width(animatedButtonWidth),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text("Amarillo", color = Color.White)
            }
        }
    }
}