package com.example.juegocolores.model

data class GameHistory(

    // Aqui se almacenara el numero de partidas que vamos, aumentando de uno en uno
    val gameNumber: Int,
    // Aqui se almacenara el puntaje obtenido en esa misma partida
    val score: Int
)