package com.example.juegocolores.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.juegocolores.screens.*
import com.example.juegocolores.viewmodel.GameViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val viewModel: GameViewModel = viewModel()
    // se define el contenedor de las pantallas y establece cuál aparece primero
    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {
        // 1. Pantalla de Bienvenida :
        composable("welcome") {
            WelcomeScreen(
                onStart = { navController.navigate("game") }
            )
        }

        // 2. Pantalla del Juego
        composable("game") {
            GameScreen(
                viewModel = viewModel,
                onFinish = { navController.navigate("result") }
            )
        }

        // 3. Pantalla de Resultados
        composable("result") {
            ResultScreen(
                viewModel = viewModel,
                onRestart = {
                    // Navega a juego borrando la pantalla de resultados de la pila
                    navController.navigate("game") {
                        popUpTo("welcome") { inclusive = false }
                    }
                }
            )
        }
    }
}