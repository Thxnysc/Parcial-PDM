package com.example.juegocolores.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.juegocolores.model.GameHistory
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {
    // inicializamos el almacenamiento  del telefono para poder guardar los datos
    private val prefs = application.getSharedPreferences("game_prefs", Context.MODE_PRIVATE)
    //nuestra variable de tiempo que se cancela cada vez que se reinicia el juego
    private var timerJob: Job? = null
// nuestra lista de colores base
    val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow)
// aqui se almacena el color actual
    private val _currentColor = MutableStateFlow(colors.random())

    val currentColor: StateFlow<Color> = _currentColor
// para llevar la cuenta interna del puntaje del jugador
    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score
// lleva el conteo interno de los segundos restantes
    private val _timeLeft = MutableStateFlow(30)
    val timeLeft: StateFlow<Int> = _timeLeft
    //registra internamente si el juego ha terminado o sigue activo
    private val _gameFinished = MutableStateFlow(false)
    val gameFinished: StateFlow<Boolean> = _gameFinished
// guarda el texto del mensaje de acierto o error
    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message
// guarda la lista de objetos con el historial de todas las partidas jugada
    private val _history = MutableStateFlow<List<GameHistory>>(emptyList())
    val history: StateFlow<List<GameHistory>> = _history

    fun startGame() {
        // Cancelamos cualquier temporizador previo por seguridad
        timerJob?.cancel()

        // Reseteamos los estados para la nueva partida
        _score.value = 0
        _timeLeft.value = 30
        _gameFinished.value = false
        _message.value = ""
        _currentColor.value = colors.random()

        // Iniciamos el conteo regresivo de 30 segundos
        timerJob = viewModelScope.launch {
            while (_timeLeft.value > 0) {
                delay(1000)
                _timeLeft.value--
            }
            // Al terminar el tiempo, guardamos en el historial local y finalizamos
            saveHistory()
            _gameFinished.value = true
        }
    }

    fun selectColor(color: Color) {
        // Verificamos si el usuario acertó el color
        if (color == _currentColor.value) {
            _score.value++

            // en caso de que se acorrecto aparecera el siguiente mensaje:
            _message.value = "¡Correcto!"
        } else {
            // de lo contrario:
            _message.value = "Incorrecto :c"
        }

        // Se cambia el color automaticamente de manera aleatoria
        _currentColor.value = colors.random()
        saveHighScore()
    }

    // Guarda de forma continua si el puntaje supera al record
    private fun saveHighScore() {
        val highScore = prefs.getInt("high_score", 0)
        if (_score.value > highScore) {
            prefs.edit().putInt("high_score", _score.value).apply()
        }
    }
    // Retorna la puntuación máxima guardada en el dispositivo
    fun getHighScore(): Int {
        return prefs.getInt("high_score", 0)
    }
    // Agrega los resultados finales a una copia mutable del historial para refrescar la LazyColumn de resultados
    private fun saveHistory() {
        val currentList = _history.value.toMutableList()
        currentList.add(
            GameHistory(
                gameNumber = currentList.size + 1,
                score = _score.value
            )
        )
        _history.value = currentList
    }
}